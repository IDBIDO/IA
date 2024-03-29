import IA.Electricity.*;
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class AIMAMain {
    public static void main(String[] args) throws Exception{
       // ArrayList<Double> k = new ArrayList<>([1, 5, 10, 25 , 125]);

        long start = System.nanoTime();

        int seed=1234;
        if(args.length == 1){
            seed = Integer.parseInt(args[0]);
        }
        Status status = new Status(seed);
        double beneficioInicial = status.beneficioPorCentral();

        // Create the Problem object
        Problem p = new  Problem(status,
                new ElectricitySuccesorFunction(),
                new ElectricityGoalTest(),
                new ElectricityHeuristicFunction());

        // Instantiate the search algorithm
        // AStarSearch(new GraphSearch()) or IterativeDeepeningAStarSearch()

        Search algHC = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(p, algHC);


/*
        // Instantiate the SearchAgent object SA
        Problem pSA = new  Problem(status,
                new ElectricitySuccesorFunctionSA(),
                new ElectricityGoalTest(),
                new ElectricityHeuristicFunction());

        //steps stiter k lamda
        //Search algSA = new SimulatedAnnealingSearch();
        Search algSA = new SimulatedAnnealingSearch(9000, 500, 1, 0.01);
        SearchAgent agent = new SearchAgent(pSA, algSA);
*/

        long end = System.nanoTime();

        System.out.println("Location: " + (end - start)/1000000);
        // We print the results of the search
        //printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // You can access also to the goal state using the
        // method getGoalState of class Search

        Status finalStatus = (Status)algHC.getGoalState();
        finalStatus.printState();
        //finalStatus.printState2();

        System.out.println("Beneficio inicial: "+String.valueOf(beneficioInicial));
        System.out.println("Beneficio final: "+String.valueOf(finalStatus.beneficioPorCentral()));
        System.out.println("Coste total:"+String.valueOf(finalStatus.costeTotal()));
        System.out.println("Bruto total:"+String.valueOf(finalStatus.brutoTotal()));
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }

}

