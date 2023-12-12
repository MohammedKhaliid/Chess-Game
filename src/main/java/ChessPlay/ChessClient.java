package ChessPlay;

import ChessCore.ChessGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


//This file is to test valid moves and printing the board at each move in the console
//It reads from file named "ChessGame.txt" in the main parent directory
//Writes in a file named "Output.txt" in the main parent directory

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