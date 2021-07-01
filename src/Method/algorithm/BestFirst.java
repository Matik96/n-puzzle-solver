package Method.algorithm;

import Problem.state.State;
import Problem.state.StateComparator;
import Problem.state.StateGenerator;

import java.util.*;

public class BestFirst extends Algorithm {

    String name = "BestFirst";
    ReturnType returnType;

    public BestFirst(State initialState, State goalState) {
        super(initialState, goalState);
    }

    @Override
    public ReturnType solve() {

        // I used here a priority queue because its prioritize adding sates into queue
        //its done by created StateComparator
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
                returnType.setToReturn(toReturn);
                returnType.setFoundResult(true);
                return returnType;
            }
            List<State> nextStates = generator.generateStates(current);
            List<State> withoutClosed = removeClosed(nextStates, closed);
            logStates(current, withoutClosed);
            for (State state : withoutClosed) {
                state.setHeuristicValue(goalState);
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

    private List<State> removeClosed(List<State> nextStates, Set<State> closed) {
        List<State> newList = new ArrayList<State>();
        for (State state : nextStates) {
            if (!closed.contains(state))
                newList.add(state);
        }
        return newList;
    }


}
