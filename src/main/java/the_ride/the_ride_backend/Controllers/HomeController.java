package the_ride.the_ride_backend.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Services.DriverService;
import the_ride.the_ride_backend.Services.UserService;


import java.util.UUID;

@RestController
public class HomeController {
    private final DriverService _driverService;
    private final UserService _userService;

    @Autowired
    public HomeController(DriverService driverService, UserService userService) {
        _driverService = driverService;
        _userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody UserBaseModel<UUID> person) {
            String userType = person.getDetectedUsertype();
            if ("Customer".equals(userType)) {
                Customer customer = (Customer) person;
                customer.setRole("ROLE_USER");
                _userService.registerUser(customer);
                return ResponseEntity.ok().build();
            } else if ("Driver".equals(userType)) {
                Driver driver = (Driver) person;
                driver.setRole("ROLE_DRIVER");
                _driverService.registerDriver(driver);
                return ResponseEntity.ok().build();
            }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/secure")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Driver> OnboardDriver(@RequestBody Driver driver) {
        if (driver != null) {
            HttpHeaders headers = new HttpHeaders();
            this._driverService.add(driver);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
            return new ResponseEntity<>(driver, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Driver(), HttpStatus.NOT_FOUND);
    }

//    private boolean authenticate(String email, String password) {
//        // Connect to your database (e.g., using JDBC or a JPA library)
//        // Query the database for a user with the provided email
//        // Check if the password matches the stored password (hashed and securely compared)
//        // Return true if credentials match, false otherwise
//
//        // This is a placeholder, you need to implement the actual logic
//        // to connect to your database and verify credentials
//        return true;
//    }
}
