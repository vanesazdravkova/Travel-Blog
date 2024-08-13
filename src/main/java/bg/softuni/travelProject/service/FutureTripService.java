package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.dto.AddFutureTripDto;
import bg.softuni.travelProject.model.view.FutureTripViewModel;

import java.util.List;

public interface FutureTripService {

    List<FutureTripViewModel> getAllFutureTrips();

    void createFutureTrip(AddFutureTripDto addFutureTripDto);

    void deleteFutureTrip(Long id);
}
