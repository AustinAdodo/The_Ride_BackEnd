package the_ride.the_ride_backend.Models.Driver;

import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Utiities.DriverStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Driver extends UserBaseModel<UUID> {
    public int TotalTrips;
    public int VehicleTypeName;
    public String TaxID;
    public BigDecimal totalEarnings = BigDecimal.ZERO;
    public String VehicleColor;
    public String VehiclePlateNumber;
    public String VehicleRegistrationStatus = "Complete";
    public String RegistrationStatus = "Active";
    public List<Trip> AllTrips;
    public Double currentLongitude;
    public Double currentLatitude;
    public DriverStatus TripStatus = DriverStatus.Free;

    public Driver() {
        super("driver");
    }

    public Driver(String firstname, String lastname, String address, Double Long,
                  Double latitude,
                  String vehicleColor) {
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.Address = address;
        this.currentLongitude = Long;
        this.currentLatitude =latitude;
        this.VehicleColor = vehicleColor;
    }


    public double getLatitude() {
        return this.currentLatitude;
    }

    public double getLongitude() {
        return this.currentLongitude;
    }
}
