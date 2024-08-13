package bg.softuni.travelProject.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddFutureTripDto {

    @NotBlank(message = "The name cannot be empty.")
    private String name;
    @NotBlank(message = "The destination cannot be empty.")
    private String destination;
    @NotBlank(message = "The company cannot be empty.")
    private String company;
    @NotNull
    @Positive
    private Integer days;
    @NotNull
    @Positive
    private Integer price;

    public String getName() {
        return name;
    }

    public AddFutureTripDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public AddFutureTripDto setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public AddFutureTripDto setCompany(String company) {
        this.company = company;
        return this;
    }

    public Integer getDays() {
        return days;
    }

    public AddFutureTripDto setDays(Integer days) {
        this.days = days;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public AddFutureTripDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

}
