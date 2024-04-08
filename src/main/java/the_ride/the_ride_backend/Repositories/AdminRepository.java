package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.Models.Admin.BackEndUser;

@Repository
public interface AdminRepository extends JpaRepository<BackEndUser, Integer> {
}

