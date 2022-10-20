import IA.Electricity.*;
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class AIMAMain {
    public static void main(String[] args) throws Exception{
        int seed=1234;

        long start = System.nanoTime();
        if(args.length == 1){
            seed = Integer.parseInt(args[0]);
        }
        Status status = new Status(seed);
        //status.printState();
        double beneficioInicial = status.beneficioPorCentral();


        ElectricitySuccesorFunction succesorFunction = new ElectricitySuccesorFunction();
        // Create the Problem object
        Problem p = new  Problem(status,
                succesorFunction,
                new ElectricityGoalTest(),
                new ElectricityHeuristicFunction());

        // Instantiate the search algorithm
        // AStarSearch(new GraphSearch()) or IterativeDeepeningAStarSearch()

        Search alg = new HillClimbingSearch();
        SearchAgent agent = new SearchAgent(p, alg);



        // Instantiate the SearchAgent object SA
        //Problem pSA = new  Problem(status,
        //        new ElectricitySuccesorFunctionSA(),
        //        new ElectricityGoalTest(),
        //        new ElectricityHeuristicFunction());

        //steps stiter k lamda
        //Search algSA = new SimulatedAnnealingSearch();
        //Search algSA = new SimulatedAnnealingSearch(200000, 500, 100, 0.001);
        //SearchAgent agent = new SearchAgent(pSA, algSA);

        long end = System.nanoTime();

        System.out.println("Location: " + (end - start)/1000000);
        // We print the results of the search
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // You can access also to the goal state using the
        // method getGoalState of class Search

        Status finalStatus = (Status)alg.getGoalState();
        //finalStatus.printState();
        //finalStatus.printState2();

        System.out.println("Numero Sucesores generados: "+String.valueOf(succesorFunction.getNumberSuccessors()));
        System.out.println("Beneficio inicial: "+String.valueOf(status.beneficioPorCentral()));
        System.out.println("Beneficio final: "+String.valueOf(finalStatus.beneficioPorCentral()));
        System.out.println("Coste total:"+String.valueOf(finalStatus.costeTotal()));
        System.out.println("Bruto total:"+String.valueOf(finalStatus.brutoTotal()));
        System.out.println("Generación solucion inicial: "+finalStatus.getGeneracion());
        System.out.println("Centrales en uso: "+(finalStatus.getCentrales().size()-finalStatus.centralesApagadas())+"/"+finalStatus.getCentrales().size());
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

