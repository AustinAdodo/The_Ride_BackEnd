package Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestAppConfig {

    /**
     * Injecting the value of the SPRING_PROFILE environment variable
     * Defaulting to "dev" if the environment variable is not set
     */

    @Value("${SPRING_PROFILE:prod}")
    private String activeProfile;

    /**
     * Method to set the active Spring profile based on the SPRING_PROFILE environment variable
     * Setting the active profile as a system property
     */

    @PostConstruct
    public void init() {
        System.setProperty("spring.profiles.active", "test-" + activeProfile);
    }
}
