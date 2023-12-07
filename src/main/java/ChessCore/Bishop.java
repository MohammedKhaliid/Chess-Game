package ChessCore;

public class Bishop extends Piece {

    public Bishop(String position, boolean color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(String from, String to) {
        int[] relativePos = Calculations.calcDistance(from, to);
        int x = relativePos[0];
        int y = relativePos[1];

        if (Math.abs(x) == Math.abs(y) && x != 0) //Diagnoal move
        {
            return true;
        }

        return false;
    }
    
    public String toString()
    {
        return "Bishop";
    }
}