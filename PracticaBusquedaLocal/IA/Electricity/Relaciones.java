package IA.Electricity;

import java.util.LinkedHashMap;
import java.util.Map;


public class Relaciones{
    Map<Integer,Relacion> relaciones;//El integer hace referencia a la central.
    public Relaciones(){
        this.relaciones = new LinkedHashMap<Integer, Relacion>();
    }
    public void asignaCliente(Cliente cliente, Central central){
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        this.relaciones.get(central.getId()).addCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
    }
    public void quitarCliente(Cliente cliente, Central central) {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        this.relaciones.get(central).deleteCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
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
}
