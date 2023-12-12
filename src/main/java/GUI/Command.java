package GUI;

import ChessCore.ChessGame;

public interface Command {

    void execute(ChessGame game);
}
