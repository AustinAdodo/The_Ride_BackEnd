package the_ride.the_ride_backend.Controllers;

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
import the_ride.the_ride_backend.Utiities.LoginCredentials;
import the_ride.the_ride_backend.Utiities.Response;

import java.math.BigDecimal;
import java.util.Objects;
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

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginCredentials credentials) {
        boolean isAuthenticated = authenticate(credentials.getUsername(), credentials.getPassword());
        if (isAuthenticated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response(false, "accepted"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(false, "Unable to resolve request"));
        }
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean logout() {
        //check if email exists
        return true;
    }

    @PostMapping("/signup")
    public HttpStatus signUp(@RequestBody Object personObj) {
        if (personObj instanceof UserBaseModel) {
            UserBaseModel<UUID> person = (UserBaseModel<UUID>) personObj;
            if ("Customer".equals(person.Usertype)) {
                Customer customer = (Customer) person;
                _userService.registerUser(customer);
                return HttpStatus.OK;
            } else if ("Driver".equals(person.Usertype)) {
                Driver driver = (Driver) person;
                _driverService.registerDriver(driver);
                return HttpStatus.OK;
            }
        }
        return HttpStatus.BAD_REQUEST;
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

    private boolean authenticate(String email, String password) {
        // Connect to your database (e.g., using JDBC or a JPA library)
        // Query the database for a user with the provided email
        // Check if the password matches the stored password (hashed and securely compared)
        // Return true if credentials match, false otherwise

        // This is a placeholder, you need to implement the actual logic
        // to connect to your database and verify credentials
        return true;
    }
}
