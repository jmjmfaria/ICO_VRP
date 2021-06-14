package main.java.com.ico.vrp.model;

public class Customer {

    private Location location;
    private String[] timeWindow;
    private int quantity;
    private double price;
    private boolean allowPartial;

    public Customer(double latitude, double longitude, String[] timeWindow, int quantity, double price, boolean allowPartial) {
        this.location = new Location(latitude, longitude);
        this.timeWindow = timeWindow;
        this.quantity = quantity;
        this.price = price;
        this.allowPartial = allowPartial;
    }

    public Location getLocation() {
        return location;
    }

}
