package the_ride.the_ride_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import the_ride.the_ride_backend.testmodels.Test_Customer;
import the_ride.the_ride_backend.testservices.Test_CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TheRideBackEndApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Test_CustomerService service;

    @Autowired
    private MockMvc mockMvc;

    private static final List<Test_Customer> customers = new ArrayList<>();

    @BeforeAll
    @Transactional
    public void setupDatabase() {
        String createTableSQL = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'test_Customers') BEGIN " +
                "CREATE TABLE test_Customers (" +
                "ID UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY, " +
                "firstName NVARCHAR(255) NOT NULL, " +
                "lastName NVARCHAR(255) NOT NULL, " +
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
                "INSERT INTO test_Customers (firstName, lastName, is_only_my_sex_allowed) VALUES ('test-firstname', 'test-lastname'" +
                ",'false');" +
                "END";
        jdbcTemplate.execute(createTableSQL);
    }

    @BeforeEach
    public void clearDB() {
        service.clear();
    }

    @BeforeEach
    public void populateCustomers() {
        customers.add(new Test_Customer("Austin", "Adodo"));
        customers.add(new Test_Customer("Tony", "Kroos"));
        customers.add(new Test_Customer("Cristiano", "Ronaldo"));
        for (Test_Customer customer : customers) {
            customer.setIsOnlyMySexAllowed("false");
        }
    }

    @AfterAll
    public static void tearDown() {
//        teardown not necessary if using 'create-drop' for the Hibernate ddl-auto setting.
    }

    public void addCustomers() {
        for (Test_Customer customer : customers) {
            this.service.add(customer);
        }
    }

    @Test
    public void shouldAllowSavingOfCustomer() throws Exception {
        for (Test_Customer customer : customers) {
            this.mockMvc.perform(post("/test_customers")
                            .content(asJsonString(customer))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.firstName", is(customer.getFirstName())));
        }
    }

    @Test
    public void shouldAllowUpdatingCustomers() throws Exception {
        addCustomers();
        String name = "Another Name";
        List<Test_Customer> all = this.service.getAll();
        Test_Customer customer = all.get(0);
        customer.setName(name);
        this.mockMvc.perform(put("/test_customers/" + customer.getId())
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Test_Customer actual = this.service.findById(customer.getId());
        String ActualName = actual.getFullName();
        Assertions.assertEquals(name, ActualName, "Should have updated the customer");
    }

    @Test
    public void shouldAllowUsToRemoveArticles() throws Exception {
        addCustomers();
        List<Test_Customer> all = new ArrayList<Test_Customer>(this.service.getAll());
        for (Test_Customer customer : all) {
            this.mockMvc.perform(delete("/test_customers/" + customer.getId()))
                    .andExpect(status().isNoContent());
        }
        Assertions.assertEquals(0, this.service.getAll().size(), "Should remove all customers");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
//    List<Test_Customer> all = customers.stream()
//            .filter(Test_Customer.class::isInstance)
//            .map(Test_Customer.class::cast)
//            .toList();