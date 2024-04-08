package the_ride.the_ride_backend.testcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import the_ride.the_ride_backend.testservices.Test_CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
public class Test_CustomerController {
    @Autowired
    private final Test_CustomerService service;

    @Autowired
    public Test_CustomerController(Test_CustomerService service) {
        this.service = service;
    }

    @PostMapping("/test_customers")
    public ResponseEntity<Test_Customer> create(@RequestBody Test_Customer customer) {
        if (customer != null) {
            HttpHeaders headers = new HttpHeaders();
            this.service.add(customer);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
            return new ResponseEntity<>(customer, headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new Test_Customer(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test_customers")
    public ResponseEntity<List<Test_Customer>> getAllCustomers() {
        List<Test_Customer> customers = this.service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return new ResponseEntity<>(customers, headers, HttpStatus.OK);
    }

    @PutMapping("/test_customers/{id}")
    public ResponseEntity<Void> update(@RequestBody Test_Customer updatedPerson,
                                       @RequestHeader HttpHeaders headers,
                                       @RequestHeader("Content-Type") String contentType,
                                       @RequestHeader(value = "Forwarded", required = false) String forwarded,
                                       @PathVariable("id") UUID id) {

        if (updatedPerson== null || areAllParametersEmpty(headers, contentType, forwarded)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            this.service.UpdateCustomer(updatedPerson);
            if (!resourceExists(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/test_customers/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        Test_Customer result = this.service.findById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.service.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean areAllParametersEmpty(HttpHeaders headers, String contentType, String forwarded) {
        return headers.isEmpty() && (contentType == null || contentType.isBlank()) && (forwarded == null || forwarded.isBlank());
    }

    private boolean resourceExists(UUID id) {
        Test_Customer foundTest_Customer = service.findById(id);
        return foundTest_Customer != null;
    }
}
