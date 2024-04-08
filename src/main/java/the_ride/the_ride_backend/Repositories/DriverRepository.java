package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.Models.Driver.Driver;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//import java.util.stream.Collectors;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

//    @Query("SELECT d FROM Driver d WHERE d.latitude BETWEEN :minLat AND :maxLat " +
//            "AND d.longitude BETWEEN :minLon AND :maxLon")
//    List<Driver> findDriversInBoundingBoxOptional(@Param("minLat") double minLat,
//                                           @Param("maxLat") double maxLat,
//                                           @Param("minLon") double minLon,
//                                           @Param("maxLon") double maxLon);

    public default List<Driver> findDriversInBoundingBox(@Param("minLat") double minLat,
                                                         @Param("maxLat") double maxLat,
                                                         @Param("minLon") double minLon,
                                                         @Param("maxLon") double maxLon) {
        List<Driver> sampleDrivers = new ArrayList<>();
        Driver driver1 = new Driver("John", "Smith", "123 Main St", 6.4215, 3.3415, "red");
        Driver driver2 = new Driver("Alice", "Murray", "456 Oak Ave", 6.4215, 3.3415, "yellow");
        Driver driver3 = new Driver("Austin", "Adodo", "789 Elm St", 6.4215, 3.3415, "blue");

        sampleDrivers.add(driver1);
        sampleDrivers.add(driver2);
        sampleDrivers.add(driver3);
        List<Driver> result = sampleDrivers.stream()
                .filter(driver -> driver.getLatitude() >= minLat && driver.getLatitude() <= maxLat &&
                        driver.getLongitude() >= minLon && driver.getLongitude() <= maxLon)
                .toList();
        return result;
    }
}
