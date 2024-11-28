package the_ride.the_ride_backend.Models.BaseModels;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseModel<PrimaryKey extends Serializable> implements IModel<PrimaryKey> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected PrimaryKey id;

    public abstract PrimaryKey getId();

    public abstract void setId(PrimaryKey id);
}
