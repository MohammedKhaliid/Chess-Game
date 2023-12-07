package ChessCore;

public class King extends Piece {

    public King(String position, boolean color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(String from, String to) {
        int[] relativePos = Calculations.calcDistance(from, to);
        int x = relativePos[0];
        int y = relativePos[1];

        if ((x == 0 || y == 0) && (x != 0 || y != 0)) //forward/backward/right/left/moves
        {
            if (x == 1 || x == -1 || y == 1 || y == -1 || (getMovesNum() == 0 && (x == 2 || x == 3 || x == -4 || x == -2))) {
                return true;
            }

        }

        if (Math.abs(x) == 1 && Math.abs(y) == 1) //Diagnoally forward move (one step)
        {
            return true;
        }

        return false;
    }

    public String toString() {
        return "King";
    }

}