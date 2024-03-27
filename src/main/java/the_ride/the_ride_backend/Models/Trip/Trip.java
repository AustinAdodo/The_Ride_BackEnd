package the_ride.the_ride_backend.Models.Trip;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import the_ride.the_ride_backend.Models.BaseModels.TripBaseModel;

import java.util.UUID;

@Entity
public class Trip extends TripBaseModel<UUID> {
    @jakarta.persistence.Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
    public Trip() {}
    public String driverName;
    public String customerName;
    public String origin;
    public String destination;
}
