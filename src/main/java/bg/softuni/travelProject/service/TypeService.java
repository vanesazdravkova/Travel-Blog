package bg.softuni.travelProject.service;

import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.enums.TripTypeEnum;

import java.util.List;

public interface TypeService {

    TypeEntity findByTypeName(TripTypeEnum tripTypeEnum);

    List<TripTypeEnum> getAllTypes();
}
