package main.java.com.ico.vrp.model;

public class Vehicle {

    private int cargaTotal;
    private int deslocMax;
    private Location armazemPartida;

    public Vehicle(int cargaTotal, int deslocMax, Location armazemPartida) {
        this.cargaTotal = cargaTotal;
        this.deslocMax = deslocMax;
        this.armazemPartida = armazemPartida;
    }

    public Location getArmazemPartida() {
        return armazemPartida;
    }

}
