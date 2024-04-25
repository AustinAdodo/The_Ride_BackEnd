package the_ride.the_ride_backend.Services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.DriverRepository;
import the_ride.the_ride_backend.Utiities.LocationUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DriverService {
    private final HashMap<UUID, UUID> BookingMapper = new HashMap<>();
    private final DriverRepository _driverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    private final MessagingService _messagingService;

    public DriverService(DriverRepository driverRepository, MessagingService messagingService) {
        _driverRepository = driverRepository;
        _messagingService = messagingService;
    }

    public void mapDriver(Customer customer, Driver driver) {
        BookingMapper.put(customer.getId(), driver.getId());
    }

    public boolean isMapped(Customer customer, Driver driver) {
        return BookingMapper.get(customer.getId()) == driver.getId();
    }

    public boolean AcceptOrDeclineTrip(Customer customer) {
        _messagingService.sendAcceptOrRejectMessage(customer, "Rejected");
        return true;
    }

    public void UnMapDriver(Customer customer, Driver driver) {
        BookingMapper.clear();
    }

    public List<Driver> getAll() {
        return new ArrayList<>();
    }

    /**
     * Gets Drivers within proximity of a requesting customer
     */

    public List<Driver> getDriversWithinProximity(double latitude, double longitude, double proximityDistance) {
        double[] boundingBox = LocationUtils.calculateBoundingBox(latitude, longitude, proximityDistance);

        double minLat = boundingBox[0];
        double maxLat = boundingBox[1];
        double minLon = boundingBox[2];
        double maxLon = boundingBox[3];

        // Query drivers within the bounding box
        return _driverRepository.findDriversInBoundingBox(minLat, maxLat, minLon, maxLon);
    }

    @Transactional
    public void registerDriver(Driver Person) {
        // Encode the password before setting it
        Person.setId(UUID.randomUUID());
        Person.setPassword(passwordEncoder.encode(Person.password));
        Person.status = "Online";
        Person.Usertype = Person.getDetectedUsertype();
        Person.TotalTrips = 0;
        Person.totalEarnings = BigDecimal.valueOf(0.0);
        Person.TaxID = "AAA1444Y";
        Person.Rating = 0;
        Person.RegistrationStatus = ("Active");
        Set<ConstraintViolation<Driver>> violations = validator.validate(Person);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        try {
            _driverRepository.save(Person);
        } catch (Exception e) {
            throw e;
        }
    }

    public void add(Driver driver) {
        if (driver != null && _driverRepository != null) {
            this._driverRepository.save(driver);
        }
    }
}
