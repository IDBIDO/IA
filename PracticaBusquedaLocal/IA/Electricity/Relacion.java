package IA.Electricity;

import java.util.Set;

public class Relacion {
    double MWVendidos;
    double MWUsados;
    int idCentral;
    double ganancia;
    Set<Integer> clientes;

    public Relacion(int idCentral, Set<Integer>clientes){
        this.MWVendidos = 0;
        this.MWUsados = 0;
        this.ganancia = 0;
        this.idCentral = idCentral;
        this.clientes = clientes;
    }

    public void addCliente(int cliente, double precioCliente, double perdida, double venta) {
        clientes.add(cliente);
        MWVendidos+=venta;
        MWUsados+=(perdida+venta);
        ganancia +=venta*precioCliente;
    }
    public void deleteCliente(int cliente,double precioCliente, double perdida, double venta){
        clientes.remove(cliente);
        MWVendidos-=venta;
        MWUsados+=(perdida+venta);
        ganancia-=(venta*perdida);
    }
    public boolean isthereCliente(int cliente){
        return clientes.contains(cliente);
    }

    public double getMWVendidos() {
        return MWVendidos;
    }

    public int getIdCentral(){
        return idCentral;
    }

    public double getMWUsados(){
        return MWUsados;
    }

    public int clientesServidos(){
        return clientes.size();
    }

    public Set<Integer> getClientes() {
        return clientes;
    }

    public double getGanancia(){
        return ganancia;
    }

}