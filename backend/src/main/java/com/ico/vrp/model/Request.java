package main.java.com.ico.vrp.model;

public class Request {

    private Location warehouse;
    private Location[] customers;

    public Request(Location warehouse, Location[] customers) {
        this.warehouse = warehouse;
        this.customers = customers;
    }

    public Location[] getLocations() {
        return customers;
    }

    public Location getWarehouse() { return warehouse; }

}
