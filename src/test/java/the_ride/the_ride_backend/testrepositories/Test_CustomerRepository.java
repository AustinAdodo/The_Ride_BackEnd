package the_ride.the_ride_backend.testrepositories;

import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface Test_CustomerRepository extends JpaRepository<Test_Customer, UUID> {

}