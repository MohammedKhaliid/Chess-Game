package ChessCore;

import java.lang.reflect.Array;

public class ChessGame {

    private Piece board[][] = new Piece[8][8];
    private Piece white[][] = new Piece[2][8];
    private Piece black[][] = new Piece[2][8];
    private boolean gameOver;
    private int[] lastMoved;
    private boolean whiteTurn;

    public ChessGame() {
        board = new Piece[8][8];
        white = new Piece[2][8];
        black = new Piece[2][8];
        lastMoved = new int[2];
        lastMoved[0] = -1;
        lastMoved[1] = -1;
        whiteTurn = true;
        gameOver = false;

        for (int i = 0; i < 8; i++) {
            white[1][i] = board[1][i] = new Pawn((char) ('A' + i) + "2", (char) ('A' + i) + "2", true);
            black[1][i] = board[6][i] = new Pawn((char) ('A' + i) + "7", (char) ('A' + i) + "7", false);
        }
        white[0][0] = board[0][0] = new Rook("A1", "A1", true);
        white[0][1] = board[0][1] = new Knight("B1", "B1", true);
        white[0][2] = board[0][2] = new Bishop("C1", "C1", true);
        white[0][3] = board[0][3] = new Queen("D1", "D1", true);
        white[0][4] = board[0][4] = new King("E1", "E1", true);
        white[0][5] = board[0][5] = new Bishop("F1", "F1", true);
        white[0][6] = board[0][6] = new Knight("G1", "G1", true);
        white[0][7] = board[0][7] = new Rook("H1", "H1", true);

        black[0][0] = board[7][0] = new Rook("A8", "A8", false);
        black[0][1] = board[7][1] = new Knight("B8", "B8", false);
        black[0][2] = board[7][2] = new Bishop("C8", "C8", false);
        black[0][3] = board[7][3] = new Queen("D8", "D8", false);
        black[0][4] = board[7][4] = new King("E8", "E8", false);
        black[0][5] = board[7][5] = new Bishop("F8", "F8", false);
        black[0][6] = board[7][6] = new Knight("G8", "G8", false);
        black[0][7] = board[7][7] = new Rook("H8", "H8", false);

    }

    public static boolean inBoard(int row, int column) {
        return (row >= 0 && row < 8 && column >= 0 && column < 8);
    }

    private static char[][] generateBoard() {
        char[][] x = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                x[i][j] = 'f';
            }
        }
        return x;
    }

    private boolean isKingSafe(char pieceType) {
        //is king safe from piecetype
        //type black then check if white king safe       
        //type white then check if black king safe

        if (pieceType == 'b') {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 8; i++) {
                    if (black[j][i].getIsCaptured()) {
                        continue;
                    }
                    char[][] danger = this.allValidMoves(black[j][i], true);
                    int[] whiteKingCoordinates = Calculations.calcPosition(white[0][4].getPosition());
                    if (danger[whiteKingCoordinates[1]][whiteKingCoordinates[0]] == 't') {
                        return false;
                    }
                }
            }
        } else if (pieceType == 'w') {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 8; i++) {
                    if (white[j][i].getIsCaptured()) {
                        continue;
                    }
                    char[][] danger = this.allValidMoves(white[j][i], true);
                    int[] blackKingCoordinates = Calculations.calcPosition(black[0][4].getPosition());
                    if (danger[blackKingCoordinates[1]][blackKingCoordinates[0]] == 't') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private char[][] allValidMoves(Piece p, boolean test) {
        char[][] all = generateBoard();
        int[] pieceCoordinates = Calculations.calcPosition(p.getPosition());
        int column = pieceCoordinates[0];
        int row = pieceCoordinates[1];
        int[] dx;
        int[] dy;
        // is instanceof() then check
        if (p instanceof Pawn) {
            if (p.getColor() == true)//white
            {
                dx = new int[]{2, 1, 1, 1};
                dy = new int[]{0, 0, 1, -1};
            } else {
                dx = new int[]{-2, -1, -1, -1};
                dy = new int[]{0, 0, 1, -1};
            }
            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    if (p.getMovesNum() == 0 && ChessGame.inBoard(row + dx[i], column + dy[i]) == true && board[row + dx[i]][column + dy[i]] == null && board[row + dx[i + 1]][column + dy[i + 1]] == null) {
                        all[row + dx[i]][column + dy[i]] = 't';
                    }
                } else if (i == 1) {
                    if (ChessGame.inBoard(row + dx[i], column + dy[i]) && board[row + dx[i]][column + dy[i]] == null) {
                        all[row + dx[i]][column + dy[i]] = 't';
                    }
                } else {
                    if (ChessGame.inBoard(row + dx[i], column + dy[i]) && board[row + dx[i]][column + dy[i]] != null && board[row + dx[i]][column + dy[i]].getColor() != p.getColor()) {
                        all[row + dx[i]][column + dy[i]] = 't';
                    }
                }
            }

            //EN PaaaaassSsssaaaAaannnNnnT
            int enRow, enX;
            if (p.getColor() == true) {
                enRow = 4;
                enX = 1;
            } else {
                enRow = 3;
                enX = -1;
            }

            if (lastMoved[0] == row && row == enRow) {
                if (ChessGame.inBoard(row, column + 1)) {
                    Piece rightNeighbor = board[row][column + 1];

                    if (rightNeighbor != null && rightNeighbor.getMovesNum() == 1 && rightNeighbor instanceof Pawn && lastMoved[1] == column + 1) {
                        all[row + enX][column + 1] = 'e';
                    }
                }
                if (ChessGame.inBoard(row, column - 1)) {
                    Piece leftNeighbor = board[row][column - 1];

                    if (leftNeighbor != null && leftNeighbor.getMovesNum() == 1 && leftNeighbor instanceof Pawn && lastMoved[1] == column - 1) {
                        all[row + enX][column - 1] = 'e';
                    }
                }
            }
        } else if (p instanceof Knight) {
            dx = new int[]{-2, -1, 1, 2, 2, 1, -1, -2};
            dy = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
            for (int i = 0; i < 8; i++) {
                if (ChessGame.inBoard(row + dx[i], column + dy[i])) {
                    if (board[row + dx[i]][column + dy[i]] == null || board[row + dx[i]][column + dy[i]].getColor() != p.getColor()) {
                        all[row + dx[i]][column + dy[i]] = 't';
                    }
                }
            }
        } else if (p instanceof Rook) {
            dx = new int[]{1, -1, 0, 0};
            dy = new int[]{0, 0, 1, -1};
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 8; j++) {
                    if (ChessGame.inBoard(row + (dx[i] * j), column + (dy[i] * j))) {
                        if (board[row + (dx[i] * j)][column + (dy[i] * j)] == null) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                        } else if (board[row + (dx[i] * j)][column + (dy[i] * j)].getColor() != p.getColor()) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        } else if (p instanceof Bishop) {
            dx = new int[]{1, -1, -1, 1};
            dy = new int[]{1, -1, 1, -1};
            for (int i = 0; i < 4; i++) {
                for (int j = 1; j < 8; j++) {
                    if (ChessGame.inBoard(row + (dx[i] * j), column + (dy[i] * j))) {
                        if (board[row + (dx[i] * j)][column + (dy[i] * j)] == null) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                        } else if (board[row + (dx[i] * j)][column + (dy[i] * j)].getColor() != p.getColor()) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        } else if (p instanceof Queen) {
            dx = new int[]{1, -1, -1, 1, 1, -1, 0, 0};
            dy = new int[]{1, -1, 1, -1, 0, 0, 1, -1};
            for (int i = 0; i < 8; i++) {
                for (int j = 1; j < 8; j++) {
                    if (ChessGame.inBoard(row + (dx[i] * j), column + (dy[i] * j))) {
                        if (board[row + (dx[i] * j)][column + (dy[i] * j)] == null) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                        } else if (board[row + (dx[i] * j)][column + (dy[i] * j)].getColor() != p.getColor()) {
                            all[row + (dx[i] * j)][column + (dy[i] * j)] = 't';
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        } else if (p instanceof King) {
            dx = new int[]{1, -1, -1, 1, 1, -1, 0, 0};
            dy = new int[]{1, -1, 1, -1, 0, 0, 1, -1};
            for (int i = 0; i < 8; i++) {
                if (ChessGame.inBoard(row + dx[i], column + dy[i])) {
                    if (board[row + dx[i]][column + dy[i]] == null || board[row + dx[i]][column + dy[i]].getColor() != p.getColor()) {
                        all[row + dx[i]][column + dy[i]] = 't';
                    }
                }
            }
            //Castle
            if (p.getMovesNum() == 0) {
                if (board[row][column + 1] == null && board[row][column + 2] == null && board[row][column + 3] instanceof Rook && board[row][column + 3].getMovesNum() == 0) {
                    all[row][column + 2] = all[row][column + 3] = 'r';
                }
                if (board[row][column - 1] == null && board[row][column - 2] == null && board[row][column - 3] == null && board[row][column - 4] instanceof Rook && board[row][column - 4].getMovesNum() == 0) {
                    all[row][column - 2] = all[row][column - 4] = 'l';
                }
            }
        }
        if (test == false) {
            if (p instanceof King) {
                if (p.getMovesNum() == 0) {
                    if (all[row][0] == 'l')//can castle left
                    {
                        for (int i = 0; i < 3; i++) {
                            board[row][column - i] = p;
                            p.setPosition(Calculations.reverseCalcPosition(row, column - i));
                            if (i != 0)// not king original place
                            {
                                board[row][column] = null;
                            }
                            if ((p.getColor() == true && this.isKingSafe('b') == false) || (p.getColor() == false && this.isKingSafe('w') == false)) {
                                all[row][column - 2] = all[row][column - 4] = 'f';
                                board[row][column - i] = null;
                                p.setPosition(Calculations.reverseCalcPosition(row, column));
                                board[row][column] = p;
                                break;
                            }
                            board[row][column - i] = null;
                            p.setPosition(Calculations.reverseCalcPosition(row, column));
                            board[row][column] = p;
                        }
                    }
                    if (all[row][7] == 'r')//can castle right
                    {
                        for (int i = 1; i <= 3; i++) {
                            board[row][7 - i] = p;
                            p.setPosition(Calculations.reverseCalcPosition(row, 7 - i));
                            if (i != 3) // not king original place
                            {
                                board[row][column] = null;
                            }
                            if ((p.getColor() == true && this.isKingSafe('b') == false) || (p.getColor() == false && this.isKingSafe('w') == false)) {
                                all[row][column + 2] = all[row][column + 3] = 'f';
                                board[row][7 - i] = null;
                                p.setPosition(Calculations.reverseCalcPosition(row, column));
                                board[row][column] = p;
                                break;

                            }
                            board[row][7 - i] = null;
                            p.setPosition(Calculations.reverseCalcPosition(row, column));
                            board[row][column] = p;
                        }
                    }
                }
            }
            for (int i = 7; i >= 0; i--) {
                for (int j = 0; j < 8; j++) {
                    if (all[i][j] != 'f') {
                        Piece temp = board[i][j];
                        if (temp != null) {
                            board[i][j].setIsCaptured(true);
                        }
                        p.setPosition(Calculations.reverseCalcPosition(i, j));
                        board[i][j] = p;
                        board[row][column] = null;
                        if (all[i][j] == 'e') {
                            if (p.getColor() == true) {
                                board[i - 1][j].setIsCaptured(true);
                            } else {
                                board[i + 1][j].setIsCaptured(true);

                            }
                        }
                        if ((p.getColor() == true && this.isKingSafe('b') == false) || (p.getColor() == false && this.isKingSafe('w') == false)) {
                            all[i][j] = 'f';
                        }
                        if (all[i][j] == 'e') {
                            if (p.getColor() == true) {
                                board[i - 1][j].setIsCaptured(false);
                            } else {
                                board[i + 1][j].setIsCaptured(false);
                            }
                        }
                        board[i][j] = temp;
                        if (temp != null) {
                            board[i][j].setIsCaptured(false);
                        }
                        p.setPosition(Calculations.reverseCalcPosition(row, column));
                        board[row][column] = p;
                    }
                }
            }
        }
        return all;
    }

    private boolean isStalemate() {
        Piece[][] piece;
        if (whiteTurn) {
            piece = white;
        } else {
            piece = black;
        }

        char[][] ithBoard = generateBoard();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (piece[i][j].getIsCaptured() == false) {
                    ithBoard = allValidMoves(piece[i][j], false);
                }

                for (int k = 0; k < 8; k++) {
                    for (int m = 0; m < 8; m++) {
                        if (ithBoard[k][m] != 'f') {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void promote(int row, int column, char promotTo) {

        Piece p = null;

        String initPos = board[row][column].getInitPosition();
        String pos = Calculations.reverseCalcPosition(row, column);

        int[] initialCoord = Calculations.calcPosition(initPos);
        int initialCol = initialCoord[0];
        int initialRow = initialCoord[1];

        switch (promotTo) {
            case 'K':
            case 'k':
                p = new Knight(initPos, pos, whiteTurn);
                break;
            case 'B':
            case 'b':
                p = new Bishop(initPos, pos, whiteTurn);
                break;
            case 'R':
            case 'r':
                p = new Rook(initPos, pos, whiteTurn);
                break;
            case 'Q':
            case 'q':
                p = new Queen(initPos, pos, whiteTurn);
                break;
        }
        if (whiteTurn) {
            white[1][initialCol] = p;
        } else {
            black[1][initialCol] = p;
        }
        board[row][column] = p;
    }

    private boolean draw() {
        //pawns,rooks,knights,bishops,queen;
        int[] bl = new int[]{0, 0, 0, 0, 0};
        int[] wh = new int[]{0, 0, 0, 0, 0};
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++) {
                if (black[j][i].getIsCaptured() == false) {
                    if (j == 1) {
                        bl[0]++;
                    } else if (black[j][i] instanceof Rook) {
                        bl[1]++;
                    } else if (black[j][i] instanceof Knight) {
                        bl[2]++;
                    } else if (black[j][i] instanceof Bishop) {
                        bl[3]++;
                    } else if (black[j][i] instanceof Queen) {
                        bl[4]++;
                    }
                }
                if (white[j][i].getIsCaptured() == false) {
                    if (j == 1) {
                        wh[0]++;
                    } else if (white[j][i] instanceof Rook) {
                        wh[1]++;
                    } else if (white[j][i] instanceof Knight) {
                        wh[2]++;
                    } else if (white[j][i] instanceof Bishop) {
                        wh[3]++;
                    } else if (white[j][i] instanceof Queen) {
                        wh[4]++;
                    }
                }
            }
        }
        if (wh[0] == 0 && bl[0] == 0 && wh[1] == 0 && bl[1] == 0 && wh[4] == 0 && bl[4] == 0) {
            if (wh[2] == 0 && bl[2] == 0 && wh[3] == 0 && bl[3] == 0)//lone kings
            {
                return true;
            }
            if (wh[2] == 1 && bl[2] == 1 && wh[3] == 0 && bl[3] == 0)//knight & king vs knight & king
            {
                return true;
            }
            if (wh[2] == 0 && bl[2] == 0 && wh[3] == 1 && bl[3] == 1)//Bishop & king vs Bishop & king
            {
                return true;
            }
            if ((wh[2] == 0 && bl[2] == 0 && wh[3] == 1 && bl[3] == 0) || (wh[2] == 0 && bl[2] == 0 && wh[3] == 0 && bl[3] == 1))//Bishop & king vs lone king
            {
                return true;
            }
            if ((wh[2] == 1 && bl[2] == 0 && wh[3] == 0 && bl[3] == 0) || (wh[2] == 0 && bl[2] == 1 && wh[3] == 0 && bl[3] == 0))//knight& king vs lone king
            {
                return true;
            }
            if ((wh[2] == 1 && bl[2] == 0 && wh[3] == 0 && bl[3] == 1) || (wh[2] == 0 && bl[2] == 1 && wh[3] == 1 && bl[3] == 0))//Bishop & king vs knight & king
            {
                return true;
            }
            if ((wh[2] == 2 && bl[2] == 0 && wh[3] == 0 && bl[3] == 0) || (wh[2] == 0 && bl[2] == 2 && wh[3] == 0 && bl[3] == 0))//2 knight & king vs lone king
            {
                return true;
            }
        }
        return false;
    }

    public String move(String from, String to, char promoteTo) {

        String ret = "";
        char[][] canMoveToBoard = generateBoard();
        int[] fromCoordinates = Calculations.calcPosition(from);
        int fromRow = fromCoordinates[1];
        int fromColumn = fromCoordinates[0];
        int[] toCoordinates = Calculations.calcPosition(to);
        int toRow = toCoordinates[1];
        int toColumn = toCoordinates[0];
        Piece movingPiece = board[fromRow][fromColumn];
        Piece capturedPiece = board[toRow][toColumn];

        if (gameOver) {

            return ret = "Game already ended\n";
        }
        if (promoteTo == 'x') {
            if (movingPiece == null || !movingPiece.isValidMove(from, to) || capturedPiece instanceof King || (whiteTurn && !movingPiece.getColor()) || (!whiteTurn && movingPiece.getColor())) {
                ret = "Invalid move\n";
            } else {
                canMoveToBoard = allValidMoves(movingPiece, false);
                if (canMoveToBoard[toRow][toColumn] == 'f') {
                    ret = "invalid move\n";
                } else if (capturedPiece != null || (capturedPiece == null && (canMoveToBoard[toRow][toColumn] == 'e' || canMoveToBoard[toRow][toColumn] == 'r' || canMoveToBoard[toRow][toColumn] == 'l'))) {

                    if (canMoveToBoard[toRow][toColumn] == 'e') {
                        ret = "Enpassant\nCaptured Pawn\n";
                        movingPiece.setPosition(to);
                        movingPiece.increaseMovesNum();
                        board[toRow][toColumn] = movingPiece;
                        board[fromRow][fromColumn] = null;
                        if (whiteTurn == true) {
                            board[toRow - 1][toColumn].setIsCaptured(true);
                            board[toRow - 1][toColumn] = null;
                        } else {
                            board[toRow + 1][toColumn].setIsCaptured(true);
                            board[toRow + 1][toColumn] = null;
                        }
                    } else if (canMoveToBoard[toRow][toColumn] == 'r') {

                        ret = "Castle\n";
                        movingPiece.setPosition(Calculations.reverseCalcPosition(toRow, 6));//set king position;
                        movingPiece.increaseMovesNum();
                        board[toRow][7].setPosition(Calculations.reverseCalcPosition(toRow, 5));//set rook position
                        board[toRow][7].increaseMovesNum();
                        board[toRow][6] = board[fromRow][fromColumn];//move king on board
                        board[toRow][5] = board[toRow][7];//move rook on board
                        board[toRow][7] = null;//remove rook from initial position
                        board[fromRow][fromColumn] = null;// remove king form initial position
                    } else if (canMoveToBoard[toRow][toColumn] == 'l') {
                        ret = "Castle\n";
                        movingPiece.setPosition(Calculations.reverseCalcPosition(toRow, 2));//set king position;
                        movingPiece.increaseMovesNum();
                        board[toRow][0].setPosition(Calculations.reverseCalcPosition(toRow, 3));//set rook position
                        board[toRow][0].increaseMovesNum();
                        board[toRow][2] = board[fromRow][fromColumn];//move king on board
                        board[toRow][3] = board[toRow][7];//move rook on board
                        board[toRow][0] = null;//remove rook from initial position
                        board[fromRow][fromColumn] = null;// remove king form initial position
                    } else if (canMoveToBoard[toRow][toColumn] == 't') {
                        ret = "Captured " + capturedPiece + "\n";
                        movingPiece.setPosition(to);//set moving position
                        movingPiece.increaseMovesNum();
                        capturedPiece.setIsCaptured(true);//set captured
                        board[toRow][toColumn] = movingPiece;
                        board[fromRow][fromColumn] = null;
                    }

                } else if (capturedPiece == null) {
                    movingPiece.setPosition(to);
                    movingPiece.increaseMovesNum();
                    board[toRow][toColumn] = movingPiece;
                    board[fromRow][fromColumn] = null;
                }
            }

        } else {
            //promote
            if (movingPiece == null || !movingPiece.isValidMove(from, to) || capturedPiece instanceof King || (toRow != 7 && toRow != 0)) {
                ret = "Invalid move\n";
            } else {
                canMoveToBoard = allValidMoves(movingPiece, false);
                if (canMoveToBoard[toRow][toColumn] == 'f') {
                    ret = "Invalid move\n";
                } else {
                    if (capturedPiece != null) {
                        capturedPiece.setIsCaptured(true);
                        ret = "Captured " + capturedPiece + "\n";
                    }
                    board[toRow][toColumn] = board[fromRow][fromColumn];
                    board[fromRow][fromColumn] = null;
                    promote(toRow, toColumn, promoteTo);
                    board[toRow][toColumn].increaseMovesNum();
                }
            }

        }
        if (!ret.equals("Invalid move\n")) {
            lastMoved[0] = toRow;
            lastMoved[1] = toColumn;
            whiteTurn = !whiteTurn;
        } else {
//            printBoard();
            return ret;
        }

        if (draw()) {
            ret += "Insufficient Material\n";
            gameOver = true;
        } else if (isStalemate()) {
            gameOver = true;
            if (whiteTurn && !isKingSafe('b')) {
                ret += "Black Won\n";
            } else if (!whiteTurn && !isKingSafe('w')) {
                ret += "White Won\n";
            } else {
                ret += "Stalemate\n";
            }
        } else if (whiteTurn && !isKingSafe('b')) {
            ret += "White in check\n";
        } else if (!whiteTurn && !isKingSafe('w')) {
            ret += "Black in check\n";
        }

//        printBoard();

        return ret;
    }
//    public Piece getPiece(int i, int j) {
//        return board[i][j];
//    }
//    private void printBoard() {
//        for (int i = 7; i >= 0; i--) {
//            for (int j = 0; j < 8; j++) {
//                if (board[i][j] != null) {
//                    System.out.print(board[i][j] + "\t");
//                } else {
//                    System.out.print("null\t");
//                }
//            }
//            System.out.println("");
//        }
//        System.out.println("------------------------------------------------");
//    }

    }
