package the_ride.the_ride_backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Exceptions.UserNotFoundException;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository _userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this._userRepository = userRepository;
    }

    public void registerUser(UserBaseModel<UUID> Person) {
        Person.status ="Online";
        Customer user = new Customer();
        user.Usertype = Person.Usertype;
        user.setPassword(passwordEncoder.encode(Person.Password));
        user.status = Person.status;
        user.TotalTrips = 0;
        user.Rating = 0;
        user.email = Person.email;
        user.setPassword(passwordEncoder.encode(Person.Password));
        _userRepository.save(user);
    }

    public Customer disableUser(Customer customer) {
        Optional<Customer> queriedUserOptional = _userRepository.findById(customer.getId());
        if (queriedUserOptional.isPresent()) {
            Customer queriedUser = queriedUserOptional.get();
            queriedUser.status = "Inactive";
            _userRepository.save(queriedUser);
            return queriedUser;
        } else {
            throw new UserNotFoundException("The specified User with ID: " + customer.getId() + " was not found");
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
