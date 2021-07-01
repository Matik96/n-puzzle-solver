package Control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Problem.state.State;
import Problem.state.StateGenerator;
import Control.view.MainView;

import java.util.List;

public class RandomBoardController implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent arg0) {

        State currentState = StateGenerator.makeGoalState();
        StateGenerator generator = new StateGenerator();

        int numOfShuffles = 10000;
        while (numOfShuffles > 0) {
            List<State> nextStates = generator.generateStates(currentState);

            int maxIndex = nextStates.size();
            double randomDouble = Math.random();
            randomDouble = randomDouble * maxIndex;
            int randomInt = (int) randomDouble;

            currentState = nextStates.get(randomInt);
            numOfShuffles--;
        }

        MainView.getInstance().setPuzzleField(currentState.getBoard());
    }
}
