package main.java.com.ico.vrp.model;

public class Visitable extends Location {

    private final double[] timeWindow;

    public Visitable(Location location, double[] timeWindow) {
        super(location.getLatitude(), location.getLongitude());
        this.timeWindow = timeWindow;
    }

    public Visitable(double latitude, double longitude, double[] timeWindow) {
        super(latitude, longitude);
        this.timeWindow = timeWindow;
    }

    public double[] getTimeWindow() {
        return timeWindow;
    }

}
