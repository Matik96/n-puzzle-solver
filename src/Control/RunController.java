package Control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import Method.algorithm.*;
import Problem.heuristic.*;
import Problem.state.State;
import Control.view.MainView;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class RunController implements EventHandler<ActionEvent> {

    private boolean isThreadRunning = false;
    private Stack<State> path = new Stack<State>();
    private Timer timer;
    private FileWriter fileWriter = null;
    private String fileName = "LogFile.txt";
    private File logFile = null;
    private String NEWLINE = "\n";
    private String selectedHeuristic;
    private int maxNum;
    private int boardSize;
    private boolean isFound;
    private int speed = 10;


    @Override
    public void handle(ActionEvent arg0) {
        try {
            int[][] initialBoard = MainView.getInstance().getStartPuzzleField();
            int[][] goalBoard = MainView.getInstance().getGoalPuzzleField();

            State initialState = new State(initialBoard, null);
            State goalState = new State(goalBoard, null);
            maxNum = State.getRowsAndColumns() * State.getRowsAndColumns() - 1;
            boardSize = State.getRowsAndColumns();
            MainView.getInstance().setPreviousStartBoard(initialBoard);

            if (!validateBoard(initialState) || !validateBoard(goalState)) {
                throw new NumberFormatException();
            }

            State.setHeuristic(selectedHeuristic(initialState, goalState));
            Algorithm algorithm = selectedAlgorithm(initialState, goalState);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    //On first tick it will start solving
                    if (!isThreadRunning) {
                        ReturnType returnedType = algorithm.solve();
                        path = returnedType.getToReturn();
                        isFound = returnedType.isFoundResult();
                        isThreadRunning = true;
                    }

                    //When it solves it or not, update GUI
                    if (!path.isEmpty()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {


                                    MainView.getInstance().setPuzzleField(path.peek().getBoard());
                                    path.pop();

                                    // End of operations
                                    if (path.isEmpty()) {
                                        try {
                                            if (!isFound) {
                                                Alert alert = new Alert(AlertType.INFORMATION, "Algorithm has not found the path!", ButtonType.OK);
                                                alert.showAndWait();

                                            }
                                            // Passing the values into log file
                                            logResults(algorithm.getAlgorithmName(), getSelectedHeuristic(), algorithm.getNumOfSteps(), algorithm.getNodeExplored(), algorithm.getTimeSpent(), isFound);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                        isThreadRunning = false;
                                        timer.stop();
                                        MainView.getInstance().enableOrDisableCommands(true);
                                    }
                                } catch (EmptyStackException ex) {

                                }
                            }
                        });
                    }
                }
            };
            //speed of solving puzzles on board
            timer = new Timer(speed, listener);
            timer.start();
            MainView.getInstance().enableOrDisableCommands(false);
        } catch (NumberFormatException nfe) {
            new Alert(AlertType.INFORMATION, "Please insert only unique numbers from range 1-" + maxNum + ".", ButtonType.OK).show();
        }
    }


    public Algorithm selectedAlgorithm(State initialState, State goalState) {
        Algorithm alg = null;
        AlgorithmType algType = MainView.getInstance().getAlgorithmTypeComboBox();
        switch (algType) {
            case HillClimb:
                alg = new HillClimb(initialState, goalState);
                break;
            case BestFirst:
                alg = new BestFirst(initialState, goalState);
                break;
        }
        return alg;
    }

    private IHeuristic selectedHeuristic(State initialState, State goalState) {
        IHeuristic heur = null;
        HeuristicType heurType = MainView.getInstance().getHeuristicTypeComboBox();
        switch (heurType) {
            case Manhattan:
                heur = new Manhatten();
                selectedHeuristic = "Manhatten";
                break;
            case Hamming:
                heur = new Hamming();
                selectedHeuristic = "Hamming";
                break;
        }
        return heur;
    }

    private String getSelectedHeuristic() {
        return selectedHeuristic;
    }

    private void getResultsToFile(ArrayList<String> list) throws IOException {
        String pathOfProject = System.getProperty("user.dir");
        try {
            logFile = new File(pathOfProject.concat("\\").concat(fileName));
            fileWriter = new FileWriter(logFile, true);

            for (int i = 0; i < list.size(); i++) {
                fileWriter.write(list.get(i));
                System.out.print(list.get(i));
            }

        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    private void logResults(String algorithmName, String heuristicName, int numberOfSteps, int nodesExplored, long time, boolean isFound) throws IOException {
        ArrayList resultList = new ArrayList();

        resultList.add("Puzzle size : " + boardSize + " x " + boardSize + NEWLINE);
        resultList.add("Algorithm name: " + algorithmName + NEWLINE);
        resultList.add("Heuristic name: " + heuristicName + NEWLINE);
        resultList.add("Number of steps: " + numberOfSteps + NEWLINE);
        resultList.add("Nodes explored: " + nodesExplored + NEWLINE);
        resultList.add("Time spent: " + time + " (ms)" + NEWLINE);
        resultList.add("Found path: " + isFound + NEWLINE);
        resultList.add("--------------------------------- " + NEWLINE);

        getResultsToFile(resultList);

    }

    //validation of board's fields. It needs to be n*n - 1 number and it needs to be an int.
    private boolean validateBoard(State initialState) {
        int n = State.getRowsAndColumns();
        int maxFields = n * n - 1;
        int sumOfFields = maxFields * (maxFields + 1) / 2;
        int tempSum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tempSum += initialState.getBoard()[i][j];

        return sumOfFields == tempSum;
    }


}
