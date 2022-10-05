package IA.Electricity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Relacion {
    int idCentral;
    double MWUsados;
    ArrayList<Integer> clientes;

    public Relacion(int idCentral, ArrayList<Integer>clientes){
        this.idCentral = idCentral;
        this.clientes = clientes;
    }

    public Relacion(Relacion relacion) {
        this.MWUsados = relacion.getMWUsados();
        this.idCentral = relacion.getIdCentral();
        this.clientes = (ArrayList<Integer>) relacion.getClientes().clone();
    }

    public double addCliente(int cliente, double precioCliente, double perdida, double venta) {
        clientes.add(cliente);
        MWUsados+=(perdida+venta);
        return (venta*precioCliente);
    }
    public double deleteCliente(int cliente,double precioCliente, double perdida, double venta){

        clientes.remove(clientes.indexOf(cliente));//Could be sped up
        MWUsados-=(perdida+venta);
        return (-venta*precioCliente);
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

    public ArrayList<Integer> getClientes() {
        return clientes;
    }

}