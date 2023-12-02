package ChessCore;

public class Calculations {

    public static int[] calcPosition(String pos) {
        int[] coordinates = new int[2];
        //column
        coordinates[0] = (int) (Character.toUpperCase(pos.charAt(0))) - (int) 'A';
        //row
        coordinates[1] = Character.getNumericValue(pos.charAt(1)) - 1;
        return coordinates;
    }
    
    public static int[] calcDistance(String pos1,String pos2)
    {
        //for measuring horizontal or vertical distance only
        int [] coordinates1 = Calculations.calcPosition(pos1);
        int [] coordinates2 = Calculations.calcPosition(pos2);
        
        int [] result = new int[2];
        
        result[0] = coordinates2[0] - coordinates1[0];
        result[1] = coordinates2[1] - coordinates1[1];
        
        return result;
        
    }
    
    public static String reverseCalcPosition(int row, int column){
        String pos = "";
        
        pos += (char)(column + (int)'A');
        pos += row + 1;
        
        return pos;
    }
}
    
    