package IA.Electricity;

import java.util.*;

public class Status {
    Centrales centrales;
    Clientes clientes;
    public Status(int seed) throws Exception {

        centrales= new Centrales(new int[]{10, 0, 0},seed);
        clientes = new Clientes(90,new double[]{0.4,0.4,0.2},0.4,seed);
        //It is useful to sort the customers so that all guaranteed are before the others.
        //If two customers are guaranteed their position is decided depending on their type
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente cliente1, Cliente cliente2) {
                //We want to serve the smaller clients first since they pay the MW at a higher price than the bigger ones

                //TODO:
                //Perhaps the energy distribution should be decided by the search algorithm and not by this function
                //Since otherwise, we are bounding the problem.

                if(cliente1.isGuaranteed() && !cliente2.isGuaranteed())return -1;
                else if(!cliente1.isGuaranteed() && cliente2.isGuaranteed())return 1;
                else if(cliente1.isGuaranteed()&&cliente2.isGuaranteed()){
                    if(cliente1.getTipo()>cliente2.getTipo())
                        return -1;
                    else return 1;
                }
                else{
                    if(cliente1.getTipo()>cliente2.getTipo())
                        return -1;
                    else if(cliente1.getTipo()<cliente2.getTipo())
                        return 1;
                    else return 0;
                }
            }
        });

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

    double gananciaCliente(Cliente cliente) throws Exception {

        int tipo = cliente.getTipo();
        boolean garantizado = cliente.isGuaranteed();

        double precio;
        if (garantizado)
            precio = VEnergia.getTarifaClienteGarantizada(tipo);
        else
            precio = VEnergia.getTarifaClienteNoGarantizada(tipo);
        return precio*cliente.getConsumo();
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

            /*
            for(int p=0;p<clientes.size();++p)
                System.out.println(clientes.get(p).isGuaranteed()+" "+clientes.get(p).getTipo()+" "+clientes.get(p).getConsumo());
            */

            for(int j=0; j<clientes.size();++j){
                ganancias+=gananciaCliente(clientes.get(j));
            }


            beneficioCentrales.add(i, ganancias - costeCentrales.get(i));
        }
        System.out.println("Beneficios de las centrales: ");
        System.out.println(beneficioCentrales);
        System.out.println("Beneficio total: " + beneficioCentrales.stream().reduce(0.0, (a, b) -> a + b));


        return 0;
    }

    boolean canServe(Central central, Cliente cliente) {
        double capacidadRestante = central.getProduccion() - central.totalServedWithLoss();
        double perdidaConsumoCliente = cliente.getConsumo()*(1+VEnergia.getPerdida(central.getCoordX(),central.getCoordY(),cliente.getCoordX(),cliente.getCoordY()));
        if (capacidadRestante >= perdidaConsumoCliente) {
            return true;
        }
        return false;
    }

    int anyCentralCanServe(Cliente cliente) {
        for (int i = 0; i < centrales.size(); ++i) {
            if (canServe(centrales.get(i),cliente)) return i;
        }
        return -1;
    }

    //Asigna Centrales a clientes de manera que no sobrepasa la capacidad de una central

    void initialSolution1(boolean includeNoGuaranteed) {

        Random r = new Random();

        int actualCentralIndex = r.nextInt(centrales.size());

        int i = 0;

        while (i < clientes.size()) {

            if (clientes.get(i).isGuaranteed()) {
                Central actualCentral = centrales.get(actualCentralIndex);
                if (canServe(actualCentral, clientes.get(i))) {
                    actualCentral.addClient(clientes.get(i));
                    clientes.get(i).setCentral(actualCentral);
                } else {
                    --i;
                }
                actualCentralIndex = r.nextInt(centrales.size());

            }
            ++i;
        }
        i = 0;
        //asign noguaranteed client
        if (includeNoGuaranteed)
            while (i < clientes.size()) {

                if (!clientes.get(i).isGuaranteed()) {
                    Central actualCentral = centrales.get(actualCentralIndex);
                    if (canServe(actualCentral, clientes.get(i))) {
                        actualCentral.addClient(clientes.get(i));
                        clientes.get(i).setCentral(actualCentral);
                    } else {
                        int rcode = anyCentralCanServe(clientes.get(i));
                        if (rcode > 0) {
                            --i;
                        } else break;
                    }
                    actualCentralIndex = r.nextInt(centrales.size());
                }
                ++i;
            }
        System.out.println("------------------------------------------ ");

        System.out.println("Initial solutions: ");
        centrales.print();
        clientes.print();
    }

    //Asigna Centrales a clientes y clientes a centrales de forma aleatoria sin sobrepasar la capacidad de ninguna central

    void initialSolution2(){
        Random r = new Random();

        int i = 0;

        while (i < clientes.size()) {
            int actualCentralIndex = r.nextInt(centrales.size());
            Central actualCentral = centrales.get(actualCentralIndex);
            double capacidadRestante = actualCentral.getProduccion() - actualCentral.totalServedWithLoss();
            double perdidaConsumoCliente = clientes.get(i).getConsumo()*(1+VEnergia.getPerdida(actualCentral.getCoordX(),actualCentral.getCoordY(),clientes.get(i).getCoordX(),clientes.get(i).getCoordY()));
            if (capacidadRestante >= perdidaConsumoCliente) {
                actualCentral.addClient(clientes.get(i));
                clientes.get(i).setCentral(actualCentral);
                ++i;
            }
            else {
                if (!clientes.get(i).isGuaranteed()) {
                    ++i;
                }
            }
        }

        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        centrales.print();
        clientes.print();
        //System.out.println(centrales.size());
        //System.out.println(actualCentralIndex);
    }
    //Assigns customers to power plants so that every power plant roughly has the same NUMBER of customers.
    //The even distribution of energy, either proportional to the power plant capacity or absolutely,
    //is not being taken into account.
    void initialSolution3(){
        int actualCentralIndex = 0;

        int i = 0;

        while (i < clientes.size()) {
            Central actualCentral = centrales.get(actualCentralIndex);
            double capacidadRestante = actualCentral.getProduccion() - actualCentral.totalServedWithLoss();
            double perdidaConsumoCliente = clientes.get(i).getConsumo()*(1+VEnergia.getPerdida(actualCentral.getCoordX(),actualCentral.getCoordY(),clientes.get(i).getCoordX(),clientes.get(i).getCoordY()));
            if (capacidadRestante >= perdidaConsumoCliente) {
                actualCentral.addClient(clientes.get(i));
                clientes.get(i).setCentral(actualCentral);
            } else {
                --i;
            }
            ++actualCentralIndex;
            if(actualCentralIndex>=centrales.size())actualCentralIndex=0;
            ++i;
        }

        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        centrales.print();
        clientes.print();
        //System.out.println(centrales.size());
        //System.out.println(actualCentralIndex);
    }
    //Intenta minimizar la distancia entre una central y cliente
    void initialSolution4(){
        int actualCentralIndex = 0;
        int i = 0;

        while (i < clientes.size()) {
            int central = 0;
            double min = -1;
            for(int j=0;j<centrales.size();++j){
                double consumoCliente = clientes.get(i).getConsumo()*(1+VEnergia.getPerdida(centrales.get(j).getCoordX(),centrales.get(j).getCoordY(),clientes.get(i).getCoordX(),clientes.get(i).getCoordY()));
                double capacidadRest = centrales.get(j).getProduccion() - centrales.get(j).totalServedWithLoss();
                double perdida =VEnergia.euclidea(centrales.get(j).getCoordX(),centrales.get(j).getCoordY(),clientes.get(i).getCoordX(),clientes.get(i).getCoordY());
                if(capacidadRest-consumoCliente>=0){
                    if(min<0 || min>perdida){
                        min = perdida;
                        central =j;
                    }
                }
            }

            Central actualCentral = centrales.get(central);
            actualCentral.addClient(clientes.get(i));
            clientes.get(i).setCentral(actualCentral);

            ++i;
        }

        System.out.println("------------------------------------------ ");
        System.out.println("Initial solutions: ");
        centrales.print();
        clientes.print();
        //System.out.println(centrales.size());
        //System.out.println(actualCentralIndex);
    }


    void swap(Central central1, Central central2){
        ArrayList<Cliente> tmp = central1.getServing();
        //Clientes de la central2 se copian en la central1

        eliminaClientes(central1);
        addClientes(central1,central2.getServing());
        //Clientes de la central1 se copian en la central2
        eliminaClientes(central2);
        addClientes(central2,tmp);
    }

    boolean canSwap(Cliente cliente1, Cliente cliente2) {
        if (cliente1.estaServido() && cliente2.estaServido()) {
            Central central1 = cliente1.getServer();
            Central central2 = cliente2.getServer();

        }
        return false;
    }

    /**
     * intercambio de centrales de cliente 1 y cliente 2
     * @param cliente1
     * @param cliente2
     */
    void swapCliente(Cliente cliente1, Cliente cliente2) {
        Central central1 = cliente1.getServer();
        Central aux1 = new Central(central1.getTipo(), central1.getProduccion(), central1.getCoordX(), central1.getCoordY());
        Central central2 = cliente2.getServer();
        //Central aux2 = new Central(central1.getTipo(), central1.getProduccion(), central1.getCoordX(), central1.getCoordY());

        cliente1.setCentral(central2);
        cliente2.setCentral(aux1);
    }

    /**
     * cambia la central del cliente a la centralDestino
     * @param cliente
     * @param centralDestino
     */
    void mueveCliente(Cliente cliente, Central centralDestino){
        cliente.setCentral(centralDestino);
    }

    /**
     * mueve un conjunto de clientes a una centralDesinto
     * @param clientes
     * @param centralDestino
     */
    void mueveBulk(Clientes clientes, Central centralDestino){
        for (Cliente c : clientes)
            c.setCentral(centralDestino);
    }

    /**
     * desasigna el cliente de su central
     * @param cliente
     */
    void quitarCliente(Cliente cliente){
        cliente.unsetCentral();
    }

    /**
     * asigna el cliente a una central
     * @param cliente
     * @param central
     */
    void asignarCliente(Cliente cliente, Central central){ cliente.setCentral(central);}

    //Auxiliares
    /**
     * elimina todos los clientes de la central
     * @param central
     */
    void eliminaClientes(Central central){
        while (!central.getServing().isEmpty())
            central.deleteClient(central.getServing().get(0));
    }

    /**
     * copia todos los clientes de la ArrayList clientes a la central destino
     * @param destino
     * @param clientes
     */
    void addClientes(Central destino, ArrayList<Cliente> clientes){
        for ( Cliente c : clientes)
            destino.addClient(c);
    }

}