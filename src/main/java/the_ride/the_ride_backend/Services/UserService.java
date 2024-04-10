package the_ride.the_ride_backend.Services;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Exceptions.UserNotFoundException;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
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

    public void registerUser(Customer Person) {
        Person.status = "Online";
        Customer user = new Customer();
        user.Usertype = Person.Usertype;
        user.sex = Person.sex;
        user.isOnlyMySexAllowed = Person.isOnlyMySexAllowed;
        user.DefaultHomeAddress = Person.address;
        user.address = Person.address;
        user.setPassword(passwordEncoder.encode(Person.password));
        user.status = Person.status;
        user.TotalTrips = 0;
        user.Rating = 0;
        user.email = Person.email;
        user.setPassword(passwordEncoder.encode(Person.password));
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

    @Transactional
    public void clear() {
        this._userRepository.flush();
    }

    @Transactional
    public void add(Customer customer) {
        assert customer != null;
        if (StringUtils.isBlank(customer.isOnlyMySexAllowed)) {
            customer.isOnlyMySexAllowed = "false";
        }
        if (_userRepository != null) {
            this._userRepository.save(customer);
        }
    }

    @Transactional
    @Query(value = "SELECT * FROM customers", nativeQuery = true)
    public List<Customer> getAll() {
        List<Customer> all = new ArrayList<>();
        if (this._userRepository != null) {
            all = _userRepository.findAll();
        }
        if (!all.isEmpty()) {
            return all;
        }
        List<Customer> emptyList = new ArrayList<>();
        return emptyList;
    }

    @Transactional
    public Customer findById(UUID id) {
        return _userRepository.findById(id).get();
    }
}
