package the_ride.the_ride_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.DriverRepository;
import the_ride.the_ride_backend.Repositories.TripRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private static final double EARTH_RADIUS_KM = 6371.0;
    private final TripRepository tripRepository;

    private final DriverRepository driverRepository;
    private final DriverService _driverService;
    private final Customer currentCustomer;
    private final Driver currentDriver;
    private Trip CurrentTrip;

    @Autowired
    public TripService(BookingService bookingService, DriverRepository driverRepository, DriverService driverService, BookingService bookingService1) {
        this.driverRepository = driverRepository;
        _driverService = driverService;
        tripRepository = null;
        currentCustomer = bookingService1.GetCurrentCustomer();
        currentDriver = bookingService1.GetChosenDriverDetails();
    }

    /**
     * Calculates Optimal distance in Radians between 2 points to Kilometres.
     *
     * @return double
     */
    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }


    /**
     * Calculates Optimal distance in Radians between 2 points to Kilometres.
     *
     * @return double(Radians)
     */

    public static double calculateSearchRadius(double centerLatitude, double centerLongitude, double radiusInKm) {
        final double earthRadius = 6371; // Earth's radius in kilometers
        double latitudeRadians = Math.toRadians(centerLatitude);
        double longitudeRadians = Math.toRadians(centerLongitude);

        return radiusInKm / earthRadius;
    }

    /**
     * @throws NumberFormatException}
     */

    public List<Driver> findDriversInProximity(double centerLatitude, double centerLongitude, double searchRadius) throws NumberFormatException {
        // get realtime locations (will contain location and driver ID).
        List<Driver> allDrivers = driverRepository.findAll();// This will be filtered.
        List<Driver> driversInRange = new ArrayList<>();
        for (Driver driver : allDrivers) {
            String latitudeStr = driver.getCurrentLatitude();
            String longitudeStr = driver.getCurrentLongitude();
            double driverLatitude = Double.parseDouble(latitudeStr);
            double driverLongitude = Double.parseDouble(longitudeStr);
            if (isWithinRadius(centerLatitude, centerLongitude, driverLatitude, driverLongitude, searchRadius)) {
                driversInRange.add(driver);
            }
        }
        return driversInRange;
    }

    private boolean isWithinRadius(double centerLatitude, double centerLongitude, double driverLatitude, double driverLongitude, double searchRadius) {
        // Haversine formula implementation to calculate distance in kilometers
        double distanceInKm = 1800;
        // ... (Haversine formula calculation)
        return distanceInKm <= searchRadius;
    }


    public Trip CancelTrip() {
        //retrieve current ongoing trip
        return new Trip();
    }

    public void StartTrip() {
        assert tripRepository != null;
        Trip currentTrip = new Trip();
        currentTrip.tripDate = LocalDate.now();
        currentTrip.startTime = LocalDateTime.now();
        currentTrip.endTime = null;
        tripRepository.save(currentTrip);
    }

    public Trip EndTrip(Trip tripDetails) {
        assert tripRepository != null;
        Optional<Trip> QueriedTrip = tripRepository.findById(tripDetails.getId());
        if (QueriedTrip.isPresent()) {
            Trip trip = QueriedTrip.get();
            trip.endTime = LocalDateTime.now();
            tripRepository.save(trip);
            _driverService.UnMapDriver(currentCustomer, currentDriver);
            return trip;
        } else {
            throw new IllegalArgumentException("Something went wrong, trip not found: ");
        }
    }
}
