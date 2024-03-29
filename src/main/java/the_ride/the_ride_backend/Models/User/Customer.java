package the_ride.the_ride_backend.Models.User;

import jakarta.persistence.*;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer extends UserBaseModel<UUID> {
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
    public String CurrentStreetName;

    public String DefaultHomeAddress = this.address;
    public Customer() {
        super("customer");
    }

    //blacklisted drivers
    @ElementCollection(fetch = FetchType.LAZY)
    public List<UUID> BlackListedDriversIds;
}
