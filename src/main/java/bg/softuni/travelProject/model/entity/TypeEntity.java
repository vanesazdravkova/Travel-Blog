package bg.softuni.travelProject.model.entity;

import bg.softuni.travelProject.model.enums.TripTypeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class TypeEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TripTypeEnum name;

    public TripTypeEnum getName() {
        return name;
    }

    public TypeEntity setName(TripTypeEnum name) {
        this.name = name;
        return this;
    }
}
