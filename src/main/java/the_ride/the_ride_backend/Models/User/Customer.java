package the_ride.the_ride_backend.Models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Utiities.BooleanString;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "customers")
@AttributeOverride(name = "id", column = @Column(name = "ID", insertable = false, updatable = false))
public class Customer extends UserBaseModel<UUID> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @PrePersist
    protected void ensureIdAssigned() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @BooleanString
    @Column(name = "is_only_my_sex_allowed")
    public String isOnlyMySexAllowed = "false";

    @Column(name = "default_home_address")
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

    public void setIsOnlyMySexAllowed(String aFalse) {
        this.isOnlyMySexAllowed = aFalse;
    }

    /*
blacklisted drivers
    @ElementCollection(fetch = FetchType.LAZY)
    public List<UUID> BlackListedDriversIds;
    //@PrePersist, @PostLoad, @PreUpdate
*/
}
