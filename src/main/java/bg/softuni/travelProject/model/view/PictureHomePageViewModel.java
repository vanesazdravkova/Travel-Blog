package bg.softuni.travelProject.model.view;

public class PictureHomePageViewModel {

    private String url;
    private Long tripId;
    String authorFullName;

    public String getUrl() {
        return url;
    }

    public PictureHomePageViewModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public Long getTripId() {
        return tripId;
    }

    public PictureHomePageViewModel setTripId(Long tripId) {
        this.tripId = tripId;
        return this;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public PictureHomePageViewModel setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
        return this;
    }
}
