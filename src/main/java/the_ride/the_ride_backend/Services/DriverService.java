package the_ride.the_ride_backend.Services;

import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.DriverRepository;
import the_ride.the_ride_backend.Utiities.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class DriverService {
    private final HashMap<UUID, UUID> BookingMapper = new HashMap<>();
    private final DriverRepository _driverRepository;
    private final  MessagingService _messagingService;
    public DriverService(DriverRepository driverRepository, MessagingService messagingService) {
        _driverRepository = driverRepository;
        _messagingService = messagingService;
    }

    public void mapDriver(Customer customer, Driver driver) {
        BookingMapper.put(customer.getID(),driver.getID());
    }

    public boolean isMapped(Customer customer, Driver driver) {
        return BookingMapper.get(customer.getID()) == driver.getID();
    }

    public boolean AcceptOrDeclineTrip(Customer customer) {
        _messagingService.sendAcceptOrRejectMessage(customer,"Rejected");
        return true;
    }

    public void UnMapDriver(Customer customer, Driver driver) {
        BookingMapper.clear();
    }

    public List<Driver> getAll() {
        return new ArrayList<Driver>();
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
}
