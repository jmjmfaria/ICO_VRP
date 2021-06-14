package main.java.com.ico.vrp.model;

public class Request {

    private final Customer[] clientes;
    private final Vehicle[] veiculos;

    public Request(Customer[] clientes, Vehicle[] veiculos) {
        this.clientes = clientes;
        this.veiculos = veiculos;
    }

    public Customer[] getClientes() {
        return clientes;
    }

    public Vehicle[] getVeiculos() {
        return veiculos;
    }

    public Location getWarehouse() { return veiculos[0].getArmazemPartida(); }

}
