package IA.Electricity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Status {
    Centrales centrales;
    Clientes clientes;
    public Status() throws Exception {
        int seed = 10;

        centrales= new Centrales(new int[]{1, 0, 0},seed);
        clientes = new Clientes(80,new double[]{0.4,0.4,0.2},0.4,seed);


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

    double gananciaCliente(Cliente cliente, double consumo) throws Exception {
        double fine = (cliente.getConsumo()-consumo)*cliente.getCompensation();

        int tipo = cliente.getTipo();
        boolean garantizado = cliente.isGuaranteed();

        double precio;
        if (garantizado)
            precio = VEnergia.getTarifaClienteGarantizada(tipo);
        else
            precio = VEnergia.getTarifaClienteNoGarantizada(tipo);
        return precio*consumo-fine;
    }

    //Funcion que calcule el beneficio
    double beneficioPorCentral() throws Exception{
        double beneficioTotal = 0;
        ArrayList<Double>costeCentrales = new ArrayList<Double>(centrales.size());
        for (int i = 0; i < centrales.size(); ++i) {
            double costeCentral = 0;
            int tipo = centrales.get(i).getTipo();
            if (centrales.get(i).totalServedWithLoss() > 0) {
                double costeProduccionMW = VEnergia.getCosteProduccionMW(tipo);
                double costeMarcha = VEnergia.getCosteMarcha(tipo);
                costeCentral = costeProduccionMW*centrales.get(i).getProduccion()+costeMarcha;
                costeCentrales.add(i, costeCentral);
            }
            else {
                double costeParada = VEnergia.getCosteParada(tipo);
                costeCentrales.add(i, costeParada);
            }
        }
        System.out.println("Coste actual de las centrales que estan en marcha: ");
        System.out.println(costeCentrales);
        System.out.println("Coste total: " + costeCentrales.stream().reduce(0.0, (a, b) -> a + b));

        ArrayList<Double>beneficioCentrales = new ArrayList<>(centrales.size());
        for(int i = 0; i < centrales.size(); ++i) {
            double ganancias = 0;
            Central centralActual = centrales.get(i);
            ArrayList<Cliente> clientes = centralActual.getServing();
            Collections.sort(clientes, new Comparator<Cliente>() {
                @Override
                public int compare(Cliente cliente1, Cliente cliente2) {
                    //We want to serve the smaller clients first since they pay the MW at a higher price than the bigger ones

                    //TODO:
                    //Perhaps the energy distribution should be decided by the search algorithm and not by this function
                    //Since otherwise, we are bounding the problem.

                    if(cliente1.isGuaranteed() && !cliente2.isGuaranteed())return -1;
                    else if(cliente1.isGuaranteed()&&cliente2.isGuaranteed()){
                        if(cliente1.getTipo()>cliente2.getTipo())
                            return -1;
                        else return 1;
                    }
                    else{
                        if(cliente1.getTipo()>cliente2.getTipo())
                            return -1;
                        else return 1;
                    }
                }
            });

            double capacidadCentral = centralActual.getProduccion();

            for(int j=0; j<clientes.size();++j){
                if(clientes.get(j).getConsumo()>capacidadCentral){
                    ganancias+=gananciaCliente(clientes.get(j),capacidadCentral);
                    capacidadCentral=0;
                }
                else{
                    ganancias+=gananciaCliente(clientes.get(j),clientes.get(j).getConsumo());
                    capacidadCentral-=clientes.get(j).getConsumo();
                }
            }


            beneficioCentrales.add(i, ganancias - costeCentrales.get(i));
        }
        System.out.println("Beneficios de las centrales: ");
        System.out.println(beneficioCentrales);
        System.out.println("Beneficio total: " + beneficioCentrales.stream().reduce(0.0, (a, b) -> a + b));


        return 0;
    }

    //Asigna Centrales a clientes y clientes a centrales.
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