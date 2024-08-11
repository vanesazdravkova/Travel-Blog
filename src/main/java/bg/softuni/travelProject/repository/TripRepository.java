package bg.softuni.travelProject.repository;

import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.enums.ContinentEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long>,
        JpaSpecificationExecutor<TripEntity> {

    Page<TripEntity> findAllByContinent(ContinentEnum continentEnum, Pageable pageable);

    Page<TripEntity> findAllByAuthor_Id(Long authorId, Pageable pageable);

    @Query("SELECT t FROM TripEntity t JOIN t.favoriteUsers u WHERE u.id = :userId")
    Page<TripEntity> findAllFavoriteTrips(@Param("userId") Long userId, Pageable pageable);

    long countTripEntitiesByContinent(ContinentEnum continent);
}
