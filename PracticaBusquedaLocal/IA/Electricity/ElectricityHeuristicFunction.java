package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class ElectricityHeuristicFunction implements HeuristicFunction {
    public double getHeuristicValue(Object n){
        try {
            return ((Status) n).heuristic();
        }
        catch (Exception e){
            System.out.println("Exception heuristic Value: "+e.toString());
            return 0;
        }
    }
}
