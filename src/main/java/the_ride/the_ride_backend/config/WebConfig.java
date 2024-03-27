package the_ride.the_ride_backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Allows all origins for testing and demonstrations
                .allowedMethods("*") // Allows all methods for testing and demonstrations
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
//.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")