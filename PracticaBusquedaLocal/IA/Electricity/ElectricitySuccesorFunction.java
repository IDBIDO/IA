package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.abs;

/**
 * Created by bejar on 17/01/17
 */
public class ElectricitySuccesorFunction implements SuccessorFunction{
    private Integer successors;
    public ElectricitySuccesorFunction(){
        this.successors = 0;
    }
    public double getNumberSuccessors(){
        return successors;
    }

    public List getSuccessorsFirstExperiment(Object state) throws Exception {
        List retval = new ArrayList();
        Status status = (Status) state;
        Relaciones relaciones= status.getRelaciones();
        Clientes clientes= status.getClientes();
        Centrales centrales = status.getCentrales();
        retval = Collections.synchronizedList(retval);
        List finalRetval = retval;
        IntStream.range(0, relaciones.getClientes().size()).parallel().forEach(relacion -> {
            if(relaciones.getClientes().get(relacion)!=-1) {
                Central centralRelacion = centrales.get(relaciones.getClientes().get(relacion));
                //Por cada cliente de la central que hay en cada relación
                Cliente cliente = clientes.get(relacion);
                //Añadimos el cliente a todas las otras centrales
                for (Map.Entry<Integer, Central> centralIter : centrales.entrySet()) {
                    if (relaciones.getClientes().get(relacion)!=centralIter.getKey() && status.canServe(cliente, centralIter.getValue())) {
                        if(status.isPossible(cliente,centralIter.getValue(),centrales.get(relaciones.getClientes().get(relacion)))) {
                            Status statusAux = new Status(status);
                            try {
                                statusAux.quitarCliente(cliente, centralRelacion);
                                statusAux.asignarCliente(cliente, centralIter.getValue());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            finalRetval.add(new Successor("AsignarCliente(" + String.valueOf(relacion) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                        }
                    }
                }
                if(!cliente.isGuaranteed()){//change to if(true) for test 5
                    Status statusAux = new Status(status);
                    try {
                        statusAux.quitarCliente(cliente, centralRelacion);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    finalRetval.add(new Successor("QuitarCliente(" + String.valueOf(relacion)+")", statusAux));
                }
            }
        });

        List finalRetval1 = finalRetval;
        IntStream.range(0, relaciones.getClientes().size()).parallel().forEach(relacion -> {
            if (relaciones.getClientes().get(relacion)==-1) {
                //Antes no hemos tratado a este cliente porque no estaba en ninguna central
                //Le asignamos a cualquier posible central
                Cliente cliente = clientes.get(relacion);
                for (Map.Entry<Integer, Central> centralIter : centrales.entrySet()) {
                    if (status.canServe(cliente, centralIter.getValue())) {
                        Status statusAux = new Status(status);
                        try {
                            statusAux.asignarCliente(cliente, centralIter.getValue());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        finalRetval1.add(new Successor("AsignarCliente(" + String.valueOf(relacion) + "," + String.valueOf(centralIter.getKey()) + ")", statusAux));
                    }
                }
            }
        });
        return finalRetval1;
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
                Central central1 = centrales.get(centralesClientes.get(i));
                for (int j = i + 1; j < centralesClientes.size(); ++j) {
                    if (centralesClientes.get(j) != -1) {
                        Cliente cliente2 = clientes.get(j);
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
        return finalRetval;
    }

    public List getSuccessorsThirdExperiment(Object state) throws Exception {
        List retval = getSuccessorsFirstExperiment(state);
        Status status = (Status) state;
        Relaciones relaciones = status.getRelaciones();
        Clientes clientesaux = status.getClientes();
        Centrales centralesaux = status.getCentrales();

        ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
        ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());

        Map<Integer,ArrayList<Integer>> centralesClientes= new HashMap<Integer,ArrayList<Integer>>();
        for(int i=0;i<centrales.size();++i){
            centralesClientes.put(centrales.get(i).getId(),new ArrayList<Integer>());
        }
        for(int i=0;i<relaciones.getClientes().size();++i){
            if(relaciones.getClientes().get(i)!=-1){
                ArrayList<Integer> lista = centralesClientes.get(relaciones.getClientes().get(i));
                lista.add(i);
                centralesClientes.put(relaciones.getClientes().get(i),lista);
            }
        }
        for(int i=0;i<centrales.size();++i){
            for(int j=i+1;j<centrales.size();++j){
                if(centrales.get(i).getTipo()==centrales.get(j).getTipo()){
                    if(status.canSwapCentral(centrales.get(i),centrales.get(j),centralesClientes.get(i),centralesClientes.get(j))){
                        Status statusAux = new Status(status);
                        swapcentrales(clientes, centrales, centralesClientes, i, j, statusAux);
                        retval.add(new Successor("SwapCentral(" + i + "," + j + ")", statusAux));
                    }
                }
            }
        }
        return retval;
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

    public List getSuccessorsFourthExperiment(Object state) throws Exception {
        List retval = getSuccessorsThirdExperiment(state);

        Status status = (Status) state;
        Relaciones relaciones = status.getRelaciones();
        Clientes clientesaux = status.getClientes();
        Centrales centralesaux = status.getCentrales();

        ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
        ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());

        Map<Integer,ArrayList<Integer>> centralesClientes= new HashMap<Integer,ArrayList<Integer>>();
        for(int i=0;i<centrales.size();++i){
            centralesClientes.put(centrales.get(i).getId(),new ArrayList<Integer>());
        }
        for(int i=0;i<relaciones.getClientes().size();++i){
            if(relaciones.getClientes().get(i)!=-1){
                ArrayList<Integer> lista = centralesClientes.get(relaciones.getClientes().get(i));
                lista.add(i);
                centralesClientes.put(relaciones.getClientes().get(i),lista);
            }
        }

        retval = Collections.synchronizedList(retval);
        List finalRetval = retval;
        IntStream.range(0, centralesClientes.size()).parallel().forEach(i -> {
            for(int j=i+1;j<centrales.size();++j){
                if(status.canShutDownCentral(centrales.get(i),centrales.get(j),centralesClientes.get(i),centralesClientes.get(j))){
                    Status statusAux = new Status(status);
                    for(int p = 0; p< centralesClientes.get(i).size(); ++p){
                        try {
                            statusAux.quitarCliente(clientes.get(centralesClientes.get(i).get(p)), centrales.get(i));
                            if(clientes.get(centralesClientes.get(i).get(p)).isGuaranteed()) {
                                statusAux.asignarCliente(clientes.get(centralesClientes.get(i).get(p)), centrales.get(j));
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    finalRetval.add(new Successor("ApagarCentral(" + i + "," + j + ")", statusAux));
                }
            }
        });
        return finalRetval;
    }

    public List getSuccessorsFifthExperiment(Object state) throws Exception{
        List retval = getSuccessorsFirstExperiment(state);
        retval.addAll(getSuccessorsSecondExperiment(state));
        retval.addAll(getSuccessorsThirdExperiment(state));
        return retval;
    }

    public List getSuccessors(Object state){
        try {
            Runtime runtime = Runtime.getRuntime();

            //System.out.println ("Memoria máxima: " + runtime.maxMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria total: " + runtime.totalMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria libre: " + runtime.freeMemory() / (1024*1024) + "MB");
            //System.out.println ("Memoria usada: " + (runtime.totalMemory() - runtime.freeMemory()) / (1024*1024) + "MB");
            List successorsList = getSuccessorsThirdExperiment(state);
            successors=successors+successorsList.size();
            return successorsList;
        }
        catch (Exception e){
            System.out.println("Excepcion: "+e.toString());
            return null;
        }
    }
}
