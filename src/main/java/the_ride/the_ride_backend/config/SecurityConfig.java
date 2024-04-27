package the_ride.the_ride_backend.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security Config
 * <br/><p>
 * Spring automatically prefixes "ROLE_" when using hasRole()
 * <br/>
 * From 7.1 to customize csRF see link below.
 * @see <a href="https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/
 * annotation/web/builders/HttpSecurity.html#csrf(org.springframework.security.config.Customizer)">...</a>
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/signup**", "/js/**", "/css/**", "/img/**","/ws/**").permitAll()  // Public paths
                .requestMatchers("/driver/**").hasRole("DRIVER")  // Restricted to users with the DRIVER role
                .requestMatchers("/customer/**").hasRole("CUSTOMER")  // Restricted to users with the CUSTOMER role
                .requestMatchers("/**").authenticated()); // All other requests require authentication

        // Configure form login
        http
                .csrf(AbstractHttpConfigurer::disable) //see
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())  // Require authentication for all requests
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"Login successful!\"}");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Login failed: " + exception.getMessage());
                        }))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("Logout successful!");
                        }));

        // Enable HTTP Basic authentication
        http.httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Password Encoder
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
