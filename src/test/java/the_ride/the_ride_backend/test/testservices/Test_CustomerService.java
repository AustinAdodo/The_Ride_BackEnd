package the_ride.the_ride_backend.test.testservices;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.test.testmodels.Test_Customer;
import the_ride.the_ride_backend.test.testrepositories.Test_CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class Test_CustomerService {
    @Autowired
    private final Test_CustomerRepository test_customerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public Test_CustomerService(Test_CustomerRepository testCustomerRepository) {
        test_customerRepository = testCustomerRepository;
    }

    @Transactional
    public void clear() {
        //test_customerRepository.deleteAll();
        test_customerRepository.deleteTop20Customers();
    }

    @Transactional //@ControllerAdvice
    public void add(Test_Customer customer) {
        assert customer != null;
        if (StringUtils.isBlank(customer.isOnlyMySexAllowed)) {
            customer.isOnlyMySexAllowed = "false";
        }
        if (test_customerRepository != null) {
            try {
                assert customer.getFirstName() != null : "First name must not be null";
                entityManager.flush();
                Test_Customer savedCustomer = test_customerRepository.save(customer);
                if (savedCustomer.getId() != null) {
                    System.out.println("Customer saved successfully with ID, first name, last name:" +
                            " " + savedCustomer.getId() + " and " + savedCustomer.getFirstName() + " " +
                            savedCustomer.getLastName());
                }
            } catch (Exception e) {
                Test_CustomerRepository.logger().error("Error saving customer: ", e);
                throw e;
            }
        }
    }

    @Transactional
    @Query(value = "SELECT * FROM test_Customers", nativeQuery = true)
    public List<Test_Customer> getAll() {
        List<Test_Customer> all = new ArrayList<>();
        if (this.test_customerRepository != null) {
            all = test_customerRepository.findAll();
        }
        if (!all.isEmpty()) {
            return all;
        }
        return new ArrayList<>();
    }

    @Transactional
    public Test_Customer findById(UUID id) {
        try {
            return test_customerRepository.findById(id).get();
        } catch (Exception e) {
            Test_CustomerRepository.logger().error("Error saving customer: ", e);
            throw e;
        }
    }

    @Transactional
    public void UpdateCustomer(Test_Customer customer) {
        if (this.test_customerRepository != null) {
            try {
                test_customerRepository.save(customer);
            } catch (Exception e) {
                Test_CustomerRepository.logger().error("Error saving customer: ", e);
            }
        }
    }

    @Transactional
    public void remove(UUID id) {
        try {
            Test_Customer customer = findById(id);
            test_customerRepository.delete(customer);
        } catch (Exception e) {
            Test_CustomerRepository.logger().error("Error saving customer: ", e);
            throw e;
        }
    }
}
