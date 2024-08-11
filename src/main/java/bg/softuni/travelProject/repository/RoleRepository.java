package bg.softuni.travelProject.repository;

import bg.softuni.travelProject.model.entity.RoleEntity;
import bg.softuni.travelProject.model.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(RoleNameEnum role);
}
