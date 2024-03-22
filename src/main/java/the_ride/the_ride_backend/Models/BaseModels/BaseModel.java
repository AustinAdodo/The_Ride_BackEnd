package the_ride.the_ride_backend.Models.BaseModels;

//import javax.persistence.Column;
import jakarta.persistence.*;

public abstract class BaseModel<PrimaryKey> implements IModel<PrimaryKey> {
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected PrimaryKey id;

    public abstract PrimaryKey getID();

    public abstract void setID(PrimaryKey id);
}
