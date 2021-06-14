package main.java.com.ico.vrp.model;

public class Visitable extends Location {

    private final int[] timeWindow;

    public Visitable(Location location, int[] timeWindow) {
        super(location.getLatitude(), location.getLatitude());
        this.timeWindow = timeWindow;
    }

    public Visitable(double latitude, double longitude, int[] timeWindow) {
        super(latitude, longitude);
        this.timeWindow = timeWindow;
    }

    public int[] getTimeWindow() {
        return timeWindow;
    }

}
