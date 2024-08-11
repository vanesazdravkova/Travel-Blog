package bg.softuni.travelProject.web;

import bg.softuni.travelProject.model.dto.TripAddDto;
import bg.softuni.travelProject.model.dto.TripEditDto;
import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.dto.UploadPictureDto;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.model.user.MyUserDetails;
import bg.softuni.travelProject.model.view.TripViewModel;
import bg.softuni.travelProject.service.PictureService;
import bg.softuni.travelProject.service.TripService;
import bg.softuni.travelProject.service.TypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;
    private final PictureService pictureService;
    private final TypeService typeService;

        public TripController(TripService tripService,
                              PictureService pictureService,
                              TypeService typeService) {
        this.tripService = tripService;
        this.pictureService = pictureService;
        this.typeService = typeService;
    }

    @ModelAttribute
    TripAddDto tripAddDto() {
        return new TripAddDto();
    }

    @ModelAttribute("uploadPictureDto")
    UploadPictureDto uploadPictureDto() {
        return new UploadPictureDto();
    }

    @ModelAttribute("allTypes")
    List<TripTypeEnum> allTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/all")
    public String allTrips(Model model,
                           @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithAllTrips = tripService.findAllTripViewModels(pageable);
        model.addAttribute("trips", pagesWithAllTrips);
        model.addAttribute("heading", String.format("All trips (%s)", pagesWithAllTrips.getTotalElements()));
        model.addAttribute("url", "/trips/all");
        return "all-trips";
    }

    @GetMapping("/asian")
    public String asianTrips(Model model,
                                    @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithAsianTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.ASIA, pageable);
        model.addAttribute("trips", pagesWithAsianTrips);
        model.addAttribute("heading", String.format("Asian trips (%s)", pagesWithAsianTrips.getTotalElements()));
        model.addAttribute("url", "/trips/asian");
        return "all-trips";
    }

    @GetMapping("/african")
    public String africanTrips(Model model,
                             @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithAfricanTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.AFRICA, pageable);
        model.addAttribute("trips", pagesWithAfricanTrips);
        model.addAttribute("heading", String.format("African trips (%s)", pagesWithAfricanTrips.getTotalElements()));
        model.addAttribute("url", "/trips/african");
        return "all-trips";
    }

    @GetMapping("/north-american")
    public String northAmericanTrips(Model model,
                               @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithNorthAmericanTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.NORTH_AMERICA, pageable);
        model.addAttribute("trips", pagesWithNorthAmericanTrips);
        model.addAttribute("heading", String.format("North American trips (%s)", pagesWithNorthAmericanTrips.getTotalElements()));
        model.addAttribute("url", "/trips/north-american");
        return "all-trips";
    }

    @GetMapping("/south-american")
    public String southAmericanTrips(Model model,
                                     @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithSouthAmericanTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.SOUTH_AMERICA, pageable);
        model.addAttribute("trips", pagesWithSouthAmericanTrips);
        model.addAttribute("heading", String.format("South American trips (%s)", pagesWithSouthAmericanTrips.getTotalElements()));
        model.addAttribute("url", "/trips/south-american");
        return "all-trips";
    }

    @GetMapping("/antarctican")
    public String antarcticanTrips(Model model,
                                   @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithAntarcticanTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.ANTARCTICA, pageable);
        model.addAttribute("trips", pagesWithAntarcticanTrips);
        model.addAttribute("heading", String.format("Antarctican trips (%s)", pagesWithAntarcticanTrips.getTotalElements()));
        model.addAttribute("url", "/trips/antarctican");
        return "all-trips";
    }

    @GetMapping("/european")
    public String europeanTrips(Model model,
                                   @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithEuropeanTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.EUROPE, pageable);
        model.addAttribute("trips", pagesWithEuropeanTrips);
        model.addAttribute("heading", String.format("European trips (%s)", pagesWithEuropeanTrips.getTotalElements()));
        model.addAttribute("url", "/trips/european");
        return "all-trips";
    }

    @GetMapping("/australian")
    public String australianTrips(Model model,
                                @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {
        Page<TripViewModel> pagesWithAustralianTrips = tripService.findAllFilteredTripViewModels(ContinentEnum.AUSTRALIA, pageable);
        model.addAttribute("trips", pagesWithAustralianTrips);
        model.addAttribute("heading", String.format("Australian trips (%s)", pagesWithAustralianTrips.getTotalElements()));
        model.addAttribute("url", "/trips/australian");
        return "all-trips";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String addTrip() {
        return "add-trip";
    }

    @PostMapping("/add")
    public String addTripConfirm(@Valid TripAddDto tripAddDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal MyUserDetails myUserDetails) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("tripAddDto", tripAddDto)
                    .addFlashAttribute("org.springframework.validation.BindingResult.tripAddDto"
                            , bindingResult);

            return "redirect:/trips/add";
        }
        Long tripId = tripService.addTrip(tripAddDto, myUserDetails);
        return "redirect:/trips/details/" + tripId;
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("trip", tripService.findTripDetailsViewModelById(id, principal != null ? principal.getName() : ""));
        return "trip-details";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/details/{id}/picture/add")
    public String addPicture(UploadPictureDto uploadPictureDto,
                             @PathVariable Long id,
                             @AuthenticationPrincipal MyUserDetails principal) {

        pictureService.createAndSavePictureEntity(principal.getId(), uploadPictureDto.getPicture(), id);
        return "redirect:/trips/details/" + id;
    }

    @PreAuthorize("isAuthenticated() && @pictureServiceImpl.isOwner(#principal.name, #pictureId)")
    @DeleteMapping("/details/{tripId}/picture/delete")
    public String deletePicture(@PathVariable("tripId") Long tripId,
                                @RequestParam("pictureId") Long pictureId,
                                Principal principal) {
        pictureService.deletePicture(pictureId);
        return "redirect:/trips/details/" + tripId;
    }

    @PreAuthorize("isAuthenticated() && @tripServiceImpl.isOwner(#principal.name, #tripId)")
    @GetMapping("/edit/{id}")
    public String editTrip(
            Principal principal,
            @PathVariable("id") Long tripId,
            Model model) {

        if (!model.containsAttribute("tripEditDto")) {
            TripEditDto tripEditDto = tripService.getTripEditDetails(tripId);
            model.addAttribute("tripEditDto", tripEditDto);
        }
        return "trip-update";
    }

    @PutMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid TripEditDto tripEditDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("tripEditDto", tripEditDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tripEditDto", bindingResult);
            return "redirect:/trips/edit/" + id;
        }

        tripService.updateTripById(tripEditDto, id, userDetails);
        return "redirect:/trips/details/" + id;
    }

    @PreAuthorize("isAuthenticated() && @tripServiceImpl.isOwner(#principal.name, #tripId)")
    @DeleteMapping("/delete/{id}")
    public String deleteTrip(
            Principal principal,
            @PathVariable("id") Long tripId) {
        tripService.deleteTripById(tripId);
        return "redirect:/trips/all";
    }

    @GetMapping("/search")
    public String searchQuery(@RequestParam Map<String, String> queryParams,
                              HttpServletRequest request,
                              @Valid TripSearchDto tripSearchDto,
                              BindingResult bindingResult,
                              Model model,
                              @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 8) Pageable pageable) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tripSearchDto", tripSearchDto);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.tripSearchDto",
                    bindingResult);
            return "trip-search";
        }

        if (!model.containsAttribute("tripSearchDto")) {
            model.addAttribute("tripSearchDto", tripSearchDto);
            model.addAttribute("result", tripSearchDto.toString());
        }

        if (!tripSearchDto.isEmpty()) {
            model.addAttribute("trips", tripService.searchTrip(tripSearchDto, pageable));
            model.addAttribute("result", tripSearchDto.toString());

            String queryString = queryParams.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("page") && !entry.getKey().equals("size"))
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            String url = request.getRequestURL().toString() + (queryString.isEmpty() ? "" : "?" + queryString);
            model.addAttribute("url", url);
        }
        return "trip-search";
    }
}
