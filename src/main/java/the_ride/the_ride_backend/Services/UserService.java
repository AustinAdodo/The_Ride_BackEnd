package the_ride.the_ride_backend.Services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.User.User;

@Service
public class UserService {

    public UserService() {}

    public User getCurrentLoggedInUser() {
        // Get the authentication object from the security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
