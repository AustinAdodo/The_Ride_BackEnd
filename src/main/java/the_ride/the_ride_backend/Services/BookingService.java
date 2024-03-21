package the_ride.the_ride_backend.Services;

//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Trip.Trip;
import the_ride.the_ride_backend.Utiities.DriverSearchStatus;



@Service
public class BookingService {
    @Autowired
    private UserService UserService;
    @Autowired
    private DriverService DriverService;

    @Autowired
    public BookingService(UserService userS, DriverService drivers) {
        this.UserService = userS;
        this.DriverService = drivers;
    }

    public BookingService() {
    }

    public Trip SaveTrip(){
        //call trip repository and save trip
        return new Trip();
    }

    public void CancelBooking(){}

    public DriverSearchStatus FindDriver(){
        return DriverSearchStatus.Found;
    }
}
