package ChessCore;

import ChessCore.Pieces.Piece;

public class State {

    private Piece[][] board, black, white;
    private int[] lastMoved;
    private boolean whiteTurn, gameOver;

    public State(Piece[][] board,int[] lastMoved, boolean whiteTurn, boolean gameOver) {
        this.board = StateCopy.copyBoard(board);
//        this.black = black;
//        this.white = white;
        this.lastMoved = StateCopy.copyLastMoved(lastMoved);
        this.whiteTurn = whiteTurn;
        this.gameOver = gameOver;
    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public Piece[][] getWhite() {
        return this.white;
    }

    public Piece[][] getBlack() {
        return this.black;
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
    
    public String toString(){
        return whiteTurn+ "";
    }

}
