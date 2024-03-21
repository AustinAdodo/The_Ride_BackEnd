package the_ride.the_ride_backend.Models;

import the_ride.the_ride_backend.Utiities.DriverStatus;

import java.util.List;

public class Driver extends UserBaseModel {
    public int TotalTrips;
    public String RegistrationStatus = "Active";
    public List<Trip> AllTrips;
    public DriverStatus TripStatus = DriverStatus.Free;

}
