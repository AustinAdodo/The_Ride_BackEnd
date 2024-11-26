package the_ride.the_ride_backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
    /**
     * Injecting the value of the SPRING_PROFILE environment variable
     * Defaulting to "dev" if the environment variable is not set
     */

    @Value("${SPRING_PROFILE:dev}")
    private String activeProfile;

    /**
     * Method to set the active Spring profile based on the SPRING_PROFILE environment variable
     * Setting the active profile as a system property
     */

    @PostConstruct
    public void init() {
        System.setProperty("spring.profiles.active", activeProfile);
        logger.info("The current Active profile: {}", activeProfile);
    }
}
