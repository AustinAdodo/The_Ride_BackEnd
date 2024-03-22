package the_ride.the_ride_backend.Services;

//import org.springframework.data.jpa.repository.Query;import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Models.User.User;
import the_ride.the_ride_backend.Utiities.DriverSearchStatus;


@Service
public class BookingService {
    @Autowired
    private UserService _UserService;
    @Autowired
    private DriverService _DriverService;
    @Autowired
    private LocatorService _locatorService;
    private MessagingService _MsgService;
    private String FindStatus;

    @Autowired
    public BookingService(UserService userS, DriverService drivers, LocatorService locatorService) {
        this._UserService = userS;
        this._DriverService = drivers;
        this._locatorService = locatorService;
    }

    public BookingService() {
    }

    public Trip SaveTrip() {
        //call trip repository and save trip
        return new Trip();
    }

    public void CancelBooking() {
        //check if booking exists with the current user
        //End booking
    }

    /**
     * Finds the closest driver and sends message asynchronously to the driver.
     */
    public void FindDriver() {
        User currentUser = _UserService.getCurrentLoggedInUser();
        var DriverList = _locatorService.driversNearSpecifiedLocation(currentUser.StreetName);
        if(!DriverList.isEmpty()){
            FindStatus = String.valueOf(DriverSearchStatus.Found);
        }
        else {
            _MsgService.sendNotificationMessage(currentUser,"No Driver Found, Click Refresh.");
        }
        _MsgService.sendRequestMessage(DriverList.get(0));
    }
}
