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

    public static final int SIDE_LENGTH = 75;
    public static final Color WHITE_COLOR = new Color(255, 228, 178);
    public static final Color BLACK_COLOR = new Color(12, 52, 61);
    public static final Color GREEN_COLOR = new Color(88, 117, 75);
    public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
    public static final Color DARKGREEN_COLOR = new Color(104, 130, 93);
    
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

    public static void frameSetup(JFrame chess) {
        chess.setSize(616, 616);
        chess.setLocationRelativeTo(null);
        chess.setUndecorated(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 8);
        chess.getRootPane().setBorder(border);
    }
    
    public static void boardSetup(ChessGUI chessBoard, ChessGame game, Graphics2D graphic){
        
        for (int i = 0; i < 8; i++) 
                {
                    for (int j = 0; j < 8; j++) 
                    {
                        if ((i + j) % 2 == 0) 
                        {
                            graphic.setColor(WHITE_COLOR);
                        } 
                        else 
                        {
                            graphic.setColor(BLACK_COLOR);
                        }
                        graphic.fillRect((i * 75), (j * 75), SIDE_LENGTH, SIDE_LENGTH);

                        if (chessBoard.getCurrentRow() != -1 && chessBoard.getCurrentColumn() != -1) 
                        {

                            Piece selectedPiece;
                            if (game.getTurn()) 
                            {
                                selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());
                            } 
                            else 
                            {
                                selectedPiece = game.getPiece(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn());
                            }

                            if (selectedPiece != null && selectedPiece.getColor() == game.getTurn() && !game.getGameOver()) 
                            {

                                graphic.setColor(GREEN_COLOR);
                                graphic.fillRect((chessBoard.getCurrentColumn() * 75), (chessBoard.getCurrentRow() * 75), SIDE_LENGTH, SIDE_LENGTH);

                                char[][] validMoves = game.allValidMoves(selectedPiece, false);

                                for (int k = 0; k < 8; k++) 
                                {
                                    for (int m = 0; m < 8; m++) 
                                    {
                                        if (validMoves[7 - m][k] != 'f') 
                                        {
                                            if (game.getPiece(7 - m, k) == null) 
                                            {
                                                graphic.setColor(DARKGREEN_COLOR);
                                                
                                                if (game.getTurn()) 
                                                {
                                                    graphic.fillOval((k * 75) + 25, (m * 75) + 25, 25, 25);
                                                } 
                                                else 
                                                {
                                                    graphic.fillOval(((7 - k) * 75) + 25, ((7 - m) * 75) + 25, 25, 25);
                                                }
                                            } 
                                            else 
                                            {
                                                Point2D center;
                                                if (game.getTurn()) 
                                                {
                                                    center = new Point2D.Float((k * 75) + 75 / 2, (m * 75) + 75 / 2);
                                                }
                                                else 
                                                {
                                                    center = new Point2D.Float(((7 - k) * 75) + 75 / 2, ((7 - m) * 75) + 75 / 2);
                                                }
                                                float radius = 75;
                                                Color[] colors = {TRANSPARENT_COLOR, GREEN_COLOR};
                                                float[] dist = {0.5f, 1.0f};
                                                RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                                graphic.setPaint(gradient);
                                                if (game.getTurn()) 
                                                {
                                                    graphic.fill(new Rectangle2D.Double((k * 75), (m * 75), 75, 75));
                                                } 
                                                else 
                                                {
                                                    graphic.fill(new Rectangle2D.Double(((7 - k) * 75), ((7 - m) * 75), 75, 75));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
    }

    public static void gameRun(ChessGUI chessBoard, ChessGame game, Graphics2D graphic, JPanel board){
        for (int i = 0; i < 8; i++) 
                {
                    for (int j = 0; j < 8; j++) 
                    {
                        Piece p;
                        if (game.getTurn()) 
                        {
                            p = game.getPiece(7 - j, i);
                        } 
                        else 
                        {
                            p = game.getPiece(j, 7 - i);
                        }
                        if (p != null) 
                        {
                            Image image;
                            String pieceImage = "";
                            if (p instanceof Pawn)
                            {
                                if (p.getColor()) 
                                {
                                    pieceImage = "WhitePawn";
                                } 
                                else 
                                {
                                    pieceImage = "BlackPawn";
                                }
                            }
                            else if (p instanceof Rook) 
                            {
                                if (p.getColor()) 
                                {
                                    pieceImage = "WhiteRook";
                                } 
                                else 
                                {
                                    pieceImage = "BlackRook";
                                }
                            } 
                            else if (p instanceof Knight) {
                                if (p.getColor()) 
                                {
                                    pieceImage = "WhiteKnight";
                                } 
                                else
                                {
                                    pieceImage = "BlackKnight";
                                }
                            } 
                            else if (p instanceof Bishop) 
                            {
                                if (p.getColor())
                                {
                                    pieceImage = "WhiteBishop";
                                } 
                                else
                                {
                                    pieceImage = "BlackBishop";
                                }
                            } 
                            else if (p instanceof Queen) 
                            {
                                if (p.getColor()) 
                                {
                                    pieceImage = "WhiteQueen";
                                } 
                                else 
                                {
                                    pieceImage = "BlackQueen";
                                }
                            } 
                            else if (p instanceof King) 
                            {
                                if (p.getColor()) 
                                {
                                    pieceImage = "WhiteKing";
                                    if (!game.isKingSafe('b')) 
                                    {
                                        Point2D center = new Point2D.Float((i * 75) + 75 / 2, (j * 75) + 75 / 2);
                                        float radius = 75 / 2 + 10;
                                        Color[] colors = {new Color(255, 0, 0), new Color(0, 0, 0, 0)};
                                        float[] dist = {0.5f, 1.0f};
                                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                        graphic.setPaint(gradient);
                                        graphic.fill(new Rectangle2D.Double((i * 75), (j * 75), 75, 75));
                                    }
                                } 
                                else 
                                {
                                    pieceImage = "BlackKing";
                                    if (!game.isKingSafe('w')) 
                                    {
                                        Point2D center = new Point2D.Float(((i) * 75) + 75 / 2, ((j) * 75) + 75 / 2);
                                        float radius = 75 / 2 + 10;
                                        Color[] colors = {new Color(255, 0, 0), new Color(0, 0, 0, 0)};
                                        float[] dist = {0.5f, 1.0f};
                                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                        graphic.setPaint(gradient);
                                        graphic.fill(new Rectangle2D.Double(((i) * 75), ((j) * 75), 75, 75));
                                    }
                                }

                            }
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\" + pieceImage + ".png");
                            graphic.drawImage(image, (i * 75), (j * 75), 75, 75, board);
                        }
                    }
                }
                if (chessBoard.getIsPromoting() == true) 
                {
                    String[] pieces = new String[]{"Queen", "Knight", "Rook", "Bishop"};
                    Image image;
                    for (int i = 0; i < 4; i++) 
                    {
                        if (game.getTurn()) 
                        {
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\White" + pieces[i] + ".png");
                        } 
                        else
                        {
                            image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\Black" + pieces[i] + ".png");
                        }
                        Point2D center = new Point2D.Float((chessBoard.getPromotionColumn() * 75) + 75 / 2, ((chessBoard.getPromotionRow() + i) * 75) + 75 / 2);
                        float radius = 75 / 2 + 12;
                        Color[] colors = {new Color(175, 175, 175), new Color(0, 0, 0, 0)};
                        float[] dist = {0.5f, 1.0f};
                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                        graphic.setPaint(gradient);
                        graphic.fill(new Ellipse2D.Double((chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() + i) * 75), 75, 75));
                        graphic.drawImage(image, (chessBoard.getPromotionColumn() * 75), ((chessBoard.getPromotionRow() + i) * 75), 75, 75, board);
                    }
                }
            }

    public static JPanel panelSetup(ChessGUI chessBoard, ChessGame game) {
        
        JPanel board;
        board = new JPanel(new GridLayout()) {
            @Override
            public void paint(Graphics graphic1d) {
                Graphics2D graphic = (Graphics2D) graphic1d;
                
                graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                boardSetup(chessBoard, game, graphic);

                gameRun(chessBoard, game, graphic, this);
                
            }
        };
        return board;
    }

    public static void mouseActions(ChessGUI chessBoard, ChessGame game, JFrame chess){
        
        MouseListener mouse = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                Piece selectedPiece;
                char[][] validMoves = ChessGame.generateBoard();
                if (chessBoard.getCurrentRow() == -1 || chessBoard.getCurrentColumn() == -1) {
                    chessBoard.setCurrentRow(e.getY() / SIDE_LENGTH);
                    chessBoard.setCurrentColumn(e.getX() / SIDE_LENGTH);
                    if (game.getTurn()) {
                        selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());
                    } else {
                        selectedPiece = game.getPiece(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn());
                    }
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece, false);
                    }
                    chess.repaint();
                } else {
                    if (game.getTurn()) {
                        selectedPiece = game.getPiece(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn());
                    } else {
                        selectedPiece = game.getPiece(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn());
                    }
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece, false);
                    }
                    if (selectedPiece instanceof Pawn && chessBoard.getIsPromoting() == false
                            && ((selectedPiece.getColor() && validMoves[7 - e.getY() / SIDE_LENGTH][e.getX() / SIDE_LENGTH] != 'f' && 7 - e.getY() / SIDE_LENGTH == 7)
                            || (!selectedPiece.getColor() && validMoves[e.getY() / SIDE_LENGTH][7 - e.getX() / SIDE_LENGTH] != 'f' && 7 - e.getY() / SIDE_LENGTH == 7))) {
                        chessBoard.setPromotionRow(e.getY() / SIDE_LENGTH);
                        chessBoard.setPromotionColumn(e.getX() / SIDE_LENGTH);
                        chessBoard.setIsPromoting(true);
                        chess.repaint();
                    } else if (chessBoard.getIsPromoting() == false) {
                        if (game.getTurn()) {
                            chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                    Calculations.reverseCalcPosition(7 - e.getY() / SIDE_LENGTH, e.getX() / SIDE_LENGTH));
                        } else {
                            chessBoard.moveGUI(Calculations.reverseCalcPosition(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn()),
                                    Calculations.reverseCalcPosition(e.getY() / SIDE_LENGTH, 7 - e.getX() / SIDE_LENGTH));
                        }
                        chessBoard.setCurrentRow(-1);
                        chessBoard.setCurrentColumn(-1);
                        chess.repaint();
                    } else {
                        int r = 7 - e.getY() / SIDE_LENGTH;
                        int c = e.getX() / SIDE_LENGTH;
                        char prom = 'x';
                        if (c == chessBoard.getPromotionColumn() && r == 7) {
                            prom = 'q';
                            if (game.getTurn()) {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }

                            chessBoard.setCurrentRow(-1);
                            chessBoard.setCurrentColumn(-1);
                            chessBoard.setIsPromoting(false);
                            chess.repaint();
                        } else if (c == chessBoard.getPromotionColumn() && r == 6) {
                            prom = 'k';
                            if (game.getTurn()) {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                            chessBoard.setCurrentRow(-1);
                            chessBoard.setCurrentColumn(-1);
                            chessBoard.setIsPromoting(false);
                            chess.repaint();
                        } else if (c == chessBoard.getPromotionColumn() && r == 5) {
                            prom = 'r';
                            if (game.getTurn()) {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                            chessBoard.setCurrentRow(-1);
                            chessBoard.setCurrentColumn(-1);
                            chessBoard.setIsPromoting(false);
                            chess.repaint();
                        } else if (c == chessBoard.getPromotionColumn() && r == 4) {
                            prom = 'b';
                            if (game.getTurn()) {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(7 - chessBoard.getCurrentRow(), chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                chessBoard.moveGUI(Calculations.reverseCalcPosition(chessBoard.getCurrentRow(), 7 - chessBoard.getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                            chessBoard.setCurrentRow(-1);
                            chessBoard.setCurrentColumn(-1);
                            chessBoard.setIsPromoting(false);
                            chess.repaint();
                        } else {
                            chessBoard.setCurrentRow(-1);
                            chessBoard.setCurrentColumn(-1);
                            chessBoard.setIsPromoting(false);
                            chess.repaint();
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
        chess.getContentPane().addMouseListener(mouse);
    }
    
    public static void main(String[] args) {
        ChessGUI chessBoard = new ChessGUI();
        ChessGame game = chessBoard.getGame();

        JFrame chess = new JFrame();
        frameSetup(chess);

        JPanel board = panelSetup(chessBoard, game);

        mouseActions(chessBoard, game, chess);
        
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
