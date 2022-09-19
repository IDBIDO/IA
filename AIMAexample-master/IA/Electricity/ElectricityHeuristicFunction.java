package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ElectricityHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((Central) n).heuristic();
    }
}
