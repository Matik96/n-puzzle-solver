package Control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import Problem.state.State;
import Control.view.MainView;

public class SetBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {
        try {

            int rowsAndColumns = Integer.valueOf(MainView.getInstance().getBoardSize());

            if (rowsAndColumns < 2 || rowsAndColumns > 15) {
                throw new NumberFormatException();
            }

            MainView.getInstance().setRowsAndColumns(rowsAndColumns);
            MainView.getInstance().setBoard();
            MainView.getInstance().enableOrDisableCommands(true);
            MainView.getInstance().sizeToScene();

            State.setRowsAndColumns(rowsAndColumns);

        } catch (NumberFormatException nfe) {
            new Alert(AlertType.INFORMATION, "Entered value should be in range 1 - 14", ButtonType.OK).show();
        }
    }

}
