package main.java.com.ico.vrp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Customer {

    private Location location;
    private int[] timeWindow;
    private int quantity;
    private double price;
    private boolean allowPartial;

    public Customer(double latitude, double longitude, String[] timeWindow, int quantity, double price, boolean allowPartial) {
        this.location = new Location(latitude, longitude);
        this.timeWindow = generateTimeWindow(timeWindow);
        this.quantity = quantity;
        this.price = price;
        this.allowPartial = allowPartial;
    }

    public Location getLocation() {
        return location;
    }

    private int[] generateTimeWindow(String[] timeWindow){
        int[] parsed = new int[2];
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (int i = 0; i != timeWindow.length; i++) {
            try {
                Date date = sdf.parse(timeWindow[i]);
                cal.setTime(date);
                parsed[i] = cal.get(Calendar.HOUR)*60 + cal.get(Calendar.MINUTE);
            } catch (ParseException ignored) {}
        }
        return parsed;
    }

}
