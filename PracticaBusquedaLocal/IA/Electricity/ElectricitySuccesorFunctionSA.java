package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import aima.util.Pair;

import java.util.*;


public class ElectricitySuccesorFunctionSA implements SuccessorFunction {
    private static Random random;

    public ElectricitySuccesorFunctionSA() {
        random = new Random();
    }

    public static void swapcentrales(ArrayList<Cliente> clientes, ArrayList<Central> centrales, Map<Integer, ArrayList<Integer>> centralesClientes, int i, int j, Status statusAux) throws Exception {
        for(int p = 0; p< centralesClientes.get(i).size(); ++p){
            statusAux.quitarCliente(clientes.get(centralesClientes.get(i).get(p)), centrales.get(i));
            statusAux.asignarCliente(clientes.get(centralesClientes.get(i).get(p)), centrales.get(j));
        }
        for(int p = 0; p< centralesClientes.get(j).size(); ++p){
            statusAux.quitarCliente(clientes.get(centralesClientes.get(j).get(p)), centrales.get(j));
            statusAux.asignarCliente(clientes.get(centralesClientes.get(j).get(p)), centrales.get(i));
        }
    }

    @Override
    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        Status status = (Status) state;
        Relaciones relaciones = status.getRelaciones();
        Clientes clientesaux = status.getClientes();
        Centrales centralesaux = status.getCentrales();
        ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
        ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());
        ArrayList<Integer>centralesClientes = relaciones.getClientes();

        int swapCliente = relaciones.getClientes().size();
        int clientSize = relaciones.getClientes().size();
        ArrayList<Cliente> noGuaranteeAsignedClientes = status.getNoGuaranteeAsignedClientes();
        int noguaranteedSize = noGuaranteeAsignedClientes.size();

        /*
        int nSwapCentral = 0;
        int cont = centrales.size()-1;
        for (int i = 0; i < cont; ++i) {
            nSwapCentral += cont;
            --cont;
        }
        */
        //System.out.println("Centrales:" +centrales.size());1172958.4  1230648.0
        //System.out.println("nSwapCentral:" + nSwapCentral);

        int dominioSuccesor = swapCliente + clientSize + noguaranteedSize -1;


        int ran = random.nextInt(dominioSuccesor);    // [0, clientes.size -1] -> asignar cliente,
                                                    // [clientes.size, cliente.size + noGuaranteeAsignedClientes.size()] -> quitarCliente


        if (ran < swapCliente) {

            //coger una asignacion cliente-> central valida
            int uidCliente1 = random.nextInt(centralesClientes.size());
            while(centralesClientes.get(uidCliente1) == -1) uidCliente1 = random.nextInt(centralesClientes.size());

            //coger otra asignacion cliente -> central que no sea la anteriol

            int uidCliente2 = random.nextInt(centralesClientes.size());
            while(centralesClientes.get(uidCliente2) == -1 || uidCliente2 == uidCliente1) uidCliente2 = random.nextInt(centralesClientes.size());

            //System.out.println("uidCliente1 " + centralesClientes.get(uidCliente1));
            //System.out.println("uidCliente2 " + centralesClientes.get(uidCliente2));

            Cliente cliente1 = clientes.get(uidCliente1);
            Cliente cliente2 = clientes.get(uidCliente2);
            Central central1 = centrales.get(centralesClientes.get(uidCliente1));
            Central central2 = centrales.get(centralesClientes.get(uidCliente2));

            while (!( status.canChange(cliente1, cliente2, central1) && status.canChange(cliente2, cliente1, central2) ) ) {
                uidCliente1 = random.nextInt(centralesClientes.size());
                while(centralesClientes.get(uidCliente1) == -1) uidCliente1 = random.nextInt(centralesClientes.size());
                cliente1 = clientes.get(uidCliente1);
                central1 = centrales.get(centralesClientes.get(uidCliente1));

                uidCliente2 = random.nextInt(centralesClientes.size());
                while(centralesClientes.get(uidCliente2) == -1 || uidCliente2 == uidCliente1) uidCliente2 = random.nextInt(centralesClientes.size());
                cliente2 = clientes.get(uidCliente2);
                centrales.get(centralesClientes.get(uidCliente2));
            }

            Status statusAux = new Status(status);
            try {
                statusAux.swapCliente(cliente1, central1, cliente2, central2);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            retval.add(new Successor("MoverCliente(" + uidCliente1 + " <-> " + uidCliente2 + ")", statusAux));

        }

        else if (ran < swapCliente+clientSize) {
            int uidCliente = random.nextInt(centralesClientes.size());      //coger un cliente random
            Cliente cliente = clientes.get(uidCliente);

            int centralKey = random.nextInt(centrales.size());      //coger una central random
            if (centralesClientes.get(uidCliente) == -1) {     //no central asignado, asignar cliente
                //System.out.println(1);
                while(!status.canServe(cliente, centrales.get(centralKey))) {
                    Central aux = status.puedeAsignarAlgunCentral(cliente);
                    if (aux != null) {
                        centralKey = aux.getId();
                    }
                    else return retval;
                }
                Status statusAux = new Status(status);
                try {
                    statusAux.asignarCliente(cliente, centrales.get(centralKey));   //asignar nueva central
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                retval.add(new Successor("AsignarCliente(" + String.valueOf(uidCliente) + "," + String.valueOf(centralKey) + ")", statusAux));

            }

            else {      //hay que quitar de su central
                while(centralesClientes.get(uidCliente) == centralKey || !status.canServe(cliente, centrales.get(centralKey))) {
                    //centralKey = random.nextInt(centrales.size());
                    Central aux = status.puedeAsignarAlgunCentral(cliente);

                    if (aux != null) {
                        centralKey = aux.getId();
                    }
                    else return retval;
                }

                Status statusAux = new Status(status);
                try {
                    statusAux.quitarCliente(cliente, centrales.get( centralesClientes.get(uidCliente) ));       //quitar la central originak
                    statusAux.asignarCliente(cliente, centrales.get(centralKey));   //asignar nueva central
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                retval.add(new Successor("AsignarCliente(" + String.valueOf(uidCliente) + "," + String.valueOf(centralKey) + ")", statusAux));

            }

        }

        else if (ran < noguaranteedSize + swapCliente+clientSize) {
            //System.out.println("Quitar Cliente");
            int ranNoGuaranteed = random.nextInt(noguaranteedSize);
            Cliente cliente = noGuaranteeAsignedClientes.get(ranNoGuaranteed);
            int centralKey = relaciones.getClientes().get(cliente.getId());
            Central central = centrales.get(centralKey);
            Status statusAux = new Status(status);
            try {
                statusAux.quitarCliente(cliente, central);
                //statusAux.asignarCliente(cliente, centrales.get(centralKey));   //asignar nueva central
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            retval.add(new Successor("QuitarCliente (" + String.valueOf(cliente.getId()) + "," + String.valueOf(centralKey) + ")", statusAux));

        }

        //swap central
        /*
        else {
            //System.out.println("Swap central");
            ArrayList<Pair> canSwapCentral =  status.getCanSwapCentral();
            int ranPair = random.nextInt(canSwapCentral.size());


            Map<Integer,ArrayList<Integer>> centralesClientes1= new HashMap<Integer,ArrayList<Integer>>();
            for(int i=0;i<centrales.size();++i){
                centralesClientes1.put(centrales.get(i).getId(),new ArrayList<Integer>());
            }
            for(int i=0;i<relaciones.getClientes().size();++i){
                if(relaciones.getClientes().get(i)!=-1){
                    ArrayList<Integer> lista = centralesClientes1.get(relaciones.getClientes().get(i));
                    lista.add(i);
                    centralesClientes1.put(relaciones.getClientes().get(i),lista);
                }
            }
            ArrayList<Pair> aux = new ArrayList<>();
            for (int i = 0; i < centrales.size(); ++i) {
                for (int j = i+1; j < centrales.size(); ++j) {
                    if(centrales.get(i).getTipo()==centrales.get(j).getTipo()) {
                        if (status.canSwapCentral(centrales.get(i), centrales.get(j), centralesClientes1.get(i), centralesClientes1.get(j))) {
                            Pair a = new Pair(i, j);
                            aux.add(a);
                        }
                    }
                }
            }
            if (aux.size() > 0) {
                int ranSwapPair = random.nextInt(aux.size());
                Pair a = aux.get(ranSwapPair);
                int central1Key = (Integer) a.getFirst();
                int central2Key = (Integer) a.getSecond();
                Status statusAux = new Status(status);
                try {
                    swapcentrales(clientes, centrales, centralesClientes1, central1Key, central2Key, statusAux);
                    retval.add(new Successor("SwapCentral(" + central1Key + "," + central2Key + ")", statusAux));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        }
        */



        return retval;
    }
}
