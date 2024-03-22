package the_ride.the_ride_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocatorService {

    /*
     * Mimics IReadOnly List in c#, list order is maintained
     */
    public static final List<String> SearchPriority = List.of("Street", "Town", "localGovernment", "District",
            "City", "State");

    //@Autowired
    public LocatorService() {
    }
    public List<Driver> driversNearSpecifiedLocation(String streetName) {
        // some logic to retrieve drivers near the specified location
        return new ArrayList<>();
    }
}
