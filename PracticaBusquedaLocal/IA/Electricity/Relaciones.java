package IA.Electricity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class Relaciones{
    Map<Integer,Relacion> relaciones;//El integer hace referencia a la central.
    public Relaciones(Centrales centrales) {
        this.relaciones = new LinkedHashMap<Integer, Relacion>();
        for (Map.Entry<Integer, Central> entry : centrales.entrySet()) {
            Relacion nueva = new Relacion(entry.getValue().getId(),new HashSet<Integer>());
            relaciones.put(entry.getValue().getId(),nueva);
        }
    }
    public void asignaCliente(Cliente cliente, Central central){
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        this.relaciones.get(central.getId()).addCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
    }
    public void quitarCliente(Cliente cliente, Central central) {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        this.relaciones.get(central).deleteCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
    }

    public boolean puedeAsignarse(Cliente cliente, Central central){
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        double gasto = cliente.getConsumo()*(1+perdida);
        double consumido = relaciones.get(central.getId()).getMWUsados();
        return ((consumido+gasto)<=central.getProduccion());
    }

    public int size() {
        return relaciones.size();
    }
    public Relacion get(int id){
        return relaciones.get(id);
    }

    public Iterable<? extends Map.Entry<Integer, Relacion>> entrySet() {
        return relaciones.entrySet();
    }

    public void print(Clientes clientes, Centrales centrales) {
        Set<Integer> clientesServidos = new HashSet<Integer>();
        for (Map.Entry<Integer, Relacion> entry : relaciones.entrySet()) {
            centrales.get(entry.getKey()).print();
            System.out.println("Capacidad usada: "+String.valueOf(entry.getValue().getMWUsados()));
            for(Integer clientId: entry.getValue().getClientes()){
                Cliente cliente = clientes.get(clientId);
                cliente.print();
                clientesServidos.add(clientId);
            }
        }
        System.out.println("Clientes no asignados a central: ");
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            if(!clientesServidos.contains(entry.getKey())){
                entry.getValue().print();
            }
        }
    }
}
