package the_ride.the_ride_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import the_ride.the_ride_backend.Exceptions.ConfigurationException;
import the_ride.the_ride_backend.Services.CustomDriverDetailsService;
import the_ride.the_ride_backend.Services.CustomUserDetailsService;

import java.util.Arrays;

/**
 * Configuration class for setting up the AuthenticationManager in Spring Security.
 * This class defines a custom AuthenticationManager that manages different authentication providers,
 * allowing for distinct authentication processes based on user types (drivers, customers, super_Users e.t.c).
 * It utilizes two UserDetailsService implementations: CustomDriverDetailsService for drivers and
 * CustomUserDetailsService for customers, each wrapped in a DaoAuthenticationProvider configured
 * with a PasswordEncoder to handle password encoding.
 *
 * @author Configuration by Adodo Austin.
 */
@Configuration
public class AuthenticationManagerConfig {

    @Autowired
    private CustomDriverDetailsService customDriverDetailsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Defines the AuthenticationManager bean that manages an array of authentication providers.
     * Each provider is responsible for a specific type of user, ensuring proper authentication handling
     * based on the user's role.
     *
     * @return AuthenticationManager configured with custom authentication providers for different user types.
     * @throws ConfigurationException if there is an error in creating the authentication manager.
     */

    @Bean
    public AuthenticationManager authenticationManager() throws ConfigurationException {
        try {
            return new ProviderManager(Arrays.asList(
                    customAuthenticationProvider(customDriverDetailsService),
                    customAuthenticationProvider(customUserDetailsService)
            ));
        } catch (Exception e) {
            throw new ConfigurationException("Failed to configure AuthenticationManager", e);
        }
    }

    /**
     * Creates a DaoAuthenticationProvider for a given UserDetailsService.
     * This provider uses the specified service for user details retrieval and the password encoder for password verification.
     *
     * @param service The UserDetailsService to retrieve user details for authentication.
     * @return DaoAuthenticationProvider configured with a UserDetailsService and a PasswordEncoder.
     */
    private DaoAuthenticationProvider customAuthenticationProvider(UserDetailsService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
