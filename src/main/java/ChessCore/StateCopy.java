package ChessCore;

import ChessCore.Pieces.*;

public class StateCopy {

    public static Piece[][] copyBoard(Piece[][] board) {
        Piece[][] newBoard = BoardInitializer.boardInit();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof Pawn) {
                    newBoard[i][j] = new Pawn(board[i][j].getPosition(), board[i][j].getColor());
                } else if (board[i][j] instanceof Queen) {
                    newBoard[i][j] = new Queen(board[i][j].getPosition(), board[i][j].getColor());
                } else if (board[i][j] instanceof Rook) {
                    newBoard[i][j] = new Rook(board[i][j].getPosition(), board[i][j].getColor());
                } else if (board[i][j] instanceof Bishop) {
                    newBoard[i][j] = new Bishop(board[i][j].getPosition(), board[i][j].getColor());
                } else if (board[i][j] instanceof Knight) {
                    newBoard[i][j] = new Knight(board[i][j].getPosition(), board[i][j].getColor());
                } else if (board[i][j] instanceof King) {
                    newBoard[i][j] = new King(board[i][j].getPosition(), board[i][j].getColor());
                } else {
                    newBoard[i][j] = null;
                    continue;
                }
                newBoard[i][j].setMovesNum(board[i][j].getMovesNum());
            }
        }
        return newBoard;
    }

    public static int[] copyLastMoved(int[] lastMoved) {
        int[] newLastMoved = new int[2];
        newLastMoved[0] = lastMoved[0];
        newLastMoved[1] = lastMoved[1];

        return newLastMoved;
    }

}
