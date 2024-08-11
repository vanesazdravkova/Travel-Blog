package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.view.StatisticsViewModel;
import bg.softuni.travelProject.service.TripService;
import bg.softuni.travelProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class StatisticsController {

    private final TripService tripService;
    private final UserService userService;

    public StatisticsController(TripService tripService,
                                UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @GetMapping("/statistics")
    public String register(Model model) {

        StatisticsViewModel statisticsViewModel = new StatisticsViewModel()
                .setAllTrips(this.tripService.findCountAll())
                .setAsianTrips(this.tripService.findCountByContinent(ContinentEnum.ASIA))
                .setAfricanTrips(this.tripService.findCountByContinent(ContinentEnum.AFRICA))
                .setNorthAmericanTrips(this.tripService.findCountByContinent(ContinentEnum.NORTH_AMERICA))
                .setSouthAmericanTrips(this.tripService.findCountByContinent(ContinentEnum.SOUTH_AMERICA))
                .setAntarcticanTrips(this.tripService.findCountByContinent(ContinentEnum.ANTARCTICA))
                .setEuropeanTrips(this.tripService.findCountByContinent(ContinentEnum.EUROPE))
                .setAustralianTrips(this.tripService.findCountByContinent(ContinentEnum.AUSTRALIA))
                .setUsersCount(this.userService.getCountRegisteredUsers())
                .setLocalDateTime(LocalDateTime.now());

        model.addAttribute("statistics", statisticsViewModel);
        return "statistics";
    }
}