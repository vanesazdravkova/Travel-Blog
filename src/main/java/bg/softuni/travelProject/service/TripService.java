package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.user.MyUserDetails;
import bg.softuni.travelProject.model.dto.TripAddDto;
import bg.softuni.travelProject.model.dto.TripEditDto;
import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.view.TripDetailsViewModel;
import bg.softuni.travelProject.model.view.TripViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface TripService {

    Page<TripViewModel> findAllTripViewModels(Pageable pageable);

    Page<TripViewModel> findAllFilteredTripViewModels(ContinentEnum continent, Pageable pageable);

    TripViewModel mapToTripViewModel(TripEntity trip);

    Long addTrip(TripAddDto tripAddDto, MyUserDetails myUserDetails);

    TripDetailsViewModel findTripDetailsViewModelById(Long id, String principalName);

    boolean isOwner(String userName, Long tripId);

    boolean isTripFavorite(String username, Long tripId);

    TripEditDto getTripEditDetails(Long tripId);

    void updateTripById(TripEditDto tripEditDto, Long id, UserDetails userDetails);

    String extractVideoId(String videoUrl);

    void deleteTripById(Long tripId);

    Page<TripViewModel> searchTrip(TripSearchDto tripSearchDto, Pageable pageable);

    Page<TripViewModel> findAllTripsUploadedByUserId(Long id, Pageable pageable);

    Page<TripViewModel> findAllFavoriteTripsForUserId(Long userId, Pageable pageable);

    long findCountByContinent(ContinentEnum continentEnum);

    long findCountAll();
}
