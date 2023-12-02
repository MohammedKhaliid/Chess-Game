package ChessCore;

public abstract class Piece {
    
    //Instance variables
    private int movesNum;
    private String position;
    private String initPosition;
    private boolean color;
    private boolean isCaptured;
    
    //Constructor
    public Piece(String initposition,String position, boolean color){
        this.movesNum = 0;
        this.position = position;
        this.initPosition = initposition;
        this.color = color;
        this.isCaptured = false;
    }
    //Methods
    public abstract boolean isValidMove(String from, String to);
    public void increaseMovesNum()
    {
        movesNum++;
    }
    
    //Getters
    public int getMovesNum() {
        return movesNum;
    }
    public String getPosition(){
        return position;
    }
    public String getInitPosition(){
        return initPosition;
    }
    public boolean getColor(){
        return color;
    }
    public boolean getIsCaptured(){
        return isCaptured;
    }
    public void setIsCaptured(boolean isCaptured){
        this.isCaptured = isCaptured;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }
}