package bg.softuni.travelProject.model.entity;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trips")
public class TripEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 2000)
    private String landmarks;
    @Column(nullable = false, length = 2000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PricingLevelEnum pricingLevel;
    @ManyToOne(optional = false)
    private UserEntity author;
    @Column
    private String videoUrl;
    @OneToMany(mappedBy = "trip")
    private List<PictureEntity> pictures;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<TypeEntity> types;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContinentEnum continent;
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @Column(nullable = false)
    private Integer days;
    @Column(nullable = false)
    private Integer countriesVisited;
    @ManyToMany(mappedBy = "favorites")
    private List<UserEntity> favoriteUsers;

    public TripEntity() {
    }

    public String getName() {
        return name;
    }

    public TripEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getLandmarks() {
        return landmarks;
    }

    public TripEntity setLandmarks(String landmarks) {
        this.landmarks = landmarks;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TripEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public PricingLevelEnum getPricingLevel() {
        return pricingLevel;
    }

    public TripEntity setPricingLevel(PricingLevelEnum pricingLevel) {
        this.pricingLevel = pricingLevel;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public TripEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public TripEntity setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public List<PictureEntity> getPictures() {
        return pictures;
    }

    public TripEntity setPictures(List<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public List<TypeEntity> getTypes() {
        return types;
    }

    public TripEntity setTypes(List<TypeEntity> types) {
        this.types = types;
        return this;
    }

    public ContinentEnum getContinent() {
        return continent;
    }

    public TripEntity setContinent(ContinentEnum continent) {
        this.continent = continent;
        return this;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public TripEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public Integer getDays() {
        return days;
    }

    public TripEntity setDays(Integer days) {
        this.days = days;
        return this;
    }

    public Integer getCountriesVisited() {
        return countriesVisited;
    }

    public TripEntity setCountriesVisited(Integer countriesVisited) {
        this.countriesVisited = countriesVisited;
        return this;
    }

    public List<UserEntity> getFavoriteUsers() {
        return favoriteUsers;
    }

    public TripEntity setFavoriteUsers(List<UserEntity> favoriteUsers) {
        this.favoriteUsers = favoriteUsers;
        return this;
    }
}

