package the_ride.the_ride_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import the_ride.the_ride_backend.Models.Admin.BackEndUser;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.AdminRepository;
import the_ride.the_ride_backend.Repositories.DriverRepository;
import the_ride.the_ride_backend.Repositories.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   AdminRepository adminRepository,
                                   DriverRepository driverRepository,  PasswordEncoder passwordEncoder) {
        return args -> {
            if (adminRepository.findByUsername("admin@example.com").isEmpty()) {
                BackEndUser admin = new BackEndUser();
                admin.setUsername("admin@example.com");
                admin.setFirstName("admin");
                admin.setLastName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                adminRepository.save(admin);
            }

            if (userRepository.findByUsername("user@example.com").isEmpty()) {
                Customer user = new Customer();
                Customer user2 = new Customer();
                user.setUsername("user@example.com");
                user2.setUsername("user2@example.com");
                user.setFirstName("Austin");
                user2.setFirstName("Paul");
                user.setLastName("Adodo");
                user2.setLastName("Scholes");
                user.setPassword(passwordEncoder.encode("user123"));
                user2.setPassword(passwordEncoder.encode("user123"));
                user.setRole("ROLE_USER");
                user2.setRole("ROLE_USER");
                userRepository.save(user);
                userRepository.save(user2);
            }

            if (driverRepository.findByUsername("driver@example.com").isEmpty()) {
                Driver user = new Driver();
                user.setUsername("driver@example.com");
                user.setFirstName("Austin");
                user.setLastName("Good-Driver");
                user.setPassword(passwordEncoder.encode("driver123"));
                user.setRole("ROLE_DRIVER");
                driverRepository.save(user);
            }
        };
    }
}
