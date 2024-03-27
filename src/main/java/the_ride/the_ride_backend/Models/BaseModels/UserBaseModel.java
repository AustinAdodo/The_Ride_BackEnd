package the_ride.the_ride_backend.Models.BaseModels;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class UserBaseModel<T extends Serializable> extends BaseModel<T> {
    public String Firstname;
    public Integer Rating;
    public String Middlename;
    public String Lastname;
    public String email;
    private String Username;
    public Date DateOfBirth;
    public String status;
    public String Address;
    public String Category;

    public UserBaseModel() {
    }

    public UserBaseModel(String category) {
        this.Category = category;
    }

    public String getUsername() {
        return this.Username != null ? this.Username : this.email;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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
