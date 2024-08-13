package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.dto.AddFutureTripDto;
import bg.softuni.travelProject.model.view.FutureTripViewModel;
import bg.softuni.travelProject.service.FutureTripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/future-trips")
public class FutureTripController {

    private final FutureTripService futureTripService;

    public FutureTripController(FutureTripService futureTripService) {
        this.futureTripService = futureTripService;
    }

    @ModelAttribute
    AddFutureTripDto addFutureTripDto() {
        return new AddFutureTripDto();
    }

    @GetMapping("/all")
    public String getAllFortunes(Model model){
        List<FutureTripViewModel> allFutureTrips = futureTripService.getAllFutureTrips();
        model.addAttribute("futureTrips", allFutureTrips);
        model.addAttribute("heading",
                String.format("All future trips (%s)", allFutureTrips.size()));
        return "all-future-trips";
    }

    @GetMapping("/add")
    public String addFutureTrip() {
        return "add-future-trip";
    }

    @PostMapping("/add")
    public String addFutureTripConfirm(
            @Valid AddFutureTripDto addFutureTripDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("addFutureTripDto", addFutureTripDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addFutureTripDto",
                            bindingResult);

            return "redirect:/future-trips/add";
        }

        futureTripService.createFutureTrip(addFutureTripDto);
        return "redirect:/future-trips/all";
    }

    @DeleteMapping("/{id}")
    public String deleteFutureTrip(@PathVariable("id") Long futureTripId) {
        futureTripService.deleteFutureTrip(futureTripId);

        return "redirect:/future-trips/all";
    }
}