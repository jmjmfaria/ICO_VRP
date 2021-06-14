package main.java.com.ico.vrp.model;

public class Location {

    private final double latitude;
    private final double longitude;
    private final int[] timeWindow;

    public Location(double latitude, double longitude, int[] timeWindow) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeWindow = timeWindow;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int[] getTimeWindow() {
        return timeWindow;
    }

    @Override
    public String toString() {
        return  "Latitude: " + latitude + " Longitude: " + longitude;
    }
}
