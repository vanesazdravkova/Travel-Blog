package bg.softuni.travelProject.model.dto;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

public class TripEditDto {

    Long id;
    @NotEmpty
    @Size(min = 5, max = 30)
    private String name;
    @NotEmpty
    private String landmarks;
    @NotEmpty
    private String description;
    @NotNull
    private PricingLevelEnum pricingLevel;
    private String videoUrl;
    private List<TripTypeEnum> types;
    @NotNull
    private ContinentEnum continent;
    @NotNull
    @Positive
    private Integer days;
    @NotNull
    @Positive
    private Integer countriesVisited;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(String landmarks) {
        this.landmarks = landmarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PricingLevelEnum getPricingLevel() {
        return pricingLevel;
    }

    public void setPricingLevel(PricingLevelEnum pricingLevel) {
        this.pricingLevel = pricingLevel;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<TripTypeEnum> getTypes() {
        return types;
    }

    public void setTypes(List<TripTypeEnum> types) {
        this.types = types;
    }

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getCountriesVisited() {
        return countriesVisited;
    }

    public void setCountriesVisited(Integer countriesVisited) {
        this.countriesVisited = countriesVisited;
    }
}
