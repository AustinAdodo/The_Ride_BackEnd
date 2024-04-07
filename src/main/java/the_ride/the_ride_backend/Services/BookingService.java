package the_ride.the_ride_backend.Services;

//import org.springframework.data.jpa.repository.Query; import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Utiities.DriverSearchStatus;

import java.util.Objects;


@Service
public class BookingService {
    private final UserService _userService;
    private final DriverService _driverService;
    private final LocatorService _locatorService;
    private final MessagingService _MsgService;
    private String FindStatus = String.valueOf(DriverSearchStatus.NotFound);
    private final Customer currentCustomer;
    private Driver currentDriver = null;

    @Autowired
    public BookingService(UserService userS, DriverService drivers,
                          LocatorService locatorService, MessagingService msgService) {
        _userService = userS;
        _driverService = drivers;
        _locatorService = locatorService;
        _MsgService = msgService;
        currentCustomer = _userService.getCurrentLoggedInUser();
    }

    public Customer GetCurrentCustomer() {
        return currentCustomer;
    }

    public Driver GetChosenDriverDetails() {
        return currentDriver;
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
        _MsgService.sendNotificationMessage(currentDriver, "The planned trip with " + currentCustomer.getFirstName() +
                " was cancelled.");
        //End booking
    }

    /**
     * Finds the closest driver and sends message asynchronously to the driver.
     */
    public void FindDriver() {
        var DriverList = _locatorService.driversNearSpecifiedLocation(currentCustomer.currentLatitude);
        if (!DriverList.isEmpty()) {
            FindStatus = String.valueOf(DriverSearchStatus.Found);
        } else {
            _MsgService.sendNotificationMessage(currentCustomer, "No Driver Found, Click Refresh.");
        }
        if (true) {
            currentDriver = DriverList.get(0);
            //send message "Driver found"
            //set status of driver to Driver.Occupied
            _MsgService.sendRequestMessage(DriverList.get(0));
        } else {
            currentDriver = DriverList.get(1);
            _MsgService.sendRequestMessage(DriverList.get(1));
        }
        _driverService.mapDriver(currentCustomer, currentDriver);
    }
}
//if (!currentCustomer.BlackListedDriversIds.contains(DriverList.get(0).getId())) {