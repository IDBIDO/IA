package IA.Electricity;

import IA.Electricity.VEnergia;

import java.io.PrintStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestEnergia {

    public static void main(String[] var0) throws Exception {
        Status status= new Status(5);
        Clientes clientes = status.getClientes();
        Centrales centrales = status.getCentrales();
        Relaciones relacion = status.getRelaciones();

        System.out.println(clientes.size());
        System.out.println(centrales.size());
        System.out.println(relacion.relaciones.size());     //representa asignacion cliente -> central
        System.out.println(relacion.mwUsados.size());       //representa energia usado en cada central

        System.out.println(relacion.relaciones);
        //clientes.print();

        Random random = new Random();
        int ran = random.nextInt(3);
        for (int i = 0; i < 10; ++i) {
            ran = random.nextInt(3);
            System.out.println(ran);
        }

        /*
        Status statusaux1 = new Status(status);
        Status statusaux2 = new Status(status);
        Status statusaux3 = new Status(status);


        statusaux2.asignarCliente(clientes.get(4),centrales.get(2));
        statusaux2.quitarCliente(clientes.get(4),centrales.get(2));

        statusaux2.printState();
        */
    }
}
