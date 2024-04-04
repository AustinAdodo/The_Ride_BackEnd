package the_ride.the_ride_backend.Models.Admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

/**
 * For internal Users
 */

@Entity
public class BackEndUser extends UserBaseModel<Integer> {
    @jakarta.persistence.Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
    public BackEndUser() {
        super("superUser");
    }
}
