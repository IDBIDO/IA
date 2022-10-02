package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17
 */
public class ElectricitySuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state)  {
        ArrayList retval = new ArrayList();
        Status status = (Status) state;

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


        return retval;
    }

    public List getSuccessors_(Object state){
        ArrayList retval = new ArrayList();
        TemporalSoItWorks status = (TemporalSoItWorks) state;
        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j", new_state)
        // new_state has to be a copy of state

        for(int i=0;i<status.obtainBoard().length-1;++i){
            System.out.println(i);
            TemporalSoItWorks probIA5Board = new TemporalSoItWorks(status.obtainBoard(),status.obtainGoal());
            probIA5Board.flip_it(i);
            Successor successor = new Successor("flip "+Integer.toString(i)+" "+Integer.toString(i+1),probIA5Board);
            retval.add(successor);
        }
        return retval;
    }

}
