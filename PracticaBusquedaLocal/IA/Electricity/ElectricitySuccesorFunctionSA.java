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

        int ran = random.nextInt(2);    //0 -> swap; 1-> asignar cliente
        if (ran == 0) {     //swap
            Relaciones relaciones = status.getRelaciones();
            Clientes clientesaux = status.getClientes();
            Centrales centralesaux = status.getCentrales();
            ArrayList<Cliente> clientes = new ArrayList<Cliente>(clientesaux.getClientes().values());
            ArrayList<Central> centrales = new ArrayList<Central>(centralesaux.getCentrales().values());

            ArrayList<Integer>centralesClientes = relaciones.getClientes();

            //coger una asignacion cliente-> central valida
            int uidCliente1 = random.nextInt(centralesClientes.size());
            while(centralesClientes.get(uidCliente1) != -1) uidCliente1 = random.nextInt(centralesClientes.size());

            //coger otra asignacion cliente -> central que no sea la anteriol
            int uidCliente2 = random.nextInt(centralesClientes.size());
            while(centralesClientes.get(uidCliente2) != -1 && uidCliente2 != uidCliente1) uidCliente2 = random.nextInt(centralesClientes.size());

            Cliente cliente1 = clientes.get(uidCliente1);
            Cliente cliente2 = clientes.get(uidCliente2);
            Central central1 = centrales.get(centralesClientes.get(uidCliente1));
            Central central2 = centrales.get(centralesClientes.get(uidCliente2));



        }





        return retval;
    }
}
