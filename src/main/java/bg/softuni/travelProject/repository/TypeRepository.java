package bg.softuni.travelProject.repository;

import bg.softuni.travelProject.model.entity.TypeEntity;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

    Optional<TypeEntity> findByName(TripTypeEnum name);
}
