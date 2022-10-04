package IA.Electricity;

import java.util.*;

public class Status {
    Centrales centrales;
    Clientes clientes;
    Relaciones relaciones;

    public Status(int seed) throws Exception {
        centrales= new Centrales(new int[]{3, 0, 0},seed);
        clientes = new Clientes(30,new double[]{0.4,0.4,0.2},0.6,seed);
        relaciones = new Relaciones();
        initialSolution1(false);
    }

    private void initialSolution1(boolean includeNonGuaranteed) {

    }

    public Status(Status state) {
        this.centrales = new Centrales(state.centrales);
        this.clientes = new Clientes(state.clientes);
        for(int i=0;i<state.centrales.size();++i){
            for(int j=0;j<state.centrales.get(i).getServing().size();++j){
            }
        }
    }

    public void printState() {
        centrales.print();
        clientes.print();
    }

    //Funcion que calcule el beneficio
    double beneficioPorCentral() throws Exception{
        double beneficioTotal = 0;
        for (Map.Entry<Integer,Relacion> entry : relaciones.entrySet()){
            double coste = 0;
            double ganancia = 0;
            Central central = centrales.get(entry.getKey());
            Relacion relacion = entry.getValue();
            if(relacion.clientesServidos()!=0){
                double costeProduccionMW = VEnergia.getCosteProduccionMW(central.getTipo());
                double costeMarcha = VEnergia.getCosteMarcha(central.getTipo());
                coste = costeProduccionMW*central.getProduccion()+costeMarcha;
                ganancia = relacion.getGanancia();
            }
            else{
                coste = VEnergia.getCosteParada(central.getTipo());
            }
            beneficioTotal +=(ganancia-coste);
        }
        return beneficioTotal;
    }

    //operadores

    public double heuristic() throws Exception {
        return beneficioPorCentral();
    }

    public boolean is_goal(){
        return false;
    }

    void quitarCliente(Cliente cliente, Central central){
        relaciones.quitarCliente(cliente,central);
    }

    void asignarCliente(Cliente cliente, Central central){
        relaciones.quitarCliente(cliente,central);
    }

}