package ChessCore;

public class Knight extends Piece {

    public Knight(String initposition, String position, boolean color) {
        super(initposition, position, color);
    }

    @Override
    public boolean isValidMove(String from, String to) {
        int[] relativePos = Calculations.calcDistance(from, to);
        int x = Math.abs(relativePos[0]);
        int y = Math.abs(relativePos[1]);

        if ((x == 1 || y == 1) && (x == 2 || y == 2))
        {
            return true;
        }
        return false;
    }
    public String toString()
    {
        return "Knight";
    }
}