package the_ride.the_ride_backend.Services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public void sendRequestMessage(String message) {
        System.out.println("Publishing message to RabbitMQ: " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}

