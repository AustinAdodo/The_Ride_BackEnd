package the_ride.the_ride_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import the_ride.the_ride_backend.Models.Payload;
import the_ride.the_ride_backend.Services.MessagePublisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TripController {
    private final MessagePublisher messagePublisher;

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
            e.printStackTrace();
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
            e.printStackTrace();
            // Handle JSON conversion error
        }
        return customerDetails;
    }
}
