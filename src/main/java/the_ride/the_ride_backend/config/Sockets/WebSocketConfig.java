package the_ride.the_ride_backend.config.Sockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * consider using https and wss (WebSocket Secure) protocols in production
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200", "http://localhost:4201")
                .withSockJS()
                .setWebSocketEnabled(true)
                .setHeartbeatTime(60000)
                .setHttpMessageCacheSize(10000)
                .setDisconnectDelay(30000);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Configure a message broker for routing messages to/from clients
        registry.enableSimpleBroker("/topic", "/queue");
        // Defines prefixes for destinations that the broker will recognize
        // Adjust "/topic" and "/queue" as needed for your application
        registry.setApplicationDestinationPrefixes("/app");
        // Prefix for messages bound for methods annotated with @MessageMapping
    }
}

