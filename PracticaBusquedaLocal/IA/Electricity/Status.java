package IA.Electricity;

import java.util.ArrayList;

public class Status {
    Centrales centrales;
    Clientes clientes;
    public Status() throws Exception {
        int seed = 10;

        centrales= new Centrales(new int[]{5, 5, 5},seed);
        clientes = new Clientes(30,new double[]{0.4,0.4,0.2},0.4,seed);


        /*
        clientes.get(0).setCentral(centrales.get(0));
        clientes.get(1).setCentral(centrales.get(0));

        centrales.get(1).addClient(clientes.get(1));
        centrales.get(1).deleteClient(clientes.get(1));

        clientes.get(1).setCentral(centrales.get(1));
        */

        centrales.print();
        clientes.print();
    }

    public void unAssignAll(){
        for(int i=0;i<centrales.size();++i){
            ArrayList<Cliente> clientes = centrales.get(i).getServing();
            for(int j=0;j<clientes.size();++j)
                centrales.get(i).deleteClient(clientes.get(j));
        }
    }

    //Funcion que calcule el beneficio
    double beneficio(){return 0;}

    //Asigna Centrales a clientes y clientes a centrales en una configuración valida.
    void initialSolution1(){}

    //Asigna Centrales a clientes y clientes a centrales en una configuración valida.
    void initialSolution2(){}

    //OPERADORES
    //void swap(Central central1, Central central2){}
    //void mueveCliente(Cliente cliente, Central centralDestino){}
    //void mueveBulk(Clientes clientes, Central centralDestino){}

}
