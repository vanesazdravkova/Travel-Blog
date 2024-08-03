package bg.softuni.travelProject.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    @Column(nullable = false)
    private String url;
    @ManyToOne(optional = false)
    private UserEntity author;
    @Column
    private String publicId;
    @Column
    private String title;
    @ManyToOne()
    private TripEntity trip;

    public PictureEntity() {
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public PictureEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureEntity setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public PictureEntity setTrip(TripEntity trip) {
        this.trip = trip;
        return this;
    }
}
