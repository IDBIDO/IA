package IA.Electricity;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17
 */
public class ElectricitySuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        TemporalSoItWorks status = (TemporalSoItWorks) state;


        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j", new_state)
        // new_state has to be a copy of state

        for(int i=0;i<status.obtainBoard().length-1;++i){
            TemporalSoItWorks probIA5Board = new TemporalSoItWorks(status.obtainBoard(),status.obtainGoal());
            probIA5Board.flip_it(i);
            Successor successor = new Successor("flip "+Integer.toString(i)+" "+Integer.toString(i+1),probIA5Board);
            retval.add(successor);
        }


        return retval;

    }

}
