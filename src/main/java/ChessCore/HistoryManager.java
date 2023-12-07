package ChessCore;

import test.TestChessGamee;
import java.util.Stack;

public class HistoryManager {
    private Stack<State> states = new Stack<>();
    
    public void save(TestChessGamee game){
        states.push(game.save());
    }
    
    public void revert(TestChessGamee game){
        if(states.size() == 0)
        {
            return;
        }
        game.revert(states.pop());
        System.out.println("size of stack : "+states.size());

    }
}
