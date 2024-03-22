package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import the_ride.the_ride_backend.Models.Trip.Trip;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
