package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import the_ride.the_ride_backend.Models.Driver.Driver;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
}