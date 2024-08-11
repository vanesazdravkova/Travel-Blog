package bg.softuni.travelProject.repository;

import bg.softuni.travelProject.model.entity.PictureEntity;
import bg.softuni.travelProject.model.entity.TypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    Page<PictureEntity> findAllByAuthor_Username(String username, Pageable pageable);

    @Query("SELECT p FROM PictureEntity p JOIN p.trip.types t WHERE :typeEntity MEMBER OF p.trip.types")
    List<PictureEntity> findAllPicturesByTripType(TypeEntity typeEntity);

    void deleteAllById(Long id);
}
