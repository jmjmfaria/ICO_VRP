package main.java.com.ico.vrp.model;

public class Vehicle {

    private int id;
    private int cargaTotal;
    private int deslocMax;

    private Visitable armazemPartida;

    public Vehicle(int cargaTotal, int deslocMax, Location armazemPartida) {
        this.cargaTotal = cargaTotal;
        this.deslocMax = deslocMax;
        this.armazemPartida = new Visitable(armazemPartida, new int[]{0,0});
    }

    public Visitable getArmazemPartida() {
        return armazemPartida;
    }

    public int getCargaTotal() {
        return cargaTotal;
    }

    public int getDeslocMax() {
        return deslocMax;
    }

    public int getId() {
        return id;
    }
}
