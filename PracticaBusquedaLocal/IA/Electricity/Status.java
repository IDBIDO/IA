package IA.Electricity;

import java.util.ArrayList;
import java.util.Random;

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
    void initialSolution1(boolean includeNoGuaranteed){
        Random r = new Random();
        int actualCentralIndex = r.nextInt(centrales.size()-1);

        //solo clientes garantizados
        int i = 0;

        while (i < clientes.size()) {
            boolean includeClient = clientes.get(i).isGuaranteed();
            if (includeNoGuaranteed) includeClient = true;
            if (includeClient) {
                Central actualCentral = centrales.get(actualCentralIndex);
                double capacidadRestante = actualCentral.getProduccion() - actualCentral.totalServedWithLoss();
                double perdidaConsumoCliente = clientes.get(i).getConsumo()*(1+VEnergia.getPerdida(actualCentral.getCoordX(),actualCentral.getCoordY(),clientes.get(i).getCoordX(),clientes.get(i).getCoordY()));
                if (capacidadRestante >= perdidaConsumoCliente) {
                    actualCentral.addClient(clientes.get(i));
                    clientes.get(i).setCentral(actualCentral);
                } else {
                    --i;
                }

                actualCentralIndex = r.nextInt(centrales.size()-1);
            }
            ++i;
        }
        System.out.println("------------------------------------------ ");

        System.out.println("Initial solutions: ");
        centrales.print();
        clientes.print();
        //System.out.println(centrales.size());
        //System.out.println(actualCentralIndex);
    }

    //Asigna Centrales a clientes y clientes a centrales en una configuración valida.
    void initialSolution2(){}

    //OPERADORES
    //void swap(Central central1, Central central2){}
    //void mueveCliente(Cliente cliente, Central centralDestino){}
    //void mueveBulk(Clientes clientes, Central centralDestino){}

}
