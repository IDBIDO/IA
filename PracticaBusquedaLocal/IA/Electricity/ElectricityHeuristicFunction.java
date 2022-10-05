package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ElectricityHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        //opcion 1 (buscar el máximo beneficio)

        //opcion 2 (buscando minimizar el coste)

        //opcion 3 (minimizar la energia "inutil" maximizando benefeicios)

        return ((TemporalSoItWorks) n).heuristic();
    }
}
