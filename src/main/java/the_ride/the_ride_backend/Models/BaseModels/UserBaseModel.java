package the_ride.the_ride_backend.Models.BaseModels;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;

/**
 * annotations @JsonTypeInfo and @JsonSubTypes are used for handling polymorphic types during serialization
 * and deserialization
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "Usertype")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Customer.class, name = "Customer"),
        @JsonSubTypes.Type(value = Driver.class, name = "Driver")
})

@Getter
public abstract class UserBaseModel<T extends Serializable> extends BaseModel<T> {
    public String firstName;
    public String photourl;
    public String sex;
    public Integer Rating;
    public String middleName;
    public String lastName;
    public int TotalTrips;
    public String Password;
    public String email;
    private String Username;
    public Date DateOfBirth;
    public String status;
    public String address;
    public String Category;
    public String Usertype;
    public String currentLongitude;
    public String currentLatitude;

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

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setName(String name) {
        this.firstName = name.split(" ")[0];
        this.lastName = name.split(" ")[1];
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
