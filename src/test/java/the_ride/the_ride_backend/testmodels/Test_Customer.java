package the_ride.the_ride_backend.testmodels;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import the_ride.the_ride_backend.Models.User.Customer;

@Entity
@Table(name = "test_Customers")
public class Test_Customer extends Customer {
    public Test_Customer() {}

    public Test_Customer(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
