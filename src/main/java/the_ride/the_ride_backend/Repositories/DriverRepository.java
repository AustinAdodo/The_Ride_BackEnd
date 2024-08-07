package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.Models.Driver.Driver;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import the_ride.the_ride_backend.Models.User.Customer;

import java.util.List;
//import java.util.stream.Collectors;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Optional<Driver> findByUsername(String username);

    public default List<Driver> findDriversInBoundingBox(@Param("minLat") double minLat,
                                                         @Param("maxLat") double maxLat,
                                                         @Param("minLon") double minLon,
                                                         @Param("maxLon") double maxLon) {
        List<Driver> sampleDrivers = new ArrayList<>();
        Driver driver1 = new Driver("John", "Smith", "123 Main St", "6.4215", "3.3415", "red");
        Driver driver2 = new Driver("Alice", "Murray", "456 Oak Ave", "6.4215", "3.3415", "yellow");
        Driver driver3 = new Driver("Austin", "Adodo", "789 Elm St", "6.4215", "3.3415", "blue");

        sampleDrivers.add(driver1);
        sampleDrivers.add(driver2);
        sampleDrivers.add(driver3);
        return sampleDrivers.stream()
                .filter(driver -> Double.parseDouble(driver.getCurrentLatitude()) >= minLat &&
                        Double.parseDouble(driver.getCurrentLatitude()) <= maxLat &&
                        Double.parseDouble(driver.getCurrentLongitude()) >= minLon &&
                        Double.parseDouble(driver.getCurrentLongitude()) <= maxLon)
                .toList();
    }
}
