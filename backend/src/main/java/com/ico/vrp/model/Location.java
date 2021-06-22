package main.java.com.ico.vrp.model;

public class Location {

    private final String name;
    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude, String name) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "Latitude: " + latitude + " Longitude: " + longitude;
    }
}
