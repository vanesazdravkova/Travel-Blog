package bg.softuni.travelProject.repository;

import bg.softuni.travelProject.model.dto.TripSearchDto;
import bg.softuni.travelProject.model.entity.TripEntity;
import bg.softuni.travelProject.model.enums.TripTypeEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TripSpecification implements Specification<TripEntity> {

    private final TripSearchDto tripSearchDto;

    public TripSpecification(TripSearchDto tripSearchDto) {
        this.tripSearchDto = tripSearchDto;
    }

    @Override
    public Predicate toPredicate(Root<TripEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        if (tripSearchDto.getName() != null && !tripSearchDto.getName().isEmpty()) {
            String searchName = "%" + tripSearchDto.getName().toLowerCase() + "%";
            p.getExpressions().add(
                    cb.and(cb.like(cb.lower(root.get("name")), searchName)));
        }

        if (tripSearchDto.getPricingLevel() != null) {
            p.getExpressions().add(
                    cb.equal(root.get("pricingLevel"), tripSearchDto.getPricingLevel()));
        }

        if (tripSearchDto.getContinent() != null) {
            p.getExpressions().add(
                    cb.equal(root.get("continent"), tripSearchDto.getContinent()));
        }

        if (tripSearchDto.getMinDays() != null) {
            p.getExpressions().add(
                    cb.and(cb.greaterThanOrEqualTo(root.get("days"), tripSearchDto.getMinDays())));
        }

        if (tripSearchDto.getMaxDays() != null) {
            p.getExpressions().add(
                    cb.and(cb.lessThanOrEqualTo(root.get("days"), tripSearchDto.getMaxDays())));
        }

        if (tripSearchDto.getTypes() != null && !tripSearchDto.getTypes().isEmpty()) {
            for (TripTypeEnum type : tripSearchDto.getTypes()) {
                p.getExpressions().add(
                        cb.equal(root.join("types").get("name"), type));
            }
        }
        return p;
    }
}
