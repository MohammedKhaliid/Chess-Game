package ChessCore;

import ChessCore.Pieces.Piece;

public class State {

    private final Piece[][] board;
    private final int[] lastMoved;
    private final boolean whiteTurn;
    private final boolean gameOver;

    public State(Piece[][] board, int[] lastMoved, boolean whiteTurn, boolean gameOver) {
        this.board = StateCopy.copyBoard(board);
        this.lastMoved = StateCopy.copyLastMoved(lastMoved);
        this.whiteTurn = whiteTurn;
        this.gameOver = gameOver;
    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public int[] getLastMoved() {
        return this.lastMoved;
    }

    public boolean getWhiteTurn() {
        return this.whiteTurn;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

}
