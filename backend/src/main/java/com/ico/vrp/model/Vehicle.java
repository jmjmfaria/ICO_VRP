package main.java.com.ico.vrp.model;

public class Vehicle {

    private final int cargaTotal;
    private final int deslocMax;
    private final Location armazemPartida;

    public Vehicle(int cargaTotal, int deslocMax, Location armazemPartida) {
        this.cargaTotal = cargaTotal;
        this.deslocMax = deslocMax;
        this.armazemPartida = armazemPartida;
    }

    public Location getArmazemPartida() {
        return armazemPartida;
    }

}
