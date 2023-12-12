package ChessCore.Pieces;

import ChessCore.Calculations;

public class Rook extends Piece {

    public Rook(String position, boolean color) {
        super(position, color);
    }

    @Override

    public boolean isValidMove(String from, String to) {
        int[] relativePos = Calculations.calcDistance(from, to);
        int x = relativePos[0];
        int y = relativePos[1];

        //forward/backward/right/left/moves
        return (x == 0 || y == 0) && (x != 0 || y != 0);
    }

    public String toString() {
        return "Rook";
    }
}