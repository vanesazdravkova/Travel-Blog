package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.dto.AddFutureTripDto;
import bg.softuni.travelProject.model.view.FutureTripViewModel;
import bg.softuni.travelProject.service.FutureTripService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FutureTripServiceImpl implements FutureTripService {

    private final WebClient webClient;

    public FutureTripServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<FutureTripViewModel> getAllFutureTrips() {
        return webClient.get()
                .uri("/future-trips/all")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<FutureTripViewModel>() {
                })
                .collectList()
                .block();
    }

    @Override
    public void createFutureTrip(AddFutureTripDto addFutureTripDto) {
        webClient.post()
                .uri("/future-trips/add")
                .bodyValue(addFutureTripDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteFutureTrip(Long id) {
        webClient.delete()
                .uri("/future-trips/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
