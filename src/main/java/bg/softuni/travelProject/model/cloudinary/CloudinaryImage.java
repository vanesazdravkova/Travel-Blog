package bg.softuni.travelProject.model.cloudinary;

public class CloudinaryImage {

    private String url;
    String pubicId;

    public String getUrl() {
        return url;
    }

    public CloudinaryImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return pubicId;
    }

    public CloudinaryImage setPublicId(String publicId) {
        this.pubicId = publicId;
        return this;
    }
}
