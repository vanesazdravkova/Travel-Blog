package bg.softuni.travelProject.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadPictureDto {
    private Long tripId;
    private MultipartFile picture;

    public Long getTripId() {
        return tripId;
    }

    public UploadPictureDto setTripId(Long tripId) {
        this.tripId = tripId;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public UploadPictureDto setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }
}
