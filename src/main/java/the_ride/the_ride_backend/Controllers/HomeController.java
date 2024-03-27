package the_ride.the_ride_backend.Controllers;


import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

import java.util.UUID;

@RestController
public class HomeController {

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(@Param("username") String email, @Param("password") String password) {
        boolean isAuthenticated = authenticate(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public boolean logout() {
        //check if email exists
        return true;
    }

    @PostMapping("/signup")
    public String signUp(@Param("Person")UserBaseModel<UUID> Person) {
        return "Secure page";
    }

    @GetMapping("/secure")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String secure() {
        return "Secure page";
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
