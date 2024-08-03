package bg.softuni.travelProject.model.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles;
    @OneToMany(mappedBy = "author")
    private List<TripEntity> addedTrips;
    @Column
    private boolean accountVerified;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdOn;
    @ManyToMany()
    @JoinTable(
            name = "user_favorite_trips",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<TripEntity> favorites;
    @OneToMany(mappedBy = "author")
    private List<PictureEntity> addedPictures;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public List<TripEntity> getAddedTrips() {
        return addedTrips;
    }

    public UserEntity setAddedTrips(List<TripEntity> addedTrips) {
        this.addedTrips = addedTrips;
        return this;
    }

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public UserEntity setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
        return this;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public UserEntity setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public List<TripEntity> getFavorites() {
        return favorites;
    }

    public UserEntity setFavorites(List<TripEntity> favorites) {
        this.favorites = favorites;
        return this;
    }

    public List<PictureEntity> getAddedPictures() {
        return addedPictures;
    }

    public UserEntity setAddedPictures(List<PictureEntity> addedPictures) {
        this.addedPictures = addedPictures;
        return this;
    }
}
