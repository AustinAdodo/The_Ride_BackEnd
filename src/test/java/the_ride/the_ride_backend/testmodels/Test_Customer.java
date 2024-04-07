package the_ride.the_ride_backend.testmodels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import the_ride.the_ride_backend.Models.User.Customer;

import java.util.UUID;

@Entity
@Table(name = "testCustomers")
public class Test_Customer extends Customer {
    public Test_Customer() {}
    public Test_Customer(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
