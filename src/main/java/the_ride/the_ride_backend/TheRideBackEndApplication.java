package the_ride.the_ride_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheRideBackEndApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TheRideBackEndApplication.class);
        app.setAdditionalProfiles("debug");
        SpringApplication.run(TheRideBackEndApplication.class, args);
    }
}
