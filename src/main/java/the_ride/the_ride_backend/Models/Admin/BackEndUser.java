package the_ride.the_ride_backend.Models.Admin;

import jakarta.persistence.*;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

/**
 * For internal Users
 */

@Entity
@Table(name = "back_end_users")
@AttributeOverride(name = "id", column = @Column(name = "ID", insertable = false, updatable = false))
public class BackEndUser extends UserBaseModel<Integer> {
    @Id
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
