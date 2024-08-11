package bg.softuni.travelProject.service.impl;

import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import bg.softuni.travelProject.repository.TypeRepository;
import bg.softuni.travelProject.service.TypeService;
import bg.softuni.travelProject.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public TypeEntity findByTypeName(TripTypeEnum tripTypeEnum) {
        return this.typeRepository.findByName(tripTypeEnum)
                .orElseThrow(() -> new ObjectNotFoundException("Type with name " + tripTypeEnum.name() + " not found!"));
    }

    @Override
    public List<TripTypeEnum> getAllTypes() {
        return this.typeRepository.findAll().stream().map(TypeEntity::getName).collect(Collectors.toList());
    }
}
