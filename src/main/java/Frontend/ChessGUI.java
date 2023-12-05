package Frontend;

import ChessCore.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ChessGUI {

    public static final int sideLength = 75;
    private ChessGame game;
    private int currentRow;
    private int currentColumn;
    private int promotionRow;
    private int promotionColumn;
    private boolean isPromoting;

    public ChessGUI() {
        this.game = new ChessGame();
        currentRow = -1;
        currentColumn = -1;
        isPromoting = false;
    }

    public boolean getIsPromoting() {
        return isPromoting;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getPromotionRow() {
        return promotionRow;
    }

    public int getPromotionColumn() {
        return promotionColumn;
    }

    public ChessGame getGame() {
        return this.game;
    }

    public void setIsPromoting(boolean isPromoting) {
        this.isPromoting = isPromoting;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setPromotionColumn(int promotionColumn) {
        this.promotionColumn = promotionColumn;
    }

    public void setPromotionRow(int promotionRow) {
        this.promotionRow = promotionRow;
    }

    public static void main(String[] args) {
        ChessGUI chessBoard = new ChessGUI();
        ChessGame game = chessBoard.getGame();

        JFrame chess = new JFrame();
        chess.setSize(616, 616);
        chess.setLocationRelativeTo(null);
        Color whiteColor = new Color(255, 228, 178);
        Color blackColor = new Color(12,52,61);
        chess.setUndecorated(true);

        JPanel board;

        board = new JPanel(new GridLayout()) {
            @Override
            public void paint(Graphics graphic1d) {
                Graphics2D graphic = (Graphics2D) graphic1d;
                graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if ((i + j) % 2 == 0) {
                            graphic.setColor(whiteColor);
                        } else {
                            graphic.setColor(blackColor);
                        }
                        graphic.fillRect((i * 75), (j * 75), sideLength, sideLength);

                        if (chessBoard.getCurrentRow() != -1 && chessBoard.getCurrentColumn() != -1) {
                            Piece selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());

                            if (selectedPiece != null && selectedPiece.getColor() == game.getTurn()) {

                                graphic.setColor(new Color(88, 117, 75));
                                graphic.fillRect((chessBoard.getCurrentColumn() * 75), (chessBoard.getCurrentRow() * 75), sideLength, sideLength);

                                char[][] validMoves = game.allValidMoves(selectedPiece, false);

                                for (int k = 0; k < 8; k++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (validMoves[7 - m][k] != 'f') {
                                            if (game.getPiece(7 - m, k) == null) {
                                                graphic.setColor(new Color(104, 130, 93));
                                                graphic.fillOval((k * 75) + 25, (m * 75) + 25, 25, 25);
                                            } else {
                                                Point2D center = new Point2D.Float((k * 75) + 75 / 2, (m * 75) + 75 / 2);
                                                float radius = 75;
                                                Color[] colors = {new Color(0, 0, 0, 0), new Color(88, 117, 75)};
                                                float[] dist = {0.5f, 0.6f};
                                                RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                                graphic.setPaint(gradient);
                                                graphic.fill(new Rectangle2D.Double((k * 75), (m * 75), 75, 75));
                                            }
                                        }
                                    }
                                }
                            }
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
                                    if (!game.isKingSafe('b')) {
                                        Point2D center = new Point2D.Float((i * 75) + 75 / 2, (j * 75) + 75 / 2);
                                        float radius = 75 / 2 + 10;
                                        Color[] colors = {new Color(255, 0, 0), new Color(0, 0, 0, 0)};
                                        float[] dist = {0.5f, 1.0f};
                                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                        graphic.setPaint(gradient);
                                        graphic.fill(new Rectangle2D.Double((i * 75), (j * 75), 75, 75));
                                    }
                                } else {
                                    pieceImage = "BlackKing";
                                    if (!game.isKingSafe('w')) {
                                        Point2D center = new Point2D.Float((i * 75) + 75 / 2, (j * 75) + 75 / 2);
                                        float radius = 75 / 2 + 10;
                                        Color[] colors = {new Color(255, 0, 0), new Color(0, 0, 0, 0)};
                                        float[] dist = {0.5f, 1.0f};
                                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                        graphic.setPaint(gradient);
                                        graphic.fill(new Rectangle2D.Double((i * 75), (j * 75), 75, 75));
                                    }
                                }

                            }
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\" + pieceImage + ".png");
                            graphic.drawImage(image, (i * 75), (j * 75), 75, 75, this);

                        }
                    }
                }
                if (chessBoard.getIsPromoting() == true) {
                    String[] pieces = new String[]{"Queen", "Knight", "Rook", "Bishop"};
                    Image image;
                    if (game.getTurn()) {
                        for (int i = 0; i < 4; i++) {
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\White" + pieces[i] + ".png");
                            Point2D center = new Point2D.Float((chessBoard.getPromotionColumn() * 75) + 75 / 2, ((chessBoard.getPromotionRow() + i) * 75) + 75 / 2);
                            float radius = 75 / 2 + 12;
                            Color[] colors = {new Color(175, 175, 175), new Color(0, 0, 0, 0)};
                            float[] dist = {0.5f, 1.0f};
                            RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                            graphic.setPaint(gradient);
                            graphic.fill(new Ellipse2D.Double((chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() + i) * 75), 75, 75));
                            graphic.drawImage(image, (chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() + i) * 75), 75, 75, this);
                        }
                    } else {
                        for (int i = 0; i < 4; i++) {
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\Black" + pieces[i] + ".png");
                            Point2D center = new Point2D.Float((chessBoard.getPromotionColumn() * 75) + 75 / 2, ((chessBoard.getPromotionRow() - i) * 75) + 75 / 2);
                            float radius = 75 / 2 + 12;
                            Color[] colors = {new Color(175, 175, 175), new Color(0, 0, 0, 0)};
                            float[] dist = {0.5f, 1.0f};
                            RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                            graphic.setPaint(gradient);
                            graphic.fill(new Ellipse2D.Double((chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() - i) * 75), 75, 75));
                            graphic.drawImage(image, (chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() - i) * 75), 75, 75, this);
                        }
                    }

                }

            }
        };
        MouseListener mouse = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                Piece selectedPiece;
                char[][] validMoves = ChessGame.generateBoard();
                if (chessBoard.getCurrentRow() == -1 || chessBoard.getCurrentColumn() == -1) {
                    chessBoard.setCurrentRow(e.getY() / sideLength);
                    chessBoard.setCurrentColumn(e.getX() / sideLength);
                    selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece, false);
                    }
                    chess.repaint();
                } else {

                    selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece, false);
                    }
                    if ((selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn())) instanceof Pawn && chessBoard.getIsPromoting() == false
                            && ((selectedPiece.getColor() && validMoves[7 - e.getY() / sideLength][e.getX() / sideLength] != 'f' && 7 - e.getY() / sideLength == 7)
                            || (!selectedPiece.getColor() && validMoves[7 - e.getY() / sideLength][e.getX() / sideLength] != 'f' && 7 - e.getY() / sideLength == 0))) {
                        chessBoard.setPromotionRow(e.getY() / sideLength);
                        chessBoard.setPromotionColumn(e.getX() / sideLength);
                        chessBoard.setIsPromoting(true);
                        chess.repaint();
                    } else if (chessBoard.getIsPromoting() == false) {
                        chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                Calculations.reverseCalcPosition(7 - e.getY() / sideLength, e.getX() / sideLength));
                        chessBoard.setCurrentRow(-1);
                        chessBoard.setCurrentColumn(-1);
                        chess.repaint();
                    } else {
                        int r = 7 - e.getY() / sideLength;
                        int c = e.getX() / sideLength;
                        char prom = 'x';
                        if (selectedPiece.getColor()) {
                            if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 7) {
                                prom = 'q';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 6) {
                                prom = 'k';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 5) {
                                prom = 'r';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 4) {
                                prom = 'b';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else {
                                chess.repaint();
                            }
                        } else {
                            if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 0) {
                                prom = 'q';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(0, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 1) {
                                prom = 'k';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(0, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 2) {
                                prom = 'r';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(0, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else if ((c == chessBoard.getCurrentColumn() || c == chessBoard.getCurrentColumn() + 1 || c == chessBoard.getCurrentColumn() - 1) && r == 3) {
                                prom = 'b';
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(0, e.getX() / sideLength), prom);
                                chessBoard.setCurrentRow(-1);
                                chessBoard.setCurrentColumn(-1);
                                chessBoard.setIsPromoting(false);
                                chess.repaint();
                            } else {
                                chess.repaint();
                            }
                        }

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        
        Border border = BorderFactory.createLineBorder(Color.BLACK, 8);
        chess.getRootPane().setBorder(border);
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
