package the_ride.the_ride_backend.testrepositories;

import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Initialization-on-demand holder idiom, a thread-safe and lazy-loaded singleton pattern
 */
@Repository
public interface Test_CustomerRepository extends JpaRepository<Test_Customer, UUID> {
    static Logger logger() {
        final class LogHolder {
            private static final Logger LOGGER = getLogger(Test_CustomerRepository.class);
        }
        return LogHolder.LOGGER;
    }
}