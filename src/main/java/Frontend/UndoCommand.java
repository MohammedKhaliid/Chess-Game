package Frontend;

import ChessCore.ChessGame;
import ChessCore.HistoryManager;

public class UndoCommand implements Command {
    private HistoryManager historyManager;

    public UndoCommand(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public void execute(ChessGame game) {
        System.out.println("Undo key pressed");
        historyManager.undo(game);
    }
}
