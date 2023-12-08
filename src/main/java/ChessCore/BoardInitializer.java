package ChessCore;

import ChessCore.Pieces.*;

public class BoardInitializer {

    public static Piece[][] boardInit() {
        Piece[][] board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn((char) ('A' + i) + "2", true);
            board[6][i] = new Pawn((char) ('A' + i) + "7", false);
        }

        board[0][0] = new Rook("A1", true);
        board[0][1] = new Knight("B1", true);
        board[0][2] = new Bishop("C1", true);
        board[0][3] = new Queen("D1", true);
        board[0][4] = new King("E1", true);
        board[0][5] = new Bishop("F1", true);
        board[0][6] = new Knight("G1", true);
        board[0][7] = new Rook("H1", true);

        board[7][0] = new Rook("A8", false);
        board[7][1] = new Knight("B8", false);
        board[7][2] = new Bishop("C8", false);
        board[7][3] = new Queen("D8", false);
        board[7][4] = new King("E8", false);
        board[7][5] = new Bishop("F8", false);
        board[7][6] = new Knight("G8", false);
        board[7][7] = new Rook("H8", false);

        return board;
    }

    public static char[][] generateBoard() {
        char[][] x = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                x[i][j] = 'f';
            }
        }
        return x;
    }
}
