package the_ride.the_ride_backend.testrepositories;

import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import the_ride.the_ride_backend.testmodels.Test_Customer;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Initialization-on-demand holder idiom, a thread-safe and lazy-loaded singleton pattern
 */
@Repository
public interface Test_CustomerRepository extends JpaRepository<Test_Customer, UUID> {
    @Modifying
    @Query(value = "DELETE FROM test_Customers WHERE id IN (SELECT TOP 20 id FROM test_Customers ORDER BY id)", nativeQuery = true)
    void deleteTop20Customers();

    static Logger logger() {
        final class LogHolder {
            private static final Logger LOGGER = getLogger(Test_CustomerRepository.class);
        }
        return LogHolder.LOGGER;
    }
}