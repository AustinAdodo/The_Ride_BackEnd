package the_ride.the_ride_backend.Utiities;

public class LocationUtils {
    private static final double EARTH_RADIUS = 6371000; // Earth's radius in meters

    public static double metersToDegrees(double meters) {
        return meters / EARTH_RADIUS * (180 / Math.PI);
    }

    public static double degreesToMeters(double degrees) {
        return degrees * EARTH_RADIUS / (180 / Math.PI);
    }

    public static double[] calculateBoundingBox(double latitude, double longitude, double distance) {
        double latRadians = Math.toRadians(latitude);
        double lonRadians = Math.toRadians(longitude);

        // Calculate the delta longitude based on the given distance
        double deltaLon = metersToDegrees(distance / Math.cos(latRadians));

        // Calculate the bounding box
        double minLat = latitude - metersToDegrees(distance);
        double maxLat = latitude + metersToDegrees(distance);
        double minLon = longitude - deltaLon;
        double maxLon = longitude + deltaLon;

        return new double[]{minLat, maxLat, minLon, maxLon};
    }
}
