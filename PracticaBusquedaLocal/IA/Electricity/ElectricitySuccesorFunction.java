package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        int i=0;
        for(Integer relacion: relaciones.getClientes()){
            if(relacion!=-1) {
                Central centralRelacion = centrales.get(relacion);
                //Por cada cliente de la central que hay en cada relación
                Cliente cliente = clientes.get(i);
                //Añadimos el cliente a todas las otras centrales
                for (Map.Entry<Integer, Central> centralIter : centrales.entrySet()) {
                    if (relacion!=centralIter.getKey() && status.canServe(cliente, centralIter.getValue())) {
                        Status statusAux = new Status(status);
                        statusAux.asignarCliente(cliente, centralIter.getValue());
                        statusAux.quitarCliente(cliente, centralRelacion);
                        retval.add(new Successor("AsignarCliente(" + String.valueOf(i) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                    }
                }
                served.add(relacion);
            }
            ++i;
        }
        i=0;

        for(Integer relacion: relaciones.getClientes()){
            if (!served.contains(relacion)) {
                //Antes no hemos tratado a este cliente porque no estaba en ninguna central
                //Le asignamos a cualquier posible central
                Cliente cliente = clientes.get(i);
                for (Map.Entry<Integer, Central> centralIter : centrales.entrySet()) {
                    if (status.canServe(cliente, centralIter.getValue())) {
                        Status statusAux = new Status(status);
                        statusAux.asignarCliente(cliente, centralIter.getValue());
                        retval.add(new Successor("AsignarCliente(" + String.valueOf(i) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                    }
                }
            }
            ++i;
        }
        return retval;
    }

    private List getSuccessorsSecondExperiment(Object state) throws Exception {
        List retval =getSuccessorsFirstExperiment(state);
        Status status = (Status)state;
        Relaciones relaciones= status.getRelaciones();
        Clientes clientesaux= status.getClientes();
        Centrales centralesaux = status.getCentrales();

        ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
        ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());

        ArrayList<Integer>centralesClientes = relaciones.getClientes();
        retval = Collections.synchronizedList(retval);
        List finalRetval = retval;
        IntStream.range(0, centralesClientes.size()).parallel().forEach(i -> {
            if (centralesClientes.get(i) != -1) {
                Cliente cliente1 = clientes.get(i);
                for (int j = i + 1; j < centralesClientes.size(); ++j) {
                    if (centralesClientes.get(j) != -1) {
                        Cliente cliente2 = clientes.get(j);
                        Central central1 = centrales.get(centralesClientes.get(i));
                        Central central2 = centrales.get(centralesClientes.get(j));
                        if (status.canChange(cliente1, cliente2, central1) && status.canChange(cliente2, cliente1, central2)) {
                            if (status.makesSenseChange(cliente1,cliente2,central1,central2)) {
                                Status statusAux = new Status(status);
                                try {
                                    statusAux.swapCliente(cliente1, central1, cliente2, central2);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                finalRetval.add(new Successor("MoverCliente(" + i + "," + j + ")", statusAux));
                            }
                        }
                    }
                }
            }
        });
        System.out.println(retval.size());
        return finalRetval;
    }
/*
    private Successor getSuccessor(Status status, Relaciones relaciones, ArrayList<Cliente> clientes, Central central1, Central central2, Cliente cliente1, int finalI, Integer cliente2id) {
        Cliente cliente2 = clientes.get(cliente2id);
        Status statusAux = new Status(status);
        if(status.canChange(cliente1,cliente2, relaciones.getRelaciones().get(finalI +1)) && status.canChange(cliente2, cliente1, relaciones.getRelaciones().get(finalI))) {
            if(status.makesSenseChange(cliente1,cliente2, relaciones.getRelaciones().get(finalI +1), relaciones.getRelaciones().get(finalI))) {
                try {
                    statusAux.swapCliente(cliente1, central1, cliente2, central2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Successor successor = new Successor("MoverCliente(", statusAux);
                return successor;
            }
        }
        return null;
    }
 */

    public List getSuccessors(Object state){
        try {
            //Runtime runtime = Runtime.getRuntime();

            //System.out.println ("Memoria máxima: " + runtime.maxMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria total: " + runtime.totalMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria libre: " + runtime.freeMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria usada: " + (runtime.totalMemory() - runtime.freeMemory()) / (1024*1024) + "MB");
            return getSuccessorsSecondExperiment(state);
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
