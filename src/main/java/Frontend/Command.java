package Frontend;

import ChessCore.ChessGame;

public interface Command {

    void execute(ChessGame game);
}
