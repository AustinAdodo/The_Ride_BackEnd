package the_ride.the_ride_backend.Models.BaseModels;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * annotations @JsonTypeInfo and @JsonSubTypes are used for handling polymorphic types during serialization
 * and deserialization.
 * <p>
 * Also Note: '@Inheritance'('strategy' = InheritanceType.TABLE_PER_CLASS)' wasn't used  because it rendered
 * an incomplete database schema mapping to child classes.
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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class UserBaseModel<T extends Serializable> extends BaseModel<T> {
    @NotNull
    @Size(min = 1)
    @Column(name = "firstName", nullable = false)
    public String firstName;
    @NotNull @Size(min = 1)
    @Column(name = "lastName", nullable = false)
    public String lastName;
    public String photograph;
    public String sex;
    public Integer Rating;
    public String middleName;
    public int TotalTrips;
    public String password;
    public String email;
    private String Username;
    public LocalDate DateOfBirth;
    public String status;
    public String address;
    public String Category;
    public String Usertype;
    public String detectedUsertype;
    public String currentLongitude;
    public String currentLatitude;

    public UserBaseModel(String category) {
        this.Category = category;
    }

    public String getUsername() {
        return this.Username != null ? this.Username : this.email;
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
        this.password = password;
    }

    public void setName(String name) {
        this.firstName = name.split(" ")[0].trim();
        this.lastName = name.split(" ")[1].trim();
    }

    public String getFullName() {
        return this.firstName.trim() + " " + this.lastName.trim();
    }
}
