package the_ride.the_ride_backend.Models.BaseModels;

//import javax.persistence.Column;
import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseModel<PrimaryKey extends Serializable> implements IModel<PrimaryKey> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected PrimaryKey id;


    public abstract PrimaryKey getId();

    public abstract void setId(PrimaryKey id);
}
