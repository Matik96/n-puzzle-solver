package Method.algorithm;

import java.util.List;

import Problem.state.State;

public abstract class Algorithm {
    protected State initialState;
    protected State goalState;

    protected int nodeExplored;
    protected int numOfSteps;

    protected long timeSpent;
    protected String algorithmName;

    public Algorithm(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
    }

    public abstract ReturnType solve();

    public int getNodeExplored() {
        return nodeExplored;
    }

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void logStates(State current, List<State> states) {
        int size = states.size();
        if(current.getBoard().length < 5){
        if (size > 0) {
            for (int i = 0; i < current.getBoard().length; i++) {
                for (int k = 0; k < size; k++) System.out.print("  ");
                System.out.print("| ");
                for (int j = 0; j < current.getBoard().length; j++) {
                    System.out.print(current.getBoard()[i][j]);
                }
                System.out.print(" |");
                System.out.println();
            }
            for (int i = 0; i < states.get(0).getBoard().length; i++) {
                for (int k = 0; k < size; k++) {
                    System.out.print(" | ");
                    for (int j = 0; j < states.get(k).getBoard().length; j++) {
                        System.out.print(states.get(k).getBoard()[i][j]);
                    }

                }
                System.out.print(" | ");
                System.out.println("");
                // System.out.println("val: " + states.get(k).getHeuristicValue());

            }
            System.out.print(" ");
            for (int i = 0; i < states.size(); i++)
                for (int j = 0; j < states.get(i).getBoard().length; j++) System.out.print("--");
            System.out.print("--- ");
            System.out.println("");
        }
    }
    }
}

