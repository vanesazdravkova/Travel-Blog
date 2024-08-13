package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.entity.UserEntity;
import bg.softuni.travelProject.model.view.TripViewModel;
import bg.softuni.travelProject.repository.TripRepository;
import bg.softuni.travelProject.repository.UserRepository;
import bg.softuni.travelProject.utils.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TripServiceTest {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TypeService typeService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private TestHelper testHelper;
    @Autowired
    private TripService tripService;

    private TripEntity trip;

    @BeforeEach
    void setUp() {
        UserEntity user = testHelper.createUser("user");
        List<TypeEntity> testTypes = testHelper.createTypes();

        trip = testHelper.createTrip(user, testTypes);
    }

    @AfterEach
    void tearDown() {
        testHelper.cleanUpDatabase();
    }

    @Test
    @Transactional
    void searchTripFounded() {
        TripSearchDto tripSearchDto = testHelper.createTripSearchDto("Par");
        TripViewModel tripViewModel = getTripViewModel();

        List<TripViewModel> expected = List.of(tripViewModel);

        List<TripViewModel> actual = tripService.searchTrip(tripSearchDto,
                PageRequest.of(0, 4)).getContent();

        assertEquals(expected.size(), actual.size());
        assertSearchedEquals(expected.get(0), actual.get(0));
    }

    @Test
    @Transactional
    void searchTripNotFounded() {
        TripSearchDto tripSearchDto = testHelper.createTripSearchDto("Prague");

        List<TripViewModel> actual = tripService.searchTrip(tripSearchDto,
                PageRequest.of(0, 4)).getContent();

        assertEquals(0, actual.size());
    }

    private TripViewModel getTripViewModel() {
        TripViewModel tripViewModel = new TripViewModel();
        tripViewModel.setId(trip.getId());
        tripViewModel.setName(trip.getName());
        tripViewModel.setPricingLevel(trip.getPricingLevel());
        tripViewModel.setContinent(trip.getContinent());
        tripViewModel.setAuthor(
                trip.getAuthor().getFirstName() + " " + trip.getAuthor().getLastName());
        tripViewModel.setPictureUrl(trip.getPictures().get(0).getUrl());
        tripViewModel.setDays(trip.getDays());
        tripViewModel.setCountriesVisited(trip.getCountriesVisited());
        return tripViewModel;
    }

    private void assertSearchedEquals(TripViewModel expected, TripViewModel actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPricingLevel(), actual.getPricingLevel());
        assertEquals(expected.getContinent(), actual.getContinent());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getPictureUrl(), actual.getPictureUrl());
        assertEquals(expected.getDays(), actual.getDays());
        assertEquals(expected.getCountriesVisited(), actual.getCountriesVisited());
    }
}
