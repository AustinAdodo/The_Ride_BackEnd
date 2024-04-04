package the_ride.the_ride_backend.Services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;
import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;
import the_ride.the_ride_backend.Models.Driver.Driver;
import the_ride.the_ride_backend.Models.User.Customer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
public class MessagingService {
    private final static String QUEUE_NAME = "messages";
    private static final String RabbitMQ_server_hostname = "localhost";

    public MessagingService() {
    }

    public void sendRequestMessage(Driver driver) {
        sendMessage("Request", driver);
    }

    /**
     * These are accept or reject actions made by the driver only.
     */
    public void sendAcceptOrRejectMessage(Customer customer, String action) {
        sendMessage("Your trip was " + action, customer);
    }

    /**
     * sends a Notification Message to a User (This could be a Customer or Driver)
     */
    public void sendNotificationMessage(UserBaseModel<UUID> person, String message) {
        sendMessage(message, person);
    }

    /**
     * Generic method to send a message.
     * Creates a connection to the RabbitMQ server and a new queue.
     * Converts the message and user data to bytes.
     * Publishes the message and data to the queue.
     *
     * @param message The message to be sent.
     * @param data    The user data associated with the message.
     */
    private void sendMessage(String message, UserBaseModel<UUID> data) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RabbitMQ_server_hostname);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            byte[] messageBytes = message.getBytes();
            byte[] dataBytes = serializeObject(data);
            channel.basicPublish("", QUEUE_NAME, null, dataBytes);
            channel.basicPublish("", QUEUE_NAME, null, messageBytes);

            // Close the channel and connection to prevent memory leaks
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        return bos.toByteArray();
    }
}
