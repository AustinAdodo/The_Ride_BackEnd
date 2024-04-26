package the_ride.the_ride_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Repositories.DriverRepository;

import java.util.Collections;

/**
 * The CustomDriverDetailsService class is a service class that implements the UserDetailsService interface
 * from Spring Security, providing a custom method to load user details based on a username.
 * This service is annotated with @Service, indicating that it's a Spring-managed component.
 * <p></p>
 * for Spring Security internals
 */
@Service
public class CustomDriverDetailsService implements UserDetailsService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Driver driver = driverRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(driver.getUsername(), driver.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_DRIVER")));
    }
}
