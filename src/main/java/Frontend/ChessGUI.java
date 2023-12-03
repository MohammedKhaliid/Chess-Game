/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Frontend;

import ChessCore.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import testing.ChessGameBoard;

/**
 *
 * @author eiada
 */
public class ChessGUI {

    public static final int sideLength = 75;
    private ChessGame game;
    private int currentRow;
    private int currentColumn;

    public ChessGUI() {
        this.game = new ChessGame();
        currentRow = -1;
        currentColumn = -1;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public ChessGame getGame() {
        return this.game;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public static void main(String[] args) {
        ChessGUI chessBoard = new ChessGUI();
        ChessGame game = chessBoard.getGame();

        JFrame chess = new JFrame();
        chess.setSize(600, 600);
        chess.setUndecorated(true);
        chess.setLocationRelativeTo(null);
        Color whiteColor = new Color(255, 228, 178);
        Color blackColor = new Color(151, 119, 84);
        chess.setUndecorated(false);
//        chess.getContentPane().setBackground(Color.red);
        JPanel board;
        board = new JPanel(new GridLayout()) {
            @Override
            public void paint(Graphics graphic1d) {
                Graphics2D graphic = (Graphics2D) graphic1d;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if ((i + j) % 2 == 0) {
                            graphic.setColor(whiteColor);
                        } else {
                            graphic.setColor(blackColor);
                        }
                        graphic.fillRect((i * 75), (j * 75), sideLength, sideLength);
                        if (chessBoard.getCurrentRow() != -1 && chessBoard.getCurrentColumn() != -1) {
                            graphic.setColor(new Color(147,196,125));
                            graphic.fillRect((chessBoard.getCurrentRow() * 75), (chessBoard.getCurrentColumn() * 75), sideLength, sideLength);

                        }
                    }
                }
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Piece p = game.getPiece(7 - j, i);
                        if (p != null) {
                            Image image;
                            String pieceImage = "";
                            if (p instanceof Pawn) {
                                if (p.getColor()) {
                                    pieceImage = "WhitePawn";
                                } else {
                                    pieceImage = "BlackPawn";
                                }
                            } else if (p instanceof Rook) {
                                if (p.getColor()) {
                                    pieceImage = "WhiteRook";
                                } else {
                                    pieceImage = "BlackRook";
                                }
                            } else if (p instanceof Knight) {
                                if (p.getColor()) {
                                    pieceImage = "WhiteKnight";
                                } else {
                                    pieceImage = "BlackKnight";
                                }
                            } else if (p instanceof Bishop) {
                                if (p.getColor()) {
                                    pieceImage = "WhiteBishop";
                                } else {
                                    pieceImage = "BlackBishop";
                                }
                            } else if (p instanceof Queen) {
                                if (p.getColor()) {
                                    pieceImage = "WhiteQueen";
                                } else {
                                    pieceImage = "BlackQueen";
                                }
                            } else if (p instanceof King) {
                                if (p.getColor()) {
                                    pieceImage = "WhiteKing";
                                } else {
                                    pieceImage = "BlackKing";
                                }
                            }

                            image = Toolkit.getDefaultToolkit().getImage("D:\\Programming\\Java\\Java University\\Lab8\\src\\main\\java\\Images\\" + pieceImage + ".png");
                            graphic.drawImage(image, (i * 75), (j * 75), 75, 75, this);

                        }
                    }
                }
            }
        };
        MouseListener mouse = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessBoard.getCurrentRow() == -1 || chessBoard.getCurrentColumn() == -1) {
                    System.out.println(" from ");

                    chessBoard.setCurrentRow(e.getX() / sideLength);
                    chessBoard.setCurrentColumn(e.getY() / sideLength);

                    //all possible moves/////////////////////////////////////////
                    chess.repaint();
                } else {
                    System.out.println(" to ");
                    chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentColumn(), chessBoard.getCurrentRow()), Calculations.reverseCalcPosition(7 - e.getY() / sideLength, e.getX() / sideLength));

                    chessBoard.setCurrentRow(-1);
                    chessBoard.setCurrentColumn(-1);

                    chess.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        chess.getContentPane().addMouseListener(mouse);
        chess.add(board);
        chess.setVisible(true);
    }

    public void moveGUI(String from, String to, char promoteTo) {
        String state = game.move(from, to, promoteTo);
        System.out.println(state);
    }

    public void moveGUI(String from, String to) {
        moveGUI(from, to, 'x');
    }

}
