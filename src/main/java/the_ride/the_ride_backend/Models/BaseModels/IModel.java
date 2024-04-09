package the_ride.the_ride_backend.Models.BaseModels;

/**
* Base Primary key is of type Integer, Long , Short or Guid
*/
public interface IModel<PrimaryKey> {
    PrimaryKey getId();
    void setId(PrimaryKey id);
}
