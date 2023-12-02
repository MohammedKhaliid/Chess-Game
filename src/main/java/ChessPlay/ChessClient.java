package ChessPlay;

import ChessCore.ChessGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ChessClient {

    public static void runGame(String fileReadName, String FileWriteName) {
        ChessGame game = new ChessGame();
        Scanner fileScan = null;
        PrintWriter filePrint = null;
        String[] move;

        try {
            fileScan = new Scanner(new File(fileReadName));
            filePrint = new PrintWriter(FileWriteName);
            while (fileScan.hasNextLine()) {
                move = fileScan.nextLine().split(",");
                if (move.length == 3) {
                    //with a promotion
                    filePrint.print(game.move(move[0], move[1], move[2].charAt(0)));
                } else {
                    //without a promotion
                    filePrint.print(game.move(move[0], move[1], 'x'));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fileScan != null) {
                fileScan.close();
            }
            if (filePrint != null) {
                filePrint.close();
            }
        }
    }

    public static void main(String[] args) {

        runGame("ChessGame.txt", "Output.txt");

    }
}