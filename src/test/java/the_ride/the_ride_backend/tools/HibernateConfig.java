package the_ride.the_ride_backend.tools;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Interceptor hibernateInterceptor() {
        return new EntityInterceptor();
    }

    @Bean
    public SessionFactory sessionFactory() {
        // Assumes you have a LocalContainerEntityManagerFactoryBean that creates EntityManagerFactory
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        sessionFactory.withOptions().interceptor(hibernateInterceptor());
        return sessionFactory;
    }
}