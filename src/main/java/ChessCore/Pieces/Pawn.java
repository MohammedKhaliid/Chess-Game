package ChessCore.Pieces;

import ChessCore.Calculations;

public class Pawn extends Piece {

    public Pawn(String position, boolean color) {
        super(position, color);
    }

    @Override
    public boolean isValidMove(String from, String to) {
        int[] relativePos = Calculations.calcDistance(from, to);
        int x = relativePos[0];
        int y = relativePos[1];
        if (getColor() == true) {        //White Pawn

            if (x == 0 && (y == 1 || (y == 2 && getMovesNum() == 0))) //Forward move
            {
                return true;
            }

            if (Math.abs(x) == 1 && y == 1) //Diagnoally forward move
            {
                return true;
            }
        }
        if (getColor() == false) {        //Black Pawn

            if (x == 0 && (y == -1 || (y == -2 && getMovesNum() == 0))) //Forward move
            {
                return true;
            }

            if (Math.abs(x) == 1 && y == -1) //Diagnoally forward move
            {
                return true;
            }
        }
        return false;
    }
    
        public String toString()
    {
        return "Pawn";
    }
//    public static void main(String[] args) {
//        Pawn p = new Pawn("E2", true);
//        
//        System.out.println(p.isValidMove("E2", "G4"));
//    }
}