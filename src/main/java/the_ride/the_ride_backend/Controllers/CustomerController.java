package the_ride.the_ride_backend.Controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Services.UserService;

import java.util.List;

@RestController
public class CustomerController {
    private final UserService service;
    public CustomerController(UserService service) {
        this.service = service;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if (customer != null) {
            HttpHeaders headers = new HttpHeaders();
            this.service.add(customer);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
            return new ResponseEntity<>(customer, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Customer(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = this.service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return new ResponseEntity<>(customers, headers, HttpStatus.OK);
    }
}
