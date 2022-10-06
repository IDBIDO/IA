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

        Status status = new Status(20);
        status.printState();
        double beneficioInicial = status.beneficioPorCentral();

        // Create the Problem object
        Problem p = new  Problem(status,
                new ElectricitySuccesorFunction(),
                new ElectricityGoalTest(),
                new ElectricityHeuristicFunction());

        // Instantiate the search algorithm
        // AStarSearch(new GraphSearch()) or IterativeDeepeningAStarSearch()
        Search alg = new HillClimbingSearch();
        // Instantiate the SearchAgent object
        SearchAgent agent = new SearchAgent(p, alg);

        // We print the results of the search
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // You can access also to the goal state using the
        // method getGoalState of class Search

        Status finalStatus = (Status)alg.getGoalState();
        finalStatus.printState();
        System.out.println("Beneficio inicial: "+String.valueOf(beneficioInicial));
        System.out.println("Beneficio final: "+String.valueOf(finalStatus.beneficioPorCentral()));
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

