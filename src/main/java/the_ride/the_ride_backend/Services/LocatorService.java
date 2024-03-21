package the_ride.the_ride_backend.Services;

import java.util.List;

public class LocatorService {
    /**
     * Mimics IReadOnly List in c# => //List.of()
     */
    public static final List<String> SearchPriority = List.of("Town", "localGovernment", "District", "City", "Metropolis","State");
}
