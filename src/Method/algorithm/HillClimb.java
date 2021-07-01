package Method.algorithm;

import Problem.state.State;
import Problem.state.StateComparator;
import Problem.state.StateGenerator;

import java.util.*;

public class HillClimb extends Algorithm {

    public HillClimb(State initialState, State goalState) {
        super(initialState, goalState);
    }

    private String name = "HillClimb";
    private ReturnType returnType;

    @Override
    public ReturnType solve() {
        int nodeExplored = 0;
        Stack<State> toReturn = new Stack<>();
        PriorityQueue<State> open = new PriorityQueue<State>(10000, new StateComparator());
        Set<State> closed = new HashSet<State>();
        StateGenerator generator = new StateGenerator();
        returnType = new ReturnType();

        long startTime = System.currentTimeMillis();
        long stopTime;
        this.algorithmName = name;

        open.add(initialState);
        closed.add(initialState);

        while (!open.isEmpty()) {
            nodeExplored++;
            State current = open.poll();
            if (current.equals(goalState)) {
                this.numOfSteps = current.getDepth();
                this.nodeExplored = nodeExplored;
                toReturn = current.getPath();
                stopTime = System.currentTimeMillis();
                this.timeSpent = stopTime - startTime;
                returnType.setFoundResult(true);
                returnType.setToReturn(toReturn);
                return returnType;
            }
            List<State> nextStates = generator.generateStates(current);
            List<State> statesToConsider = comparationNodes(nextStates, closed);
            logStates(current, statesToConsider);
            for (State state : statesToConsider) {
                open.add(state);
                closed.add(state);

            }

        }
        returnType.setFoundResult(false);
        Object o;
        if (!closed.isEmpty()) {
            Iterator iter = closed.iterator();
            o = iter.next();
            State current = (State) o;
            toReturn = current.getPath();
            this.numOfSteps = current.getDepth();
            this.nodeExplored = nodeExplored;
            stopTime = System.currentTimeMillis();
            this.timeSpent = stopTime - startTime;
            returnType.setToReturn(toReturn);
        }
        return returnType;
    }

    private List<State> comparationNodes(List<State> nextStates, Set<State> closed) {

        List<State> bestStates = new ArrayList<State>();


        for (State state : nextStates) {
            if (!closed.contains(state)) {
                state.setHeuristicValue(goalState);
                if (bestStates.size() == 0) {
                    bestStates.add(state);
                } else {
                    if (bestStates.get(0).getHeuristicValue() > state.getHeuristicValue()) {
                        bestStates.clear();
                        bestStates.add(state);
                    } else if (bestStates.get(0).getHeuristicValue() == state.getHeuristicValue()) {
                        bestStates.add(state);
                    }
                }

            }
        }
        return bestStates;
    }

}
