package Problem.heuristic;

import Problem.state.State;

public interface IHeuristic {
    int getHeuristicValue(State startState, State finishState);

}
