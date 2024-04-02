package the_ride.the_ride_backend.Controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import the_ride.the_ride_backend.Models.Payload;
import the_ride.the_ride_backend.Services.MessagePublisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TripController {
    private final MessagePublisher messagePublisher;
    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public TripController(MessagePublisher messagePublisher, ObjectMapper objectMapper) {
        this.messagePublisher = messagePublisher;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/acceptTrip")
    @SendTo("/topic/driver")
    public Payload processAcceptTrip(Payload driverDetails) {
        try {
            String message = objectMapper.writeValueAsString(driverDetails);
            messagePublisher.sendRequestMessage(message);
        } catch (JsonProcessingException e) {
            logger.error("Error processing acceptTrip message: ", e);
            // Handle JSON conversion error
        }
        return driverDetails;
    }

    @MessageMapping("/trip/Request")
    @SendTo("/topic/customer")
    public Payload processRequestTrip(Payload customerDetails) {
        try {
            String message = objectMapper.writeValueAsString(customerDetails);
            messagePublisher.sendRequestMessage(message);
        } catch (JsonProcessingException e) {
            logger.error("Error processing tripRequest message: ", e);
            // Handle JSON conversion error
        }
        return customerDetails;
    }
}
