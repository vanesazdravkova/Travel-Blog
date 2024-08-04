package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.cloudinary.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage uploadImage(MultipartFile multipartFile) throws IOException;

    boolean delete(String publicId);
}
