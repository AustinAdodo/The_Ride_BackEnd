package the_ride.the_ride_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.Models.Admin.BackEndUser;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<BackEndUser, Integer> {
    Optional<BackEndUser> findByUsername(String username);
}

