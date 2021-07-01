package Control;

import Control.view.MainView;
import Problem.heuristic.HeuristicType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HeuristicChangeController implements EventHandler<ActionEvent> {


    @Override
    public void handle(ActionEvent event) {
        MainView mainView =  MainView.getInstance();
        HeuristicType heurType = mainView.getHeuristicTypeComboBox();

        switch (heurType) {
            case Manhattan:
                mainView.setTooltipText("Manhattan - sum of distance from goal state");
                break;
            case Hamming:
                mainView.setTooltipText("Hamming - sum of wrongly placed pieces");
                break;
        }

    }
}
