package bg.softuni.travelProject.model.view;

import java.time.LocalDateTime;

public class StatisticsViewModel {

    private long allTrips;
    private long asianTrips;
    private long africanTrips;
    private long northAmericanTrips;
    private long southAmericanTrips;
    private long antarcticanTrips;
    private long europeanTrips;
    private long australianTrips;
    private long usersCount;
    private LocalDateTime localDateTime;

    public long getAllTrips() {
        return allTrips;
    }

    public StatisticsViewModel setAllTrips(long allTrips) {
        this.allTrips = allTrips;
        return this;
    }

    public long getAsianTrips() {
        return asianTrips;
    }

    public StatisticsViewModel setAsianTrips(long asianTrips) {
        this.asianTrips = asianTrips;
        return this;
    }

    public long getAfricanTrips() {
        return africanTrips;
    }

    public StatisticsViewModel setAfricanTrips(long africanTrips) {
        this.africanTrips = africanTrips;
        return this;
    }

    public long getNorthAmericanTrips() {
        return northAmericanTrips;
    }

    public StatisticsViewModel setNorthAmericanTrips(long northAmericanTrips) {
        this.northAmericanTrips = northAmericanTrips;
        return this;
    }

    public long getSouthAmericanTrips() {
        return southAmericanTrips;
    }

    public StatisticsViewModel setSouthAmericanTrips(long southAmericanTrips) {
        this.southAmericanTrips = southAmericanTrips;
        return this;
    }

    public long getAntarcticanTrips() {
        return antarcticanTrips;
    }

    public StatisticsViewModel setAntarcticanTrips(long antarcticanTrips) {
        this.antarcticanTrips = antarcticanTrips;
        return this;
    }

    public long getEuropeanTrips() {
        return europeanTrips;
    }

    public StatisticsViewModel setEuropeanTrips(long europeanTrips) {
        this.europeanTrips = europeanTrips;
        return this;
    }

    public long getAustralianTrips() {
        return australianTrips;
    }

    public StatisticsViewModel setAustralianTrips(long australianTrips) {
        this.australianTrips = australianTrips;
        return this;
    }

    public long getUsersCount() {
        return usersCount;
    }

    public StatisticsViewModel setUsersCount(long usersCount) {
        this.usersCount = usersCount;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public StatisticsViewModel setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }
}
