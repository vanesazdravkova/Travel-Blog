package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.entity.PictureEntity;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.model.view.PictureHomePageViewModel;
import bg.softuni.travelProject.model.view.PictureViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {

    Page<PictureViewModel> findAllPictureViewModelsByUsername(String principalName, Pageable pageable);

    PictureEntity createAndSavePictureEntity(Long userId, MultipartFile file, Long tripId);

    void deletePicture(Long id);

    boolean isOwner(String userName, Long pictureId);

    PictureViewModel mapToPictureViewModel(PictureEntity picture, String principalName);

    PictureHomePageViewModel mapToPictureHomePageViewModel(PictureEntity picture);

    List<PictureHomePageViewModel> getThreeRandomPicturesByTripType(TripTypeEnum tripTypeEnum);
}
