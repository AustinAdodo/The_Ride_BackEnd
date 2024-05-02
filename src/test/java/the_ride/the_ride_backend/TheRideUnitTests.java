package the_ride.the_ride_backend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Repositories.UserRepository;
import the_ride.the_ride_backend.Services.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TheRideUnitTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService service;

    @Mock
    private UserRepository customerRepository;

    @BeforeEach
    @Transactional
    public void setupDatabase() {
        String createTableSQL = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'customers') BEGIN " +
                "CREATE TABLE customers (" +
                "ID UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY, " +
                "firstName NVARCHAR(255) NOT NULL DEFAULT 'John', " +
                "lastName NVARCHAR(255) NOT NULL DEFAULT 'Wick', " +
                "middleName NVARCHAR(255), " +
                "sex NVARCHAR(10), " +
                "photourl NVARCHAR(MAX), " +
                "Rating INT, " +
                "TotalTrips INT, " +
                "Password NVARCHAR(255), " +
                "email NVARCHAR(255), " +
                "username NVARCHAR(255), " +
                "DateOfBirth DATE, " +
                "status NVARCHAR(255), " +
                "address NVARCHAR(MAX), " +
                "Category NVARCHAR(255), " +
                "Usertype NVARCHAR(255), " +
                "currentLongitude NVARCHAR(255), " +
                "currentLatitude NVARCHAR(255), " +
                "is_only_my_sex_allowed NVARCHAR(50), " +
                "default_home_address NVARCHAR(MAX)" +
                ");" +
                "INSERT INTO customers (first_name, last_name, is_only_my_sex_allowed,username) VALUES ('test-firstname', 'test-lastname'" +
                ",'false','usernameTest');" +
                "END";
        jdbcTemplate.execute(createTableSQL);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new UserService(customerRepository);
    }

    @BeforeEach
    public void clearDB() {
        service.clear();
    }

    @AfterAll
    public static void tearDown() {
//        teardown not necessary if using 'create-drop' for the Hibernate ddl-auto setting.
    }

    /**
     * Unit test
     * This test ensures that the value of is_only_my_sex_allowed is correctly passed through the service layer.
     */

    @Test
    @WithMockUser(username = "test_user", roles = {"USER"})
    public void testAddCustomerWithCorrectIsOnlyMySexAllowedField() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setUsername("userName");
        customer.setIsOnlyMySexAllowed("false");
        UUID id = UUID.randomUUID();
        customer.setId(id);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // Act
        service.add(customer);
        Optional<Customer> result = customerRepository.findById(id);
        Customer savedCustomer = result.get();

        // Assert
        verify(customerRepository).save(customer);
        assert "false".equals(savedCustomer.getIsOnlyMySexAllowed()) :
                "Field is_only_my_sex_allowed was not handled properly";
    }
}
