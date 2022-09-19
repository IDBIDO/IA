package IA.Electricity;

import aima.search.framework.GoalTest;

/**
 * Created by bejar on 17/01/17.
 */
public class ElectricityGoalTest implements GoalTest {

    public boolean isGoalState(Object state){

        return((Central) state).is_goal();
    }
}
