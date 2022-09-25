package IA.Electricity;

public class Status {
    Centrales centrales;
    Clientes clientes;
    public Status() throws Exception {
        int seed = 10;

        centrales= new Centrales(new int[]{5, 5, 5},seed);
        clientes = new Clientes(30,new double[]{0.4,0.4,0.2},0.4,seed);

        centrales.print();
        clientes.print();
    }

    //Funcion que calcule el beneficio
    double beneficio(){return 0;}

    //Asigna Centrales a clientes y clientes a centrales en una configuración valida.
    void initialSolution1(){}

    //Asigna Centrales a clientes y clientes a centrales en una configuración valida.
    void initialSolution2(){}

    //OPERADORES
    //void swap(Central central1, Central central2){}
    //void mueveCliente(Cliente cliente, Central centralDestino){}
    //void mueveBulk(Clientes clientes, Central centralDestino){}

}
