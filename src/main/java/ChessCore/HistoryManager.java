package ChessCore;

import java.util.Stack;

public class HistoryManager {
    private Stack<State> states = new Stack<>();
    
    public void save(ChessGame game){
        states.push(game.save());
    }
    
    public void revert(ChessGame game){
        if(states.size() == 0)
        {
            return;
        }
        game.revert(states.pop());
        System.out.println("size of stack : "+states.size());

    }
}
