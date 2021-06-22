package main.java.com.ico.vrp.model;

public class Warehouse extends Visitable {

    public Warehouse(Location location, double[] timeWindow, String name) {
        super(location, timeWindow);
    }

    public Warehouse(double latitude, double longitude, double[] timeWindow, String name) {
        super(new Location(latitude, longitude, name), timeWindow);
    }

}
