package the_ride.the_ride_backend.Services;

//import org.springframework.data.jpa.repository.Query; import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Utiities.DriverSearchStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class BookingService {
    private final UserService _userService;
    private final TripService _tripService;
    private final DriverService _driverService;
    private final LocatorService _locatorService;
    private final MessagingService _MsgService;
    private String FindStatus = String.valueOf(DriverSearchStatus.NotFound);
    private final Customer currentCustomer;
    private Driver currentDriver = null;

    @Autowired
    public BookingService(UserService userS, TripService tripService, DriverService drivers,
                          LocatorService locatorService, MessagingService msgService) {
        _userService = userS;
        _tripService = tripService;
        _driverService = drivers;
        _locatorService = locatorService;
        _MsgService = msgService;
        currentCustomer = _userService.getCurrentLoggedInUser();
    }

    /*
    *
    */
    public void SaveAsTrip() {
        Trip currentTrip = new Trip();
        currentTrip.tripDate = LocalDate.now();
        currentTrip.startTime= LocalDateTime.now();
        currentTrip.endTime= null;
        _tripService.StartTrip(currentTrip);
        // the booking officially becomes a trip
    }

    /**
    * This is a customer only cancel action.
    */
    public void CancelBooking() {
        //check if booking exists with the current user
        if (Objects.equals(FindStatus, "Found") && _driverService.isMapped(currentCustomer, currentDriver)) {
            _driverService.UnMapDriver(currentCustomer, currentDriver);
        }
        FindStatus = String.valueOf(DriverSearchStatus.NotFound);
        _MsgService.sendNotificationMessage(currentDriver, "The planned trip with " + currentCustomer.getFirstname() +
                " was cancelled.");
        //End booking
    }

    /**
     * Finds the closest driver and sends message asynchronously to the driver.
     */
    public void FindDriver() {
        var DriverList = _locatorService.driversNearSpecifiedLocation(currentCustomer.CurrentStreetName);
        if (!DriverList.isEmpty()) {
            FindStatus = String.valueOf(DriverSearchStatus.Found);
        } else {
            _MsgService.sendNotificationMessage(currentCustomer, "No Driver Found, Click Refresh.");
        }
        if (!currentCustomer.BlackListedDriversIds.contains(DriverList.get(0).getID())) {
            currentDriver = DriverList.get(0);
            _MsgService.sendRequestMessage(DriverList.get(0));
        } else {
            currentDriver = DriverList.get(1);
            _MsgService.sendRequestMessage(DriverList.get(1));
        }
        _driverService.mapDriver(currentCustomer, currentDriver);
    }
}
