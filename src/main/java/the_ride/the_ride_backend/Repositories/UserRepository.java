package the_ride.the_ride_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.Models.User.Customer;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Customer, UUID> {
}