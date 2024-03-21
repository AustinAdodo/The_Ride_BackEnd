package the_ride.the_ride_backend.Models.BaseModels;

import lombok.Getter;

import java.util.Date;


@Getter
public class UserBaseModel<T> extends BaseModel<T> {
    public String Firstname;
    public Integer Rating;
    public String Middlename;
    public String Lastname;
    public Date DateOfBirth;
    public String status;
    public String Address;
    public String Category;
    public UserBaseModel() {
    }
    public UserBaseModel(String category) {
        this.Category = category;
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