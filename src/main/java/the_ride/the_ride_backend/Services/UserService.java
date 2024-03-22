package the_ride.the_ride_backend.Services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Exceptions.UserNotFoundException;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository _userRepository;

    public UserService(UserRepository userRepository) {
        this._userRepository = userRepository;
    }

    public Customer disableUser(Customer customer) {
        Optional<Customer> queriedUserOptional = _userRepository.findById(customer.getID());

        // Check if the user exists
        if (queriedUserOptional.isPresent()) {
            Customer queriedUser = queriedUserOptional.get();
            queriedUser.status = "Inactive";
            _userRepository.save(queriedUser);
            return queriedUser;
        } else {
            // Handle case when user is not found
            throw new UserNotFoundException("User not found with ID: " + customer.getID());
        }
    }

    public Customer getCurrentLoggedInUser() {
        // Get the authentication object from the security context holder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Customer) {
            return (Customer) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
