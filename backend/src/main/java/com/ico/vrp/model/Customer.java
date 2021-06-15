package main.java.com.ico.vrp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Customer extends Visitable {

    private final int quantity;
    private final double price;

    public Customer(double latitude, double longitude, String[] timeWindow, int quantity, double price) {
        super(latitude, longitude, generateTimeWindow(timeWindow));
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    private static double[] generateTimeWindow(String[] timeWindow){
        double[] parsed = new double[2];
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
