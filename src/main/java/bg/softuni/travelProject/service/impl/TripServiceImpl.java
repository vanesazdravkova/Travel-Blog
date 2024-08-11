package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.entity.PictureEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.user.MyUserDetails;
import bg.softuni.travelProject.model.dto.TripAddDto;
import bg.softuni.travelProject.model.dto.TripEditDto;
import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.view.TripDetailsViewModel;
import bg.softuni.travelProject.model.view.TripViewModel;
import bg.softuni.travelProject.repository.TripRepository;
import bg.softuni.travelProject.repository.TripSpecification;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.PictureService;
import bg.softuni.travelProject.service.TripService;
import bg.softuni.travelProject.service.TypeService;
import bg.softuni.travelProject.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TypeService typeService;
    private final PictureService pictureService;

    public TripServiceImpl(TripRepository tripRepository,
                           ModelMapper modelMapper,
                           UserRepository userRepository,
                           TypeService typeService,
                           PictureService pictureService) {
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.typeService = typeService;
        this.pictureService = pictureService;
    }

    @Override
    @Transactional
    public Page<TripViewModel> findAllTripViewModels(Pageable pageable) {
        return this.tripRepository.
                findAll(pageable)
                .map(this::mapToTripViewModel);
    }

    @Override
    @Transactional
    public Page<TripViewModel> findAllFilteredTripViewModels(ContinentEnum continent, Pageable pageable) {
        return this.tripRepository.
                findAllByContinent(continent, pageable)
                .map(this::mapToTripViewModel);
    }

    @Override
    public TripViewModel mapToTripViewModel(TripEntity trip) {
        TripViewModel tripViewModel = modelMapper.map(trip, TripViewModel.class);
        tripViewModel.setAuthor(trip.getAuthor().getFirstName() + " " + trip.getAuthor().getLastName());
        tripViewModel.setPictureUrl(!trip.getPictures().isEmpty() ?
                trip.getPictures()
                        .stream()
                        .sorted(Comparator.comparingLong(PictureEntity::getId))
                        .collect(Collectors.toList())
                        .get(0)
                        .getUrl() : "/static/images/register.jpg");
        return tripViewModel;
    }

    @Override
    @Transactional
    public Long addTrip(TripAddDto tripAddDto, MyUserDetails myUserDetails) {
        TripEntity newTrip = modelMapper.map(tripAddDto, TripEntity.class);

        newTrip.setAuthor(userRepository.findById(myUserDetails.getId()).orElseThrow());
        newTrip.setTypes(tripAddDto.getTypes()
                .stream()
                .map(typeService::findByTypeName)
                .collect(Collectors.toList()));

        newTrip.setPictures(new ArrayList<>());
        newTrip = this.tripRepository.save(newTrip);
        for (MultipartFile file : tripAddDto.getPictureFiles()) {
            PictureEntity picture = pictureService.createAndSavePictureEntity(myUserDetails.getId(), file, newTrip.getId());
            newTrip.getPictures().add(picture);
        }
        return tripRepository.save(newTrip).getId();
    }

    @Override
    @Transactional
    public TripDetailsViewModel findTripDetailsViewModelById(Long id, String principalName) {
        TripEntity tripEntity = this.tripRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trip with ID " + id + " not found!"));

        TripDetailsViewModel tripDetailsViewModel = modelMapper
                .map(tripEntity, TripDetailsViewModel.class);

        tripDetailsViewModel.setPictures(tripEntity.getPictures()
                .stream()
                .map(p -> pictureService.mapToPictureViewModel(p, principalName))
                .collect(Collectors.toList()));

        tripDetailsViewModel.setLandmarks(Arrays.stream(tripEntity.getLandmarks().split("[\r\n]+")).collect(Collectors.toList()));
        tripDetailsViewModel.setAuthor(tripEntity.getAuthor().getFirstName() + " " + tripEntity.getAuthor().getLastName());
        tripDetailsViewModel.setCanDelete(isOwner(principalName, id));
        tripDetailsViewModel.setIsFavorite(isTripFavorite(principalName, tripEntity.getId()));
        tripDetailsViewModel.setVideoId(extractVideoId(tripEntity.getVideoUrl()));

        return tripDetailsViewModel;
    }

    @Override
    public boolean isOwner(String userName, Long tripId) {
        boolean isOwner = tripRepository.
                findById(tripId).
                filter(t -> t.getAuthor().getUsername().equals(userName)).
                isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository
                .findByUsername(userName)
                .filter(this::isAdmin)
                .isPresent();
    }

    public boolean isAdmin(UserEntity user) {
        return user.getRoles().
                stream().
                anyMatch(t -> t.getRole() == RoleNameEnum.ADMIN);
    }

    @Override
    public boolean isTripFavorite(String username, Long tripId) {
        UserEntity user = this.userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return false;
        }

        return user.getFavorites().stream().map(TripEntity::getId).anyMatch(id -> id.equals(tripId));
    }

    @Override
    @Transactional
    public TripEditDto getTripEditDetails(Long tripId) {
        TripEntity tripEntity = tripRepository.findById(tripId)
                .orElseThrow(() -> new ObjectNotFoundException("Trip with ID " + tripId + " not found"));

        TripEditDto tripEditDto = modelMapper.map(tripEntity, TripEditDto.class);
        tripEditDto.setTypes(tripEntity.getTypes()
                .stream()
                .map(TypeEntity::getName)
                .collect(Collectors.toList()));

        return tripEditDto;
    }

    @Override
    public void updateTripById(TripEditDto tripEditDto, Long id, UserDetails userDetails) {
        TripEntity updateTrip = this.tripRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Trip with id: " + id + " not found!"));

        updateTrip.setAuthor(userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException("User with username " + userDetails.getUsername() + " not found!")));

        updateTrip.setTypes(tripEditDto.getTypes()
                .stream()
                .map(typeService::findByTypeName)
                .collect(Collectors.toList()));

        updateTrip.setName(tripEditDto.getName())
                .setPricingLevel(tripEditDto.getPricingLevel())
                .setContinent(tripEditDto.getContinent())
                .setCountriesVisited(tripEditDto.getCountriesVisited())
                .setDays(tripEditDto.getDays())
                .setDescription(tripEditDto.getDescription())
                .setVideoUrl(tripEditDto.getVideoUrl())
                .setLandmarks(tripEditDto.getLandmarks());

        tripRepository.save(updateTrip);
    }

    @Override
    public String extractVideoId(String videoUrl) {
        String pattern = "(?<=v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|%2Fvideos%2F|%2Fvi%2F|v=|%2Fv%2F)([a-zA-Z0-9_-]{11})";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoUrl);

        if (matcher.find()) {
            return matcher.group();
        }

        return null; // Video ID not found
    }

    @Override
    @Transactional
    public void deleteTripById(Long tripId) {
        TripEntity trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ObjectNotFoundException("Trip with ID " + tripId + " not found!"));

        trip.getPictures().forEach(picture -> pictureService.deletePicture(picture.getId()));
        trip.getFavoriteUsers().forEach(user -> {
            user.getFavorites().remove(trip);
            userRepository.save(user);
        });
        tripRepository.deleteById(tripId);
    }

    @Override
    @Transactional
    public Page<TripViewModel> searchTrip(TripSearchDto tripSearchDto, Pageable pageable) {
        return this.tripRepository
                .findAll(new TripSpecification(tripSearchDto), pageable)
                .map(this::mapToTripViewModel);
    }

    @Override
    @Transactional
    public Page<TripViewModel> findAllTripsUploadedByUserId(Long id, Pageable pageable) {
        return this.tripRepository.
                findAllByAuthor_Id(id, pageable)
                .map(this::mapToTripViewModel);
    }

    @Override
    @Transactional
    public Page<TripViewModel> findAllFavoriteTripsForUserId(Long userId, Pageable pageable) {
        return this.tripRepository.
                findAllFavoriteTrips(userId, pageable)
                .map(this::mapToTripViewModel);
    }

    @Override
    public long findCountByContinent(ContinentEnum continentEnum) {
        return this.tripRepository.countTripEntitiesByContinent(continentEnum);
    }

    @Override
    public long findCountAll() {
        return this.tripRepository.count();
    }
}