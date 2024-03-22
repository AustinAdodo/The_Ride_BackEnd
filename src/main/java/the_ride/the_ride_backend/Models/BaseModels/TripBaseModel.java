package the_ride.the_ride_backend.Models.BaseModels;

import java.util.Date;

public class TripBaseModel<T> extends BaseModel<T> {

    public Date tripDate;
    public Date startTime;
    public Date endTime;

    public TripBaseModel() {
    }

    public TripBaseModel(T id) {
        this.id = id;
    }

    @Override
    public T getID() {
        return id;
    }

    @Override
    public void setID(T id) {
        this.id = id;
    }
}
