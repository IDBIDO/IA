package IA.Electricity;

/**
 * Created by bejar on 17/01/17.
 */
public class Central {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
     */

    private int [] board;
    private static int [] solution;

    /* Constructor */
    public Central(int []init, int[] goal) {

        board = new int[init.length];
        solution = new int[init.length];

        for (int i = 0; i< init.length; i++) {
            board[i] = init[i];
            solution[i] = goal[i];
        }

    }

    /* vvvvv TO COMPLETE vvvvv */
    public void flip_it(int i){
        board[i]=1-board[i];
        board[i+1]=1-board[i+1];
    }

    /* Heuristic function */
    public double heuristic(){
        int out=0;
        for(int i=0;i<board.length;++i){
            if(board[i]!=solution[i])++out;
        }
        return out;
    }

    /* Goal test */
    public boolean is_goal(){
        for(int i=0;i<board.length;++i){
            if(board[i]!=solution[i])return false;
        }
        return true;
    }

    /* auxiliary functions */

    Central copy(){
        int[] ret=new int[board.length];
        for(int i = 0;i<board.length;++i)
            ret[i]=board[i];

        return new Central(ret,solution);
    }
    int[] obtainBoard(){
        return board;
    }
    int[] obtainGoal(){
        return solution;
    }

}

/* ^^^^^ TO COMPLETE ^^^^^ */


