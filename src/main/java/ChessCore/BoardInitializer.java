package ChessCore;

public class BoardInitializer {

    public static Piece[][] boardInit() {
        Piece[][] board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn((char) ('A' + i) + "2", (char) ('A' + i) + "2", true);
            board[6][i] = new Pawn((char) ('A' + i) + "7", (char) ('A' + i) + "7", false);
        }
        board[0][0] = new Rook("A1", "A1", true);
        board[0][1] = new Knight("B1", "B1", true);
        board[0][2] = new Bishop("C1", "C1", true);
        board[0][3] = new Queen("D1", "D1", true);
        board[0][4] = new King("E1", "E1", true);
        board[0][5] = new Bishop("F1", "F1", true);
        board[0][6] = new Knight("G1", "G1", true);
        board[0][7] = new Rook("H1", "H1", true);

        board[7][0] = new Rook("A8", "A8", false);
        board[7][1] = new Knight("B8", "B8", false);
        board[7][2] = new Bishop("C8", "C8", false);
        board[7][3] = new Queen("D8", "D8", false);
        board[7][4] = new King("E8", "E8", false);
        board[7][5] = new Bishop("F8", "F8", false);
        board[7][6] = new Knight("G8", "G8", false);
        board[7][7] = new Rook("H8", "H8", false);
        
        return board;
    }

    public static Piece[][] whiteInit(Piece[][] board) {
        Piece[][] white = new Piece[2][8];
        for (int i = 0; i < 8; i++) {
            white[1][i] = board[1][i];
        }
        white[0][0] = board[0][0];
        white[0][1] = board[0][1];
        white[0][2] = board[0][2];
        white[0][3] = board[0][3];
        white[0][4] = board[0][4];
        white[0][5] = board[0][5];
        white[0][6] = board[0][6];
        white[0][7] = board[0][7];
        return white;
    }

    public static Piece[][] blackInit(Piece[][] board) {
        Piece[][] black = new Piece[2][8];
        for (int i = 0; i < 8; i++) {
            black[1][i] = board[6][i];
        }
        black[0][0] = board[7][0];
        black[0][1] = board[7][1];
        black[0][2] = board[7][2];
        black[0][3] = board[7][3];
        black[0][4] = board[7][4];
        black[0][5] = board[7][5];
        black[0][6] = board[7][6];
        black[0][7] = board[7][7];
        return black;
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
