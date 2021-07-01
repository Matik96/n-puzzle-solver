package Control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Control.view.MainView;

public class ClearBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
        int n = MainView.getInstance().getRowsAndColumns();
        int[][] clearBoard = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                clearBoard[i][j] = 0;
            }
        MainView.getInstance().setPuzzleField(clearBoard);
        MainView.getInstance().setGoalPuzzle(clearBoard);
    }

}
