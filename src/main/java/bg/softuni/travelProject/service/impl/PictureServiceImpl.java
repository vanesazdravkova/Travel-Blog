package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.cloudinary.CloudinaryImage;
import bg.softuni.travelProject.model.entity.PictureEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.model.view.PictureHomePageViewModel;
import bg.softuni.travelProject.model.view.PictureViewModel;
import bg.softuni.travelProject.repository.PictureRepository;
import bg.softuni.travelProject.repository.TripRepository;
import bg.softuni.travelProject.repository.TypeRepository;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.service.CloudinaryService;
import bg.softuni.travelProject.service.PictureService;
import bg.softuni.travelProject.web.exception.InvalidFileException;
import bg.softuni.travelProject.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final TypeRepository typeRepository;

    public PictureServiceImpl(PictureRepository pictureRepository,
                              UserRepository userRepository,
                              TripRepository tripRepository,
                              CloudinaryService cloudinaryService,
                              ModelMapper modelMapper,
                              TypeRepository typeRepository) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.typeRepository = typeRepository;
    }

    @Override
    public Page<PictureViewModel> findAllPictureViewModelsByUsername(String principalName, Pageable pageable) {
        Page<PictureEntity> pictures = this.pictureRepository
                .findAllByAuthor_Username(principalName, pageable);

        return pictures.map(picture -> this.mapToPictureViewModel(picture, principalName));
    }

    @Override
    public PictureEntity createAndSavePictureEntity(Long userId, MultipartFile file, Long tripId) {
        try {
            final CloudinaryImage upload = cloudinaryService
                    .uploadImage(file);
            PictureEntity newPicture = new PictureEntity()
                    .setAuthor(userRepository.findById(userId)
                            .orElseThrow(() -> new ObjectNotFoundException("User with id " + userId + " not found!")))
                    .setUrl(upload.getUrl())
                    .setPublicId(upload.getPublicId())
                    .setTitle(file.getOriginalFilename())
                    .setTrip(tripRepository.findById(tripId).orElse(null));

            return pictureRepository.save(newPicture);
        } catch (RuntimeException | IOException e) {
            throw new InvalidFileException("File with name " + file.getOriginalFilename() + " can not be uploaded.");
        }
    }

    @Override
    @Transactional
    public void deletePicture(Long id) {
        Optional<PictureEntity> picture = pictureRepository.findById(id);

        if (picture.isEmpty()) {
            throw new ObjectNotFoundException("Picture with id " + id + " not found!");
        }

        String publicId = picture.get().getPublicId();
        if (publicId != null) {
            cloudinaryService.delete(publicId);
        }

        pictureRepository.deleteById(id);
    }

    @Override
    public boolean isOwner(String userName, Long pictureId) {
        boolean isOwner = pictureRepository.
                findById(pictureId).
                filter(picture -> picture.getAuthor().getUsername().equals(userName)).
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
                anyMatch(r -> r.getRole() == RoleNameEnum.ADMIN);
    }

    @Override
    public PictureViewModel mapToPictureViewModel(PictureEntity picture, String principalName) {
        PictureViewModel pictureViewModel = modelMapper.map(picture, PictureViewModel.class);
        pictureViewModel.setTripId(picture.getTrip().getId())
                .setAuthorUsername(picture.getAuthor().getUsername())
                .setCanNotDelete(!isOwner(principalName, picture.getId()));
        return pictureViewModel;
    }

    @Override
    public PictureHomePageViewModel mapToPictureHomePageViewModel(PictureEntity picture) {
        PictureHomePageViewModel pictureHomePageViewModel = modelMapper.map(picture, PictureHomePageViewModel.class);
        pictureHomePageViewModel.setTripId(picture.getTrip().getId())
                .setAuthorFullName(picture.getAuthor().getFirstName() + " " + picture.getAuthor().getLastName());
        return pictureHomePageViewModel;
    }

    @Override
    public List<PictureHomePageViewModel> getThreeRandomPicturesByTripType(TripTypeEnum tripTypeEnum) {
        List<PictureEntity> allPictures = pictureRepository.findAllPicturesByTripType(
                this.typeRepository.findByName(tripTypeEnum)
                        .orElseThrow(() -> new ObjectNotFoundException("TypeEntity with name " + tripTypeEnum + " not found!")));

        List<PictureHomePageViewModel> resultPictures = new ArrayList<>();
        Set<Long> selectedTripIds = new HashSet<>();

        Collections.shuffle(allPictures);

        for (PictureEntity picture : allPictures) {
            if (!selectedTripIds.contains(picture.getTrip().getId())) {
                resultPictures.add(this.mapToPictureHomePageViewModel(picture));
                selectedTripIds.add(picture.getTrip().getId());
            }

            if (resultPictures.size() == 3) {
                break;
            }
        }
        return resultPictures;
    }
}
