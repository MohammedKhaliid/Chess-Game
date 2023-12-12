package ChessCore;

import java.util.Stack;

public class HistoryManager {

    private final Stack<State> undoStates = new Stack<>();
    private final Stack<State> redoStates = new Stack<>();

    public void undoSave(ChessGame game) {
        undoStates.push(game.save());
    }

    public void redoSave(ChessGame game) {
        redoStates.push(game.save());
    }

    public void undo(ChessGame game) {
        if (undoStates.isEmpty()) {
            return;
        }
        redoSave(game);
        game.revert(undoStates.pop());
//        System.out.println("size of undoStack : " + undoStates.size());
    }

    public void redo(ChessGame game) {
        if (redoStates.isEmpty()) {
            return;
        }
        undoSave(game);
        game.revert(redoStates.pop());
//        System.out.println("size of RedoStack : " + redoStates.size());
    }

    public void clearRedo() {
        while (!redoStates.isEmpty()) {
            redoStates.pop();
        }
    }
}
