package Method.algorithm;

import java.util.Stack;

import Problem.state.State;

public class ReturnType {

    private Stack<State> toReturn;
    private boolean isFoundResult;

    public ReturnType(Stack<State> toReturn, boolean isFoundResult) {
        this.toReturn = toReturn;
        this.isFoundResult = isFoundResult;
    }

    public ReturnType() {
        this.toReturn = new Stack<State>();
        this.isFoundResult = false;
    }

    public Stack<State> getToReturn() {
        return toReturn;
    }

    public void setToReturn(Stack<State> toReturn) {
        this.toReturn = toReturn;
    }

    public boolean isFoundResult() {
        return isFoundResult;
    }

    public void setFoundResult(boolean foundResult) {
        isFoundResult = foundResult;
    }

}
