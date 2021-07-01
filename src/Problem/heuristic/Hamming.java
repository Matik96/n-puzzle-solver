package Problem.heuristic;

import Problem.state.State;

public class Hamming implements IHeuristic {

    @Override
    public int getHeuristicValue(State initialState, State goalState) {

        int rowsAndColumns = State.getRowsAndColumns();
        int[][] initialBoard = initialState.getBoard();
        int[][] goalBoard = goalState.getBoard();

        int diff = 0;
        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                if (initialBoard[i][j] == 0) continue;
                if (initialBoard[i][j] != goalBoard[i][j])
                    diff++;
            }
        return diff;
    }


}
