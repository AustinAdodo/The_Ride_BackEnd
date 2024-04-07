package the_ride.the_ride_backend.testcontrollers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import the_ride.the_ride_backend.testservices.Test_CustomerService;

import java.util.List;
@RestController
public class Test_CustomerController {
        private final Test_CustomerService service;

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
    }
