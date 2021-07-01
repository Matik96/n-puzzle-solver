package Problem.heuristic;

import Problem.state.State;

import java.awt.*;

public class Manhatten implements IHeuristic {


    @Override
    public int getHeuristicValue(State initialState, State goalState) {

        int rowsAndColums = State.getRowsAndColumns();
        int[][] initialBoard = initialState.getBoard();
        int[][] goalBoard = goalState.getBoard();


        // set points to get difference from distance of puzzles
        Point[] goalPositions = new Point[1000];
        for (int i = 0; i < rowsAndColums; i++)
            for (int j = 0; j < rowsAndColums; j++) {
                if (initialBoard[i][j] >= 0)
                    goalPositions[initialBoard[i][j]] = findInGoalState(initialBoard[i][j], goalBoard, rowsAndColums, rowsAndColums);
            }


        // counting the actual distance
        int diff = 0;
        for (int i = 0; i < rowsAndColums; i++)
            for (int j = 0; j < rowsAndColums; j++) {
                if (initialBoard[i][j] == 0) continue;
                else {
                    Point point = goalPositions[initialBoard[i][j]];
                    diff += Math.abs(i - point.getX()) + Math.abs(j - point.getY());
                }

            }
        return diff;
    }

    private Point findInGoalState(int element, int[][] goalBoard, int rows, int columns) {
        Point point = null;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (element == goalBoard[i][j])
                    point = new Point(i, j);
        return point;
    }
}