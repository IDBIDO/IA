package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ElectricityHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        Status status = (Status) n;

        int option = 1;
        // puesto para ir probando las heurísticas quitar junto con el switch cuando ya tengamos la buena

        switch (option) {
            //opcion 1 (buscar el máximo beneficio)
            case 1:
                double beneficio;
                try {
                    beneficio = ((Status) n).beneficioPorCentral();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            //opcion 2 (buscando minimizar el coste)

            //opcion 3 (minimizar la energia "inutil" maximizando beneficios)
            case 3:
        }
        return ((TemporalSoItWorks) n).heuristic();
    }
}
