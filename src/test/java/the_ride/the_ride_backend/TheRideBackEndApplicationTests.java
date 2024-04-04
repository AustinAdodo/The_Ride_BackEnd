package the_ride.the_ride_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @BeforeAll
    public static void setUp() throws SQLException {
        // String In_memory_Connection = "jdbc:sqlite::memory:"; <- sqlite
        String inMemoryConnection = "jdbc:h2:mem:testdb;USER=sa;PASSWORD=";
        connection = DriverManager.getConnection(inMemoryConnection);
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Customers " +
                "(id INT PRIMARY KEY AUTO_INCREMENT, firstame TEXT, lastname TEXT)");
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
        // Drop the 'articles' table
        statement.execute("DROP TABLE Customers");
        statement.close();
        connection.close();
    }

    public void addArticles() {
        for (Customer customer : customers) {
            service.add(customer);
        }
    }

    @Test
    void contextLoads() {
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
    public void shouldLetUsPostArticles() throws Exception {
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
    public void shouldAllowUpdatingArticles() throws Exception {
        addArticles();
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
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}





