package main.java.com.ico.vrp.model;

public class Warehouse extends Visitable {

    public Warehouse(Location location, double[] timeWindow) {
        super(location, timeWindow);
    }

    public Warehouse(double latitude, double longitude, double[] timeWindow) {
        super(new Location(latitude, longitude), timeWindow);
    }

}
