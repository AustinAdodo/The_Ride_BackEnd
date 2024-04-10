package the_ride.the_ride_backend.Models.Driver;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Utiities.DriverStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "drivers")
public class Driver extends UserBaseModel<UUID> {
    @Id
    @GeneratedValue(generator = "UUID")
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
    public String RegistrationStatus = "Complete";
    public DriverStatus TripStatus = DriverStatus.Free;

    public Driver() {
        super("driver");
    }

    public Driver(String firstname, String lastname, String address, String Long,
                  String latitude,
                  String vehicleColor) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.address = address;
        this.currentLongitude = Long;
        this.currentLatitude = latitude;
        this.carColor = vehicleColor;
    }
}
