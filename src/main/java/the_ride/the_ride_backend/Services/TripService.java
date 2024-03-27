package the_ride.the_ride_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.TripRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final DriverService _driverService;
    private final BookingService _bookingService;
    private final Customer currentCustomer;
    private final Driver currentDriver;
    private Trip CurrentTrip;

    @Autowired
    public TripService(BookingService bookingService, DriverService driverService, BookingService bookingService1) {
        _driverService = driverService;
        _bookingService = bookingService1;
        tripRepository = null;
        currentCustomer = _bookingService.GetCurrentCustomer();
        currentDriver = _bookingService.GetChosenDriverDetails();
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
