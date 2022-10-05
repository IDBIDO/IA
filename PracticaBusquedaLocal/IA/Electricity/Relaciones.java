package IA.Electricity;

import java.util.*;


public class Relaciones{
    double brutoTotal;
    double costeTotal;
    ArrayList<Relacion> relaciones;//El integer hace referencia a la central.
    public Relaciones(Centrales centrales) throws Exception {
        this.relaciones = new ArrayList<Relacion>();
        for (Map.Entry<Integer, Central> entry : centrales.entrySet()) {
            Relacion nueva = new Relacion(entry.getValue().getId(),new ArrayList<Integer>());
            relaciones.add(nueva);
            this.costeTotal+=VEnergia.getCosteParada(entry.getValue().getTipo());
        }
        this.brutoTotal = 0;
        this.costeTotal = 0;
    }

    public Relaciones(Relaciones relaciones){
        this.relaciones = new ArrayList<Relacion>();
        for(Relacion relacion: relaciones.getRelaciones()){
            Relacion nueva = new Relacion(relacion);
            this.relaciones.add(nueva);
        }
        this.brutoTotal = relaciones.getBrutoTotal();
        this.costeTotal = relaciones.getCosteTotal();
    }

    public ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }

    public double getBrutoTotal() {
        return brutoTotal;
    }

    public double getCosteTotal(){
        return costeTotal;
    }


    public void asignaCliente(Cliente cliente, Central central) throws Exception {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        brutoTotal+=this.relaciones.get(central.getId()).addCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
        if(this.relaciones.get(central.getId()).clientesServidos()==1){
            costeTotal=costeTotal+(VEnergia.getCosteMarcha(central.getTipo())-VEnergia.getCosteParada(central.getTipo()));
            costeTotal=costeTotal+(VEnergia.getCosteProduccionMW(central.getTipo())* central.getProduccion());
        }
    }
    public void quitarCliente(Cliente cliente, Central central) throws Exception {
        double perdida = VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY());
        brutoTotal+=this.relaciones.get(central.getId()).deleteCliente(cliente.getId(),cliente.getPrecio(),perdida*cliente.getConsumo(), cliente.getConsumo());
        if(this.relaciones.get(central.getId()).clientesServidos()==0){
            costeTotal=costeTotal-(VEnergia.getCosteMarcha(central.getTipo())-VEnergia.getCosteParada(central.getTipo()));
            costeTotal=costeTotal-(VEnergia.getCosteProduccionMW(central.getTipo())* central.getProduccion());
        }
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

    public void print(Clientes clientes, Centrales centrales) {
        Set<Integer> clientesServidos = new HashSet<Integer>();
        for (Relacion relacion: relaciones) {
            centrales.get(relacion.getIdCentral()).print();
            System.out.println("Capacidad usada: "+String.valueOf(relacion.getMWUsados()));
            for(Integer clientId: relacion.getClientes()){
                Cliente cliente = clientes.get(clientId);
                cliente.print();
                clientesServidos.add(clientId);
            }
        }
        System.out.println("\nClientes no asignados a central: ");
        for (Map.Entry<Integer, Cliente> entry : clientes.entrySet()) {
            if(!clientesServidos.contains(entry.getKey())){
                entry.getValue().print();
            }
        }
    }
}
