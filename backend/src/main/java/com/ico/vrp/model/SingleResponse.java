package main.java.com.ico.vrp.model;

public class SingleResponse {

    private int vehicleId;
    private Location[] clients;

    public SingleResponse(int vehicleId, Location[] clients){
        this.vehicleId = vehicleId;
        this.clients = clients;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public Location[] getClients() {
        return clients;
    }
}
