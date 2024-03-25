package the_ride.the_ride_backend.Models.Trip;

import the_ride.the_ride_backend.Models.BaseModels.TripBaseModel;

import java.util.UUID;

public class Trip extends TripBaseModel<UUID> {
    public Trip() {}
    public String driverName;
    public String customerName;
    public String origin;
    public String destination;
}
