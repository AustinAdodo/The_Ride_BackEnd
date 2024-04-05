package the_ride.the_ride_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import the_ride.the_ride_backend.Models.User.Customer;
import the_ride.the_ride_backend.Services.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * '@RunWith(SpringRunner.class)' is from JUnit 4 and will not be used
 */

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TheRideBackEndApplicationTests {
    @Autowired
    private UserService service;
    @Value("${spring.datasource.url}")
    private static String databaseUrl;

    // Connection and statement for database setup/teardown
    private static Connection connection;
    private static Statement statement;
    private static final List<Customer> customers = new ArrayList<>();

    /**
     * Injects the mock Library.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * String In_memory_Connection = "jdbc:sqlite::memory:"; <- sqlite
     */
    @BeforeAll
    public static void setUp() throws SQLException {
        String inMemoryConnection = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        //String inMemoryConnection = "jdbc:h2:mem:testdb;USER=sa;PASSWORD=";
        connection = DriverManager.getConnection(inMemoryConnection, "sa", "");
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS customer " +
                "(id UUID DEFAULT RANDOM_UUID() PRIMARY KEY, firstname VARCHAR(255), lastname VARCHAR(255), " +
                "isOnlyMySexAllowed VARCHAR(255), DefaultHomeAddress VARCHAR(255))");
    }

    @BeforeAll
    public static void populateCustomers() {
        customers.add(new Customer("Austin", "Adodo"));
        customers.add(new Customer("Tony", "Kroos"));
        customers.add(new Customer("Cristiano", "Ronaldo"));
    }

    @BeforeEach
    public void clearDB() {
        service.clear();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        // Drop the 'customer' table
        statement.execute("DROP TABLE customer");
        statement.close();
        connection.close();
    }

    public void addCustomers() {
        for (Customer customer : customers) {
            service.add(customer);
        }
    }

    @Test
    public void shouldAllowSavingOfCustomer_Modified_UTF_8() throws Exception {
        var mocker = this.mockMvc;
        for (Customer customer : customers) {
            this.mockMvc.perform(post("/customers")
                            .content(asJsonString(customer))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("firstname", is(customer.getFullName().split(" ")[0])));
        }
    }

    @Test
    public void shouldLetUsPostCustomers() throws Exception {
        for (Customer customer : customers) {
            this.mockMvc.perform(post("/customers")
                            .content(asJsonString(customer))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) //TestUtil.APPLICATION_JSON_UTF8
                    .andExpect(jsonPath("firstname", is(customer.getFullName().split(" ")[0])));
        }
    }

    @Test
    public void shouldAllowUpdatingCustomers() throws Exception {
        addCustomers();
        String name = "Another Name";
        List<Customer> all = this.service.getAll();
        Customer customer = this.service.getAll().get(0);
        customer.setName(name);
        this.mockMvc.perform(put("/customers/" + customer.getId())
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Customer actual = this.service.findById(customer.getId());
        assertEquals("Should have updated the customer", actual.getFullName(), name);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}





