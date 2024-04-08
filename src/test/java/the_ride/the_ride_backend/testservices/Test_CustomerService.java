package the_ride.the_ride_backend.testservices;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import the_ride.the_ride_backend.testrepositories.Test_CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class Test_CustomerService {
    private final Test_CustomerRepository test_customerRepository;

    @Autowired
    public Test_CustomerService(Test_CustomerRepository testCustomerRepository) {
        test_customerRepository = testCustomerRepository;
    }

    @Transactional
    public void clear() {
        this.test_customerRepository.flush();
    }

    @Transactional
    public void add(Test_Customer customer) {
        assert customer != null;
        if (StringUtils.isBlank(customer.isOnlyMySexAllowed)) {
            customer.isOnlyMySexAllowed = "false";
        }
        if (test_customerRepository != null) {
            this.test_customerRepository.save(customer);
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
        List<Test_Customer> emptyList = new ArrayList<>();
        return emptyList;
    }

    @Transactional
    public Test_Customer findById(UUID id) {
        return test_customerRepository.findById(id).get();
    }

    @Transactional
    public void UpdateCustomer(Test_Customer customer) {
        if (this.test_customerRepository != null) {
            test_customerRepository.save(customer);
        }
    }

    @Transactional
    public void remove(UUID id) {
        try {
            Test_Customer customer= findById(id);
            test_customerRepository.delete(customer);
        } catch (Exception e) {
            throw e;
        }
    }
}
