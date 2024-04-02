package the_ride.the_ride_backend.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import the_ride.the_ride_backend.Models.Payload;

@Controller
public class TripController {

    @MessageMapping("/acceptTrip")
    @SendTo("/topic/driver")
    public Payload processAcceptTrip(Payload driverDetails) {
        // Assuming driverDetails is a JSON string containing the driverPayload and message
        // You might want to convert this string to an actual object and process it as needed
        return driverDetails;
    }

    @MessageMapping("/trip/Request")
    @SendTo("/topic/customer")
    public Payload processRequestTrip(Payload customerDetails) {
        // Assuming driverDetails is a JSON string containing the driverPayload and message
        // You might want to convert this string to an actual object and process it as needed
        return customerDetails;
    }
}

