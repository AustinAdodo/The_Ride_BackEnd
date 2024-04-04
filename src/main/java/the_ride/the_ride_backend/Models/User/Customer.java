package the_ride.the_ride_backend.Models.User;

import jakarta.persistence.*;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Utiities.BooleanString;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer extends UserBaseModel<UUID> {
    @jakarta.persistence.Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @BooleanString
    public String isOnlyMySexAllowed;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String DefaultHomeAddress = this.address;

    public Customer(String firstname, String lastname) {
        super();
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public Customer() {
        super("customer");
    }

    //blacklisted drivers
    @ElementCollection(fetch = FetchType.LAZY)
    public List<UUID> BlackListedDriversIds;
}
