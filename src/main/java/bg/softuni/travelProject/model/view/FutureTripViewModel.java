package bg.softuni.travelProject.model.view;

public class FutureTripViewModel {

    private String id;
    private String name;
    private String destination;
    private String company;
    private Integer days;
    private Integer price;

    public String getId() {
        return id;
    }

    public FutureTripViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FutureTripViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public FutureTripViewModel setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public FutureTripViewModel setCompany(String company) {
        this.company = company;
        return this;
    }

    public Integer getDays() {
        return days;
    }

    public FutureTripViewModel setDays(Integer days) {
        this.days = days;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public FutureTripViewModel setPrice(Integer price) {
        this.price = price;
        return this;
    }

}