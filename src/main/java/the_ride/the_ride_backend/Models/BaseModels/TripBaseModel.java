package the_ride.the_ride_backend.Models.BaseModels;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TripBaseModel<T extends Serializable> extends BaseModel<T> {

    public LocalDate tripDate;
    public LocalDateTime startTime;
    public LocalDateTime  endTime;

    public TripBaseModel() {
    }

    public TripBaseModel(T id) {
        this.id = id;
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public void setId(T id) {
        this.id = id;
    }
}
