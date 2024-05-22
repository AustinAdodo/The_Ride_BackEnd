package the_ride.the_ride_backend.test.testconfig;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestAppConfig {

    /**
     * Injecting the value of the SPRING_PROFILE environment variable
     * Defaulting to "prod" if the test environment variable is not set
     */

    @Value("${SPRING_PROFILE:test-prod}")
    private String activeProfile;

    /**
     * Method to set the active Spring profile based on the SPRING_PROFILE environment variable
     * Setting the active profile as a system property
     * NB: You can also use @ActiveProfiles("test")
     */

    @PostConstruct
    public void init() {
        System.setProperty("spring.profiles.active", "test-" + activeProfile);
        System.out.println("Active profile set to: " + System.getProperty("spring.profiles.active"));
    }
}
