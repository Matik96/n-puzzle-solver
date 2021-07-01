package Problem.state;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StateGenerator {


    //default goal state maker
    public static State makeGoalState() {
        int rowsAndColums = State.getRowsAndColumns();

        int num = 1;
        int[][] goalBoard = new int[rowsAndColums][rowsAndColums];
        for (int i = 0; i < rowsAndColums; i++)
            for (int j = 0; j < rowsAndColums; j++) {
                if (i == rowsAndColums - 1 && j == rowsAndColums - 1)
                    goalBoard[i][j] = 0;
                else
                    goalBoard[i][j] = num++;
            }
        return new State(goalBoard, null);
    }

    //Generator of states - if check every possibility of move of the blank piece and then move and generate new states
    public List<State> generateStates(State currentState) {
        Point emptyField = currentState.getEmptyField();
        State parent = currentState;
        int rowsAndColums = State.getRowsAndColumns();

        List<State> nextStates = new ArrayList<>();
        if (this.checkRight(emptyField, rowsAndColums))
            nextStates.add(this.moveRight(rowsAndColums, rowsAndColums, emptyField, parent));
        if (this.checkDown(emptyField, rowsAndColums))
            nextStates.add(this.moveDown(rowsAndColums, rowsAndColums, emptyField, parent));
        if (this.checkUp(emptyField))
            nextStates.add(this.moveUp(rowsAndColums, rowsAndColums, emptyField, parent));
        if (this.checkLeft(emptyField, rowsAndColums))
            nextStates.add(this.moveLeft(rowsAndColums, rowsAndColums, emptyField, parent));

        return nextStates;
    }

    private boolean checkUp(Point emptyField) {
        int pos = emptyField.y - 1;
        if (pos < 0)
            return false;
        return true;
    }

    private boolean checkDown(Point emptyField, int rows) {
        int pos = emptyField.y + 1;
        if (pos >= rows)
            return false;
        return true;
    }

    private boolean checkLeft(Point emptyField, int columns) {
        if (emptyField.x % columns == 0)
            return false;
        return true;
    }

    private boolean checkRight(Point emptyField, int columns) {
        if (emptyField.x % columns == columns - 1)
            return false;
        return true;
    }

    private State moveUp(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y - 1][emptyField.x];
        newBoard[emptyField.y - 1][emptyField.x] = temp;

        return new State(newBoard, parent);
    }

    private State moveDown(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y + 1][emptyField.x];
        newBoard[emptyField.y + 1][emptyField.x] = temp;

        return new State(newBoard, parent);
    }

    private State moveLeft(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y][emptyField.x - 1];
        newBoard[emptyField.y][emptyField.x - 1] = temp;

        return new State(newBoard, parent);
    }

    private State moveRight(int rows, int columns, Point emptyField, State parent) {
        int[][] newBoard = new int[rows][columns];
        copyBoard(newBoard, rows, columns, parent);

        int temp = newBoard[emptyField.y][emptyField.x];
        newBoard[emptyField.y][emptyField.x] = newBoard[emptyField.y][emptyField.x + 1];
        newBoard[emptyField.y][emptyField.x + 1] = temp;

        return new State(newBoard, parent);
    }

    private void copyBoard(int[][] newBoard, int rows, int columns, State parent) {
        int[][] board = parent.getBoard();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                newBoard[i][j] = board[i][j];
    }
}
