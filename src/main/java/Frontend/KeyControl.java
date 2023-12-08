package Frontend;

import ChessCore.ChessGame;

//Invoker
public class KeyControl {

    private Command command;

    public void setCommand(Command command){
        this.command = command;
    }
    public void pressKey(ChessGame game){
        command.execute(game);
    }
}
