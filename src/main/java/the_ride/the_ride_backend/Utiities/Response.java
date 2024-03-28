package the_ride.the_ride_backend.Utiities;

public class Response {
    private boolean authenticated;
    private String message;

    public Response(boolean authenticated, String message) {
        this.authenticated = authenticated;
        this.message = message;
    }

    // Getters (and setters if needed)
    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
