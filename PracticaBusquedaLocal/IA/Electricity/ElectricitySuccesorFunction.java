package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.*;

/**
 * Created by bejar on 17/01/17
 */
public class ElectricitySuccesorFunction implements SuccessorFunction{

    public List getSuccessorsFirstExperiment(Object state) throws Exception {
        ArrayList retval = new ArrayList();
        Status status = (Status) state;
        Relaciones relaciones= status.getRelaciones();
        Clientes clientes= status.getClientes();
        Centrales centrales = status.getCentrales();

        Set<Integer> served = new HashSet<Integer>();
        for (Map.Entry<Integer,Relacion> entry : relaciones.entrySet()){
            Relacion relacion = entry.getValue();
            Central centralRelacion = centrales.get(entry.getKey());

            //Por cada cliente de la central que hay en cada relación
            for(Integer clienteId: relacion.getClientes()){
                Cliente cliente = clientes.get(clienteId);
                //Quitamos el cliente si es que es no garantizado
                if (!cliente.isGuaranteed()) {
                    Status statusAux = new Status(status);
                    statusAux.quitarCliente(cliente,centralRelacion);
                    retval.add(new Successor("QuitarCliente("+String.valueOf(clienteId)+","+String.valueOf(entry.getKey())+")",statusAux));
                }
                //Añadimos al cliente a todas las otras centrales
                for(Map.Entry<Integer,Central> centralIter: centrales.entrySet()){
                    if(centralIter.getKey()!=entry.getKey() && status.canServe(cliente,centralIter.getValue())) {
                        Status statusAux = new Status(status);
                        statusAux.asignarCliente(cliente, centralIter.getValue());
                        statusAux.quitarCliente(cliente,centralRelacion);
                        retval.add(new Successor("AsignarCliente(" + String.valueOf(clienteId) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                    }
                }
                served.add(clienteId);
            }
        }
        for(Map.Entry<Integer,Cliente> clienteIter: clientes.entrySet()){
            if(!served.contains(clienteIter.getKey())){
                //Antes no hemos tratado a este cliente porque no estaba en ninguna central
                //Le asignamos a cualquier posible central
                Cliente cliente = clienteIter.getValue();
                for(Map.Entry<Integer,Central> centralIter: centrales.entrySet()){
                    if(status.canServe(cliente,centralIter.getValue())) {
                        Status statusAux = new Status(status);
                        statusAux.asignarCliente(cliente, centralIter.getValue());
                        retval.add(new Successor("AsignarCliente(" + String.valueOf(clienteIter.getKey()) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                    }
                }
            }
        }
        return retval;
    }


    public List getSuccessors(Object state){
        try {
            return getSuccessorsFirstExperiment(state);
        }
        catch (Exception e){
            System.out.println("Excepcion: "+e.toString());
            return null;
        }
        /*
        //todos los swaps de clientes posibles
        for (int i = 0; i < status.clientes.size(); ++i) {
            Cliente cliente1 = status.clientes.get(i);
            if (cliente1.estaServido()) {       //cojemos los que estan servidos
                for (int j = i + 1; j < status.clientes.size(); ++j) {
                    Cliente cliente2 = status.clientes.get(j);
                    if(cliente2.estaServido()) {         //cojemos los que estan servidos
                        if (status.canSwap(cliente1, cliente2)) {   //comprobamos que las dos centrales tiene suficiente recurso si hacemos swap
                            Status statusAux = new Status(status);
                            statusAux.swapCliente(cliente1, cliente2);
                            retval.add(new Successor("Swap: cliente "+ String.valueOf(i) + "<->" + "cliente " + String.valueOf(j) + "\n",
                                    statusAux));
                        }

                    }
                }
            }
        }

         */

        /*
        //assignar clientes no servidos
        for (int i = 0; i < status.clientes.size(); ++i) {
            Cliente cliente1 = status.clientes.get(i);
            if (!cliente1.estaServido()) {      //filtramos los no servidos
                for (int j = 0; j < status.centrales.size(); ++j) {
                    Central central = status.centrales.get(j);
                    if (status.canServe(central, cliente1)) {       //comprobamos que la central tiene suficiente recurso
                        Status statusAux = new Status(status);
                        statusAux.asignarCliente(cliente1,central);
                        retval.add(new Successor("Asignar cliente no servido"+ String.valueOf(i) + "->" + "central " + String.valueOf(j) + "\n",
                                statusAux));

                    }
                }
            }
        }

        //mover clientes
        for (int i = 0; i < status.clientes.size(); ++i) {
            Cliente cliente = status.clientes.get(i);
            if (cliente.estaServido()) {        //si no esta servido lo dejamos
                for (int j = 0; j < status.centrales.size(); ++j) {
                    Central central = status.centrales.get(j);
                    if (!status.equalCentral(cliente.getServer(), central)) {   //evitamos mover a la misma central
                        if (status.canServe(central, cliente)) {        //comprobamos que la central destino le queda suficiente recurso
                            Status statusAux = new Status(status);
                            status.mueveCliente(cliente, central);
                            retval.add(new Successor("Mover cliente servido" + String.valueOf(i) + "->" + "central " + String.valueOf(j) + "\n",
                                    statusAux));
                        }
                    }
                }
            }
        }

        //desasignar clientes
        for (int i = 0; i < status.clientes.size(); ++i) {
            Cliente cliente = status.clientes.get(i);
            if (!cliente.isGuaranteed()) {        //si esta servido lo dejamos, para no salir de la solucion
                if (cliente.estaServido()) {
                    Status statusAux = new Status(status);
                    statusAux.quitarCliente(cliente);
                    retval.add(new Successor("Desasignar cliente no guarantizado" + String.valueOf(i) + "\n",
                            statusAux));
                }
            }
        }

        */
        //return retval;
    }
}
