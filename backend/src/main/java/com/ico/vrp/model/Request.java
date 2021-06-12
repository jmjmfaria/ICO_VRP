package main.java.com.ico.vrp.model;

public class Request {

    private Location[] customers;

    public Request(Location[] customers) {
        this.customers = customers;
    }

    public Location[] getLocations() {
        return customers;
    }

}
