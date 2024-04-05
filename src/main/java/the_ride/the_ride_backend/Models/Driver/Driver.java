package the_ride.the_ride_backend.Models.Driver;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Utiities.DriverStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Driver extends UserBaseModel<UUID> {
    @jakarta.persistence.Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String carModel;
    public String TaxID;

    public BigDecimal totalEarnings = BigDecimal.ZERO;
    public String carColor;
    public String VehiclePlateNumber;
    public String VehicleRegistrationStatus = "Complete";
    public String RegistrationStatus = "Active";
    public Double currentLongitude;
    public Double currentLatitude;
    public DriverStatus TripStatus = DriverStatus.Free;

    public Driver() {
        super("driver");
    }

    public Driver(String firstname, String lastname, String address, Double Long,
                  Double latitude,
                  String vehicleColor) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.currentLongitude = Long;
        this.currentLatitude =latitude;
        this.carColor = vehicleColor;
    }

    public double getLatitude() {
        return this.currentLatitude;
    }

    public double getLongitude() {
        return this.currentLongitude;
    }

}
