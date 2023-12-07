package ChessCore;

public class State {

    private Piece[][] board, black, white;
    private int[] lastMoved;
    private boolean whiteTurn, gameOver;

    public State(Piece[][] board, Piece[][] black, Piece[][] white, int[] lastMoved, boolean whiteTurn, boolean gameOver) {
        this.board = board;
        this.black = black;
        this.white = white;
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

}
