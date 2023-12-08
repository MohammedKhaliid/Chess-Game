package Frontend;

import ChessCore.ChessGame;
import ChessCore.HistoryManager;

public class RedoCommand implements Command {
    private HistoryManager historyManager;

    public RedoCommand(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public void execute(ChessGame game) {
        System.out.println("Redo key pressed");
        historyManager.redo(game);
    }
}
