package main.java.com.ico.vrp.model;

public class Vehicle {

    private int id;
    private int cargaTotal;
    private int deslocMax;
    private double custoDist;
    private double custoHora;

    private Visitable armazemPartida;

    public Vehicle(int cargaTotal, int deslocMax, int custoDist, int custoHora, Location armazemPartida) {
        this.cargaTotal = cargaTotal;
        this.deslocMax = deslocMax;
        this.custoDist = custoDist;
        this.custoHora = custoHora;
        this.armazemPartida = new Visitable(armazemPartida, new double[]{0,0});
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

    public double getCustoDist() {
        return custoDist;
    }

    public double getCustoHora() {
        return custoHora;
    }

    public int getId() {
        return id;
    }
}
