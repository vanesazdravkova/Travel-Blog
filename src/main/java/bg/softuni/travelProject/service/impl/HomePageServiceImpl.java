package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.dto.HomePageDto;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.model.view.PictureHomePageViewModel;
import bg.softuni.travelProject.service.HomePageService;
import bg.softuni.travelProject.service.PictureService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    private final PictureService pictureService;

    public HomePageServiceImpl(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    public HomePageDto initHomePageDto(){
        List<PictureHomePageViewModel> pictures;
        String message = "";

        LocalTime now = LocalTime.now();
        if (now.getHour() == 0 || now.getHour() == 12) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.BEACH);
            message = "You want a trip to the beach? Read about some interesting beach vacations!";
        } else if (now.getHour() == 1 || now.getHour() == 13) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.MOUNTAIN);
            message = "You want a trip in the mountains? Read about some interesting mountain vacations!";
        } else if (now.getHour() == 2 || now.getHour() == 14) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.CITY);
            message = "You want a city trip? Read about some interesting cities to visit!";
        } else if (now.getHour() == 3 || now.getHour() == 15) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.SAFARI);
            message = "You want to experience safari? Read about some interesting safari adventures!";
        } else if (now.getHour() == 4 || now.getHour() == 16) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.CRUISE);
            message = "You want a cruise vacation? Read about some interesting cruises!";
        } else if (now.getHour() == 5 || now.getHour() == 17) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.ADVENTURE);
            message = "You want a to live your adventure of a lifetime? Read about some cool adventures!";
        } else if (now.getHour() == 6 || now.getHour() == 18) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.FOREST);
            message = "You want a trip through the woods? Read about some interesting forest adventures!";
        } else if (now.getHour() == 7 || now.getHour() == 19) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.CULTURAL);
            message = "You want to take a cultural vacation? Read about some interesting places to visit!";
        } else if (now.getHour() == 8 || now.getHour() == 20) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.HISTORICAL);
            message = "You want to visit historical landmarks? Read about some places full of history!";
        } else if (now.getHour() == 9 || now.getHour() == 21) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.SKI);
            message = "You want a ski vacation? Read about some exciting ski resorts!";
        } else if (now.getHour() == 10 || now.getHour() == 22) {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.DESERT);
            message = "You want to wander through the desert? Read about some mystical deserts!";
        } else {
            pictures = this.pictureService
                    .getThreeRandomPicturesByTripType(TripTypeEnum.ISLAND);
            message = "You want to go to a tropical island? Read about some island vacations!";
        }

        return new HomePageDto().setMessage(message).setPictures(pictures);
    }
}