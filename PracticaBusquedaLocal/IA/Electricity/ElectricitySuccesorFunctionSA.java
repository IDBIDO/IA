package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class ElectricitySuccesorFunctionSA implements SuccessorFunction {
    private static Random random;

    public ElectricitySuccesorFunctionSA() {
        random = new Random();
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

        int clientSize = clientes.size();
        int noguaranteedSize;
        int ran = random.nextInt(2);    //0 -> swap; 1-> asignar cliente


        if (ran == 4) {

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

        else if (ran < 9) {
            int uidCliente = random.nextInt(centralesClientes.size());      //coger un cliente random
            Cliente cliente = clientes.get(uidCliente);

            int centralKey = random.nextInt(centrales.size());      //coger una central random
            if (centralesClientes.get(uidCliente) == -1) {     //no central asignado, asignar cliente
                System.out.println(1);
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




        return retval;
    }
}
