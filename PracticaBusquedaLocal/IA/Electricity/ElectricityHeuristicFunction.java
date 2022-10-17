package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ElectricityHeuristicFunction implements HeuristicFunction {
    public double getHeuristicValue(Object n){
        try {
            return ((Status) n).heuristic4()*-1;
        }
        catch (Exception e){
            return 0;
        }
    }
}
