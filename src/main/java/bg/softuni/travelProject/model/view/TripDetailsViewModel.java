package bg.softuni.travelProject.model.view;

import bg.softuni.travelProject.model.entity.CommentEntity;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;

import java.util.List;

public class TripDetailsViewModel {

    private Long id;
    private String name;
    private List<String> landmarks;
    private String description;
    private PricingLevelEnum pricingLevelEnum;
    private String author;
    private String videoId;
    private List<PictureViewModel> pictures;
    private List<CommentEntity> comments;
    private Integer days;
    private Integer countriesVisited;
    private ContinentEnum continent;
    private boolean canDelete;
    boolean isFavorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TripDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<String> landmarks) {
        this.landmarks = landmarks;
    }

    public String getDescription() {
        return description;
    }

    public TripDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public PricingLevelEnum getPricingLevelEnum() {
        return pricingLevelEnum;
    }

    public TripDetailsViewModel setPricingLevelEnum(PricingLevelEnum pricingLevelEnum) {
        this.pricingLevelEnum = pricingLevelEnum;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public TripDetailsViewModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getVideoId() {
        return videoId;
    }

    public TripDetailsViewModel setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public List<PictureViewModel> getPictures() {
        return pictures;
    }

    public TripDetailsViewModel setPictures(List<PictureViewModel> pictures) {
        this.pictures = pictures;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public TripDetailsViewModel setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
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

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public TripDetailsViewModel setIsFavorite(boolean favorite) {
        isFavorite = favorite;
        return this;
    }
}
