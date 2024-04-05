package the_ride.the_ride_backend.Models.User;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Utiities.BooleanString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer extends UserBaseModel<UUID> {
    @jakarta.persistence.Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @BooleanString
    public String isOnlyMySexAllowed;

    public String DefaultHomeAddress = this.address;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

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
