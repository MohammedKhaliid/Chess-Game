package ChessCore;

import java.util.Stack;

public class HistoryManager {
    private Stack<State> states = new Stack<>();
    
    public void save(ChessGame game){
        states.push(game.save());
    }
    
    public void revert(ChessGame game){
        game.revert(states.pop());
    }
}
