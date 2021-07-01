package Control.view;

import Control.*;
import Problem.state.State;
import Problem.state.StateGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Method.algorithm.AlgorithmType;
import Problem.heuristic.HeuristicType;


public class MainView extends Stage {
    private static MainView instance = null;
    private int rowsAndColumns;

    private int[][] previousStartBoard;
    private boolean isSetBoard = false;

    private TextField[][] puzzleField;
    private TextField[][] goalPuzzleField;
    private TextField textFieldSize;

    private ComboBox<HeuristicType> heuristicTypeComboBox;
    private ComboBox<AlgorithmType> algorithmTypeComboBox;

    private Button runButton;
    private Button setBoardButton;
    private Button resetBoardButton;
    private Button clearBoardButton;
    private Button randomBoardButton;

    private GridPane gpMain;
    private GridPane gp;
    private GridPane gp2;
    private GridPane gp3;
    private GridPane gp4;
    private GridPane gp5;

    private Label labelSize;
    private Label labelHeuristic;
    private Label labelAlgorithm;

    private RunController runController;
    private SetBoardController setBoardController;
    private RandomBoardController randomBoardController;
    private ClearBoardController clearBoardController;
    private ResetBoardController resetBoardController;
    private HeuristicChangeController heuristicChangeController;

    private Tooltip tooltip = new Tooltip();


    private int SEPARATOR = 15;
    private int BUTTONSIZE = 100;
    private int PUZZLESIZE = 50;


    private MainView() {
        this.setTitle("Sliding Puzzle Solver");
        this.getIcons().add(new Image(MainView.class.getResourceAsStream("images/puzzle.png")));

        this.setResizable(false);
        gpMain = new GridPane();
        gpMain.setPadding(new Insets(SEPARATOR));

        gp2 = new GridPane();
        gp3 = new GridPane();
        gp3.setHgap(10);
        gp3.setVgap(10);
        gp3.setPadding(new Insets(SEPARATOR));

        labelSize = new Label("Puzzle size (N x N)");
        textFieldSize = new TextField();
        textFieldSize.setPrefSize(20,20);
        setBoardButton = new Button("Set Boards");
        setBoardButton.setPrefWidth(BUTTONSIZE);
        resetBoardButton = new Button("Reset Board");
        resetBoardButton.setDisable(true);
        resetBoardButton.setPrefWidth(BUTTONSIZE);
        clearBoardButton = new Button("Clear Boards");
        clearBoardButton.setDisable(true);
        clearBoardButton.setPrefWidth(BUTTONSIZE);
        randomBoardButton = new Button("Random Board");
        randomBoardButton.setDisable(true);
        randomBoardButton.setPrefWidth(BUTTONSIZE);

        gp3.add(labelSize, 0, 0);
        gp3.add(textFieldSize, 1, 0);

        gp3.add(setBoardButton, 0, 2);
        gp3.add(randomBoardButton, 1, 2);
        gp3.add(resetBoardButton, 0, 3);
        gp3.add(clearBoardButton, 1, 3);

        gp2.add(gp3, 0, 0);
        gpMain.add(gp2, 1, 0);

        setBoardController = new SetBoardController();
        setBoardButton.setOnAction(setBoardController);
        resetBoardController = new ResetBoardController();
        resetBoardButton.setOnAction(resetBoardController);
        clearBoardController = new ClearBoardController();
        clearBoardButton.setOnAction(clearBoardController);
        randomBoardController = new RandomBoardController();
        randomBoardButton.setOnAction(randomBoardController);

        Scene sc = new Scene(gpMain);
        sc.getStylesheets().add("Control/view/stylesheet.css");
        setScene(sc);
        show();
    }

    public static MainView getInstance() {
        if (instance == null) {
            instance = new MainView();
        }
        return instance;
    }

    public void setBoard() {
        gpMain.getChildren().remove(gp);
        gp = new GridPane();
        gp.setMaxSize(100, 100);
        gp.setVgap(1);
        gp.setHgap(1);
        gp.setPadding(new Insets(SEPARATOR));

        puzzleField = new TextField[rowsAndColumns][rowsAndColumns];
        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                puzzleField[i][j] = new TextField();
                puzzleField[i][j].setAlignment(Pos.CENTER);
                puzzleField[i][j].setMinSize(PUZZLESIZE, PUZZLESIZE);
                puzzleField[i][j].getStyleClass().add("Board");
                gp.add(puzzleField[i][j], j, i);
            }

        gpMain.add(gp, 0, 0);

        gpMain.getChildren().remove(gp5);
        gp5 = new GridPane();
        gp5.setMaxSize(100, 100);
        gp5.setVgap(1);
        gp5.setHgap(1);
        gp5.setPadding(new Insets(SEPARATOR));

        gpMain.add(gp5, 2, 0);

        setGoalPuzzleField();

        //Independent of rows and columns
        if (!isSetBoard) {
            gp4 = new GridPane();
            gp4.setHgap(10);
            gp4.setVgap(10);
            gp4.setPadding(new Insets(SEPARATOR));
            labelHeuristic = new Label("Heuristic");
            labelAlgorithm = new Label("Algorithm");

            heuristicTypeComboBox = new ComboBox<HeuristicType>();
            heuristicTypeComboBox.getItems().add(HeuristicType.Hamming);
            heuristicTypeComboBox.getItems().add(HeuristicType.Manhattan);
            heuristicTypeComboBox.getSelectionModel().selectFirst();

            heuristicChangeController = new HeuristicChangeController();
            heuristicTypeComboBox.setOnAction(heuristicChangeController);

            heuristicTypeComboBox.setTooltip(tooltip);
            setTooltipSettings();

            algorithmTypeComboBox = new ComboBox<AlgorithmType>();
            algorithmTypeComboBox.getItems().add(AlgorithmType.HillClimb);
            algorithmTypeComboBox.getItems().add(AlgorithmType.BestFirst);
            algorithmTypeComboBox.getSelectionModel().selectFirst();

            runButton = new Button("Run");
            runButton.setPrefWidth(BUTTONSIZE);


            gp4.add(labelAlgorithm, 0, 0);
            gp4.add(algorithmTypeComboBox, 1, 0);
            gp4.add(labelHeuristic, 0, 1);
            gp4.add(heuristicTypeComboBox, 1, 1);
            gp4.add(runButton, 0, 2);


            gp2.add(gp4, 0, 1);

            runController = new RunController();
            runButton.setOnAction(runController);

            isSetBoard = true;

        }

        previousStartBoard = null;
    }

    private int[][] getPuzzleField(TextField[][] board) {
        int[][] toReturn = new int[rowsAndColumns][rowsAndColumns];
        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                if (board[i][j].getText().isEmpty())
                    toReturn[i][j] = 0;
                else
                    toReturn[i][j] = Integer.valueOf(board[i][j].getText());
            }
        return toReturn;
    }

    public int[][] getStartPuzzleField() {
        int[][] toReturn = getPuzzleField(puzzleField);
        return toReturn;
    }

    public int[][] getGoalPuzzleField() {
        int[][] toReturn = getPuzzleField(goalPuzzleField);
        return toReturn;
    }

    public void setPuzzleField(int[][] board) {
        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                if (board[i][j] == 0)
                    puzzleField[i][j].setText("");
                else
                    puzzleField[i][j].setText(String.valueOf(Math.abs(board[i][j])));
            }
    }


    public void enableOrDisableCommands(boolean val) {

        setBoardButton.setDisable(!val);
        clearBoardButton.setDisable(!val);
        resetBoardButton.setDisable(!val);
        randomBoardButton.setDisable(!val);

        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++)
                puzzleField[i][j].setEditable(val);
    }


    public HeuristicType getHeuristicTypeComboBox() {
        return heuristicTypeComboBox.getSelectionModel().getSelectedItem();
    }


    public int getRowsAndColumns() {
        return rowsAndColumns;
    }

    public void setRowsAndColumns(int rows) {
        this.rowsAndColumns = rows;
    }


    public int[][] getPreviousStartBoard() {
        return previousStartBoard;
    }

    public void setPreviousStartBoard(int[][] previousStartBoard) {
        this.previousStartBoard = previousStartBoard;
    }

    public AlgorithmType getAlgorithmTypeComboBox() {
        return algorithmTypeComboBox.getSelectionModel().getSelectedItem();
    }

    public String getBoardSize() {
        return textFieldSize.getText();
    }


    public void setTooltipText(String text) {
        tooltip.setText(text);
    }

    private void setTooltipSettings() {
        tooltip.setText("Hamming - sum of wrongly placed pieces");
    }

    private void setGoalPuzzleField() {

        int num = 1;
        int[][] goalBoard = new int[rowsAndColumns][rowsAndColumns];
        goalPuzzleField = new TextField[rowsAndColumns][rowsAndColumns];

        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                goalPuzzleField[i][j] = new TextField();
                goalPuzzleField[i][j].setAlignment(Pos.CENTER);
                goalPuzzleField[i][j].setMinSize(PUZZLESIZE, PUZZLESIZE);
                goalPuzzleField[i][j].getStyleClass().add("Board");
                gp5.add(goalPuzzleField[i][j], j, i);
                if (i == rowsAndColumns - 1 && j == rowsAndColumns - 1)
                    goalBoard[i][j] = 0;
                else
                    goalBoard[i][j] = num++;
            }
        setGoalPuzzle(goalBoard);
    }

    public void setGoalPuzzle(int[][] board) {
        for (int i = 0; i < rowsAndColumns; i++)
            for (int j = 0; j < rowsAndColumns; j++) {
                if (board[i][j] == 0)
                    goalPuzzleField[i][j].setText("");
                else
                    goalPuzzleField[i][j].setText(String.valueOf(Math.abs(board[i][j])));
            }
    }


}
