package the_ride.the_ride_backend.Services;

import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Trip.Trip;

@Service
public class TripService {
    private final BookingService _bookingService;
    private Trip CurrentTrip;
    public TripService(BookingService bookingService) {
        _bookingService = bookingService;
    }
    public Trip CancelTrip() {
        //retrieve current ongoing trip
        return new Trip();
    }
    public Trip StartTrip() {
        _bookingService.SaveAsTrip();
        return new Trip();
    }
    public Trip EndTrip() {
        //call trip repository and save trip
        return new Trip();
    }
}
