package bg.softuni.travelProject.model.dto;

import bg.softuni.travelProject.model.enums.ContinentEnum;
import bg.softuni.travelProject.model.enums.PricingLevelEnum;
import bg.softuni.travelProject.model.enums.TripTypeEnum;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public class TripSearchDto {

    @Size(max = 30)
    private String name;
    private PricingLevelEnum pricingLevel;
    private List<TripTypeEnum> types;
    private ContinentEnum continent;
    @Positive
    private Integer minDays;
    @Positive
    private Integer maxDays;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PricingLevelEnum getPricingLevel() {
        return pricingLevel;
    }

    public void setPricingLevel(PricingLevelEnum pricingLevel) {
        this.pricingLevel = pricingLevel;
    }

    public List<TripTypeEnum> getTypes() {
        return types;
    }

    public void setTypes(List<TripTypeEnum> types) {
        this.types = types;
    }

    public ContinentEnum getContinent() {
        return continent;
    }

    public void setContinent(ContinentEnum continent) {
        this.continent = continent;
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }

    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                maxDays == null &&
                minDays == null &&
                pricingLevel == null &&
                continent == null &&
                (types == null || types.isEmpty());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (name != null && !name.isEmpty()) {
            sb.append(String.format("Name: " + name + " "));
        }

        if (pricingLevel != null) {
            sb.append(String.format("Level: " + pricingLevel + " "));
        }

        if (types != null && !types.isEmpty()) {
            List<String> typeNames = types.stream()
                    .map(TripTypeEnum::name)
                    .collect(Collectors.toList());

            String result = String.join(", ", typeNames);
            sb.append(String.format("Types: " + result + " "));
        }

        if (continent != null) {
            sb.append(String.format("Continent: " + continent + " "));
        }

        if (minDays != null) {
            sb.append(String.format("Minimum days to spend: " + minDays + " "));
        }

        if (maxDays != null) {
            sb.append(String.format("Max days to spend: " + maxDays + " "));
        }
        return sb.toString();
    }

}