package bg.softuni.travelProject.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {

    @Column(nullable = false, length = 3000)
    private String textContent;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @ManyToOne(optional = false)
    private UserEntity author;
    @ManyToOne(optional = false)
    private TripEntity trip;

    public CommentEntity() {
    }

    public String getTextContent() {
        return textContent;
    }

    public CommentEntity setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public CommentEntity setTrip(TripEntity trip) {
        this.trip = trip;
        return this;
    }
}
