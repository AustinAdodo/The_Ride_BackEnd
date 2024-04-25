package the_ride.the_ride_backend.Services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.UserRepository;

import java.util.Collections;

/**
 * for Spring Security internals
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(customer.getUsername(), customer.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
    }
}
