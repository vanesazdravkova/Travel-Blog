package bg.softuni.travelProject.model.view;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;

public class TripViewModel {

    private Long id;
    private String name;
    private PricingLevelEnum pricingLevel;
    private ContinentEnum continent;
    private String author;
    private String pictureUrl;
    private int days;
    private int countriesVisited;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TripViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public PricingLevelEnum getPricingLevel() {
        return pricingLevel;
    }

    public TripViewModel setPricingLevel(PricingLevelEnum pricingLevel) {
        this.pricingLevel = pricingLevel;
        return this;
    }

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public String getAuthor() {
        return author;
    }

    public TripViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public TripViewModel setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public int getDays() {
        return days;
    }

    public TripViewModel setDays(int days) {
        this.days = days;
        return this;
    }

    public int getCountriesVisited() {
        return countriesVisited;
    }

    public TripViewModel setCountriesVisited(int countriesVisited) {
        this.countriesVisited = countriesVisited;
        return this;
    }
}
