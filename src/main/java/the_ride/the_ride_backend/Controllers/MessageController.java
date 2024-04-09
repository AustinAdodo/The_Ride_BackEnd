package the_ride.the_ride_backend.Controllers;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import the_ride.the_ride_backend.Models.ModelPayload;
import the_ride.the_ride_backend.Services.MessagePublisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Controller
public class MessageController {
    private final MessagePublisher messagePublisher;
    @Autowired
    private SimpMessagingTemplate template;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageController(MessagePublisher messagePublisher, ObjectMapper objectMapper) {
        this.messagePublisher = messagePublisher;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/acceptTrip")
    @SendTo("/topic/driverUpdates")
    public ModelPayload processAcceptTrip(ModelPayload driverDetails) {
        try {
            String message = objectMapper.writeValueAsString(driverDetails);
            messagePublisher.sendRequestMessage(message);
        } catch (JsonProcessingException e) {
            logger.error("Error processing acceptTrip message: ", e);
        }
        return driverDetails;
    }

    @MessageMapping("/trip/Request")
    @SendTo("/topic/customer")
    public ModelPayload processRequestTrip(ModelPayload customerDetails) {
        try {
            String message = objectMapper.writeValueAsString(customerDetails);
            messagePublisher.sendRequestMessage(message);
        } catch (JsonProcessingException e) {
            logger.error("Error processing tripRequest message: ", e);
        }
        return customerDetails;
    }

    public void broadcastDriverTopic(ModelPayload message) {
        template.convertAndSend("/topic/driverUpdates", message);
    }

    public void broadcastUserTopic(ModelPayload message) {
        template.convertAndSend("/topic/customer", message);
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessageFromRabbitMQ(String message) {
        ModelPayload tripMessage = convertStringToTripMessage(message);
        assert tripMessage != null;
        if ("driverUpdates".equals(tripMessage.topic)) {
            tripMessage.status = "Back-End Processed Successfully";
            broadcastDriverTopic(tripMessage);
        } else if ("customer".equals(tripMessage.topic)) {
            tripMessage.status = "Back-End Processed Successfully";
            broadcastUserTopic(tripMessage);
        } else {
            System.out.println("Received message with unknown type: " + tripMessage.topic);
        }
    }

    private ModelPayload convertStringToTripMessage(String message) {
        try {
            return objectMapper.readValue(message, ModelPayload.class);
        } catch (JsonProcessingException e) {
            logger.error("Error deserializing message from RabbitMQ: ", e);
            return null;
        }
    }

}
