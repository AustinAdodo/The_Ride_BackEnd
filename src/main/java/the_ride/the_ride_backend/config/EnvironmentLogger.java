package the_ride.the_ride_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentLogger {

    @Value("${spring.datasource.url:Not Found}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:Not Found}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:Not Found}")
    private String datasourcePassword;

    @Value("${spring.profiles.active:Not Found}")
    private String environmentName;


    @Bean
    public CommandLineRunner logEnvironmentVariables() {
        return args -> {
            System.out.println("===== Environment Variables =====");
            System.out.println("spring.profiles.active: " + environmentName);
            System.out.println("spring.datasource.url: " + datasourceUrl);
            System.out.println("spring.datasource.username: " + datasourceUsername);
            System.out.println("spring.datasource.password: " + datasourcePassword);
            System.out.println("=================================");
        };
    }
}
