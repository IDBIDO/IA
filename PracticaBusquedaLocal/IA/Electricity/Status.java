package IA.Electricity;

import java.util.*;

public class Status {
    Centrales centrales;
    Clientes clientes;
    Relaciones relaciones;

    public Status(int seed) throws Exception {
        centrales= new Centrales(new int[]{1, 0, 0},seed);
        clientes = new Clientes(30,new double[]{0.4,0.4,0.2},0.6,seed);
        relaciones = new Relaciones(centrales);
        initialSolution1(false,seed);
    }

    public Status(Status status){
        this.centrales=status.getCentrales();
        this.clientes=status.getClientes();
        this.relaciones=new Relaciones(status.getRelaciones());
    }

    void initialSolution1(boolean includeNoGuaranteed, int seed) throws Exception {

        Random r = new Random(seed);
        ArrayList<Integer>centralIds = centrales.getIds();
        int actualCentralIndex = r.nextInt(centrales.size());

        int i = 0;

        for (Map.Entry<Integer,Cliente> entry : clientes.entrySet()) {
            {
                if (entry.getValue().isGuaranteed()) {
                    Central actualCentral = centrales.get(centralIds.get(actualCentralIndex));
                    if (canServe(entry.getValue(),actualCentral)) {
                        relaciones.asignaCliente(entry.getValue(),actualCentral);
                    } else {
                        --i;
                    }
                    actualCentralIndex = r.nextInt(centrales.size());
                }
                ++i;
            }
        }
        System.out.println("------------------------------------------ ");

        System.out.println("Initial solutions: ");
        relaciones.print(clientes,centrales);
        System.out.println("Beneficio: "+String.valueOf(beneficioPorCentral()));
    }

    public void printState(){
        relaciones.print(clientes,centrales);
    }

    public boolean canServe(Cliente cliente,Central central) {
        return relaciones.puedeAsignarse(cliente,central);
    }

    //Funcion que calcule el beneficio
    public double beneficioPorCentral() throws Exception{
        return relaciones.getBrutoTotal()-relaciones.getCosteTotal();
    }

    //operadores

    public Clientes getClientes(){
        return clientes;
    }
    public Centrales getCentrales(){
        return centrales;
    }
    public Relaciones getRelaciones(){
        return relaciones;
    }

    public double heuristic1() throws Exception {
        return beneficioPorCentral();
    }

    public boolean is_goal(){
        return false;
    }

    void quitarCliente(Cliente cliente, Central central) throws Exception {
        relaciones.quitarCliente(cliente,central);
    }

    void asignarCliente(Cliente cliente, Central central) throws Exception {
        relaciones.asignaCliente(cliente,central);
    }

}