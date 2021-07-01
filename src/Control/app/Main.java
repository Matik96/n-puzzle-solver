package Control.app;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import Control.view.MainView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) {
        MainView.getInstance();
        new Alert(Alert.AlertType.INFORMATION, "Informations about process will be stored in: " + System.getProperty("user.dir") + "\\LogFile.txt", ButtonType.OK).show();
    }

}