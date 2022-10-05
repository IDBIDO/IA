package IA.Electricity;

import IA.Electricity.VEnergia;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestEnergia {

    public static void main(String[] var0) throws Exception {
        Status status= new Status(5);
        Clientes clientes = status.getClientes();
        Centrales centrales = status.getCentrales();

        Status statusaux1 = new Status(status);
        Status statusaux2 = new Status(status);
        Status statusaux3 = new Status(status);

        System.out.println(status.getCentrales());
        statusaux2.asignarCliente(clientes.get(4),centrales.get(2));
        statusaux2.quitarCliente(clientes.get(4),centrales.get(2));

        //statusaux2.printState();

    }
}
