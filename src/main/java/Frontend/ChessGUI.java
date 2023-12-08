package Frontend;

import ChessCore.*;
import ChessCore.ChessGame;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public static final Color RED_COLOR = new Color(255, 0, 0);
    public static final Color GREY_COLOR = new Color(175, 175, 175);

    private final ChessGame game;
    private int currentRow, currentColumn;
    private int promotionRow, promotionColumn;
    private boolean isPromoting;
    private final JFrame chess;
    private JPanel board;
    private Graphics2D graphic;
    private MouseListener mouse;
    private KeyListener undoKey;
    private HistoryManager historyManager;
    private boolean withFlip;
    private String moveState;

    public ChessGUI() {
        game = new ChessGame();
        chess = new JFrame();
        currentRow = currentColumn = -1;
        isPromoting = false;
        historyManager = new HistoryManager();
        withFlip = true;
        moveState = "";
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

    //methods
    private void frameSetup() {
        chess.setSize(600 + 8 * 2, 600 + 8 * 2);
        chess.setLocationRelativeTo(null);
        chess.setUndecorated(true);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 8);
        chess.getRootPane().setBorder(border);
    }

    private void boardSetup() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    graphic.setColor(WHITE_COLOR);
                } else {
                    graphic.setColor(BLACK_COLOR);
                }
                graphic.fillRect((i * SIDE_LENGTH), (j * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH);

                if (getCurrentRow() != -1 && getCurrentColumn() != -1) {

                    Piece selectedPiece;
                    if (game.getTurn() || !withFlip) {
                        selectedPiece = game.getPiece(7 - getCurrentRow(), getCurrentColumn());
                    } else {
                        selectedPiece = game.getPiece(getCurrentRow(), 7 - getCurrentColumn());
                    }

                    if (selectedPiece != null && selectedPiece.getColor() == game.getTurn() && !game.getGameOver()) {

                        graphic.setColor(GREEN_COLOR);
                        graphic.fillRect((getCurrentColumn() * SIDE_LENGTH), (getCurrentRow() * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH);

                        char[][] validMoves = game.allValidMoves(selectedPiece);

                        for (int k = 0; k < 8; k++) {
                            for (int m = 0; m < 8; m++) {
                                if (validMoves[7 - m][k] != 'f') {
                                    if (game.getPiece(7 - m, k) == null) {
                                        graphic.setColor(DARKGREEN_COLOR);

                                        if (game.getTurn() || !withFlip) {
                                            graphic.fillOval((k * SIDE_LENGTH) + 25, (m * SIDE_LENGTH) + 25, 25, 25);
                                        } else {
                                            graphic.fillOval(((7 - k) * SIDE_LENGTH) + 25, ((7 - m) * SIDE_LENGTH) + 25, 25, 25);
                                        }
                                    } else {
                                        Point2D center;
                                        if (game.getTurn() || !withFlip) {
                                            center = new Point2D.Float((k * SIDE_LENGTH) + SIDE_LENGTH / 2, (m * SIDE_LENGTH) + SIDE_LENGTH / 2);
                                        } else {
                                            center = new Point2D.Float(((7 - k) * SIDE_LENGTH) + SIDE_LENGTH / 2, ((7 - m) * SIDE_LENGTH) + SIDE_LENGTH / 2);
                                        }
                                        float radius = SIDE_LENGTH;
                                        Color[] colors = {TRANSPARENT_COLOR, GREEN_COLOR};
                                        float[] dist = {0.5f, 0.6f};
                                        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                                        graphic.setPaint(gradient);
                                        if (game.getTurn() || !withFlip) {
                                            graphic.fill(new Rectangle2D.Double((k * SIDE_LENGTH), (m * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH));
                                        } else {
                                            graphic.fill(new Rectangle2D.Double(((7 - k) * SIDE_LENGTH), ((7 - m) * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH));
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

    private void gameField() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p;
                if (game.getTurn() || !withFlip) {
                    p = game.getPiece(7 - j, i);
                } else {
                    p = game.getPiece(j, 7 - i);
                }
                if (p != null) {
                    Image image;
                    String pieceImage = "";

                    if (p.getColor()) {
                        pieceImage = "White" + p.toString();
                    } else {
                        pieceImage = "Black" + p.toString();
                    }

                    if (p instanceof King) {
                        if (!game.isKingSafe('b') && p.getColor() || !game.isKingSafe('w') && !p.getColor()) {
                            Point2D center = new Point2D.Float((i * SIDE_LENGTH) + SIDE_LENGTH / 2, (j * SIDE_LENGTH) + SIDE_LENGTH / 2);
                            float radius = SIDE_LENGTH / 2 + 10;
                            Color[] colors = {RED_COLOR, TRANSPARENT_COLOR};
                            float[] dist = {0.5f, 1.0f};
                            RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                            graphic.setPaint(gradient);
                            graphic.fill(new Rectangle2D.Double((i * SIDE_LENGTH), (j * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH));
                        }
                    }

                    image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\" + pieceImage + ".png");
                    graphic.drawImage(image, (i * SIDE_LENGTH), (j * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH, board);
                }
            }
        }
        if (getIsPromoting() == true) {
            String[] pieces = new String[]{"Queen", "Knight", "Rook", "Bishop"};
            Image image;
            for (int i = 0; i < 4; i++) {
                if (game.getTurn() || !withFlip) {
                    image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\White" + pieces[i] + ".png");
                } else {
                    image = Toolkit.getDefaultToolkit().getImage("PiecesImages\\Black" + pieces[i] + ".png");
                }
                Point2D center = new Point2D.Float((getPromotionColumn() * SIDE_LENGTH) + SIDE_LENGTH / 2, ((getPromotionRow() + i) * SIDE_LENGTH) + SIDE_LENGTH / 2);
                float radius = SIDE_LENGTH / 2 + 12;
                Color[] colors = {GREY_COLOR, TRANSPARENT_COLOR};
                float[] dist = {0.5f, 1.0f};
                RadialGradientPaint gradient = new RadialGradientPaint(center, radius, dist, colors);
                graphic.setPaint(gradient);
                graphic.fill(new Ellipse2D.Double((getPromotionColumn() * SIDE_LENGTH), ((getPromotionRow() + i) * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH));
                graphic.drawImage(image, (getPromotionColumn() * SIDE_LENGTH), ((getPromotionRow() + i) * SIDE_LENGTH), SIDE_LENGTH, SIDE_LENGTH, board);
            }
        }
    }

    private void panelSetup() {

        board = new JPanel(new GridLayout()) {
            @Override
            public void paint(Graphics graphic1d) {
                graphic = (Graphics2D) graphic1d;

                graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                boardSetup();

                gameField();

            }
        };
    }

    private void mouseActions() {

        mouse = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                Piece selectedPiece;
                char[][] validMoves = BoardInitializer.generateBoard();
                if (getCurrentRow() == -1 || getCurrentColumn() == -1) {
                    setCurrentRow(e.getY() / SIDE_LENGTH);
                    setCurrentColumn(e.getX() / SIDE_LENGTH);
                    if (game.getTurn() || !withFlip) {
                        selectedPiece = game.getPiece(7 - getCurrentRow(), getCurrentColumn());
                    } else {
                        selectedPiece = game.getPiece(getCurrentRow(), 7 - getCurrentColumn());
                    }
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece);
                    }
                    chess.repaint();
                } else {
                    if (game.getTurn() || !withFlip) {
                        selectedPiece = game.getPiece(7 - getCurrentRow(), getCurrentColumn());
                    } else {
                        selectedPiece = game.getPiece(getCurrentRow(), 7 - getCurrentColumn());
                    }
                    if (selectedPiece != null) {
                        validMoves = game.allValidMoves(selectedPiece);
                    }
                    if (selectedPiece instanceof Pawn && getIsPromoting() == false
                            && ((selectedPiece.getColor() && validMoves[7 - e.getY() / SIDE_LENGTH][e.getX() / SIDE_LENGTH] != 'f' && 7 - e.getY() / SIDE_LENGTH == 7)
                            || (!selectedPiece.getColor() && validMoves[e.getY() / SIDE_LENGTH][7 - e.getX() / SIDE_LENGTH] != 'f' && 7 - e.getY() / SIDE_LENGTH == 7))) {
                        setPromotionRow(e.getY() / SIDE_LENGTH);
                        setPromotionColumn(e.getX() / SIDE_LENGTH);
                        setIsPromoting(true);
                        chess.repaint();
                    } else if (getIsPromoting() == false) {
                        if (game.getTurn() || !withFlip) {
                            moveGUI(Calculations.reverseCalcPosition(7 - getCurrentRow(), getCurrentColumn()),
                                    Calculations.reverseCalcPosition(7 - e.getY() / SIDE_LENGTH, e.getX() / SIDE_LENGTH));
                        } else {
                            moveGUI(Calculations.reverseCalcPosition(getCurrentRow(), 7 - getCurrentColumn()),
                                    Calculations.reverseCalcPosition(e.getY() / SIDE_LENGTH, 7 - e.getX() / SIDE_LENGTH));
                        }
                        setCurrentRow(-1);
                        setCurrentColumn(-1);
                        chess.repaint();
                    } else {
                        int r = 7 - e.getY() / SIDE_LENGTH;
                        int c = e.getX() / SIDE_LENGTH;
                        char prom = 'x';
                        if (c == getPromotionColumn() && r == 7) {
                            prom = 'q';
                            if (game.getTurn() || !withFlip) {
                                moveGUI(Calculations.reverseCalcPosition(7 - getCurrentRow(), getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                moveGUI(Calculations.reverseCalcPosition(getCurrentRow(), 7 - getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }

                        } else if (c == getPromotionColumn() && r == 6) {
                            prom = 'k';
                            if (game.getTurn() || !withFlip) {
                                moveGUI(Calculations.reverseCalcPosition(7 - getCurrentRow(), getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                moveGUI(Calculations.reverseCalcPosition(getCurrentRow(), 7 - getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                        } else if (c == getPromotionColumn() && r == 5) {
                            prom = 'r';
                            if (game.getTurn() || !withFlip) {
                                moveGUI(Calculations.reverseCalcPosition(7 - getCurrentRow(), getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                moveGUI(Calculations.reverseCalcPosition(getCurrentRow(), 7 - getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                        } else if (c == getPromotionColumn() && r == 4) {
                            prom = 'b';
                            if (game.getTurn() || !withFlip) {
                                moveGUI(Calculations.reverseCalcPosition(7 - getCurrentRow(), getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7, e.getX() / SIDE_LENGTH), prom);
                            } else {
                                moveGUI(Calculations.reverseCalcPosition(getCurrentRow(), 7 - getCurrentColumn()),
                                        Calculations.reverseCalcPosition(7 - 7, 7 - e.getX() / SIDE_LENGTH), prom);
                            }
                        }
                        setCurrentRow(-1);
                        setCurrentColumn(-1);
                        setIsPromoting(false);
                        chess.repaint();
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
    }

    private void undoKeyAction() {
        undoKey = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                
                System.out.println(e.isControlDown());

                if ((e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Undo key pressed");
                    historyManager.revert(game);
                    chess.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }

    private void moveGUI(String from, String to, char promoteTo) {

        System.out.println(moveState);

        if ((!moveState.equals("Invalid move\n") && simulateMove(from, to))
                || (moveState.equals("Invalid move\n") && simulateMove(from, to))) {
            historyManager.save(game);
            System.out.println("Saved!!");
        }
        moveState = game.move(from, to, promoteTo);
        //handling invalid moves
    }

    private void moveGUI(String from, String to) {
        moveGUI(from, to, 'x');
    }

    public boolean simulateMove(String from, String to) {
        char[][] canMoveToBoard = BoardInitializer.generateBoard();
        int[] fromCoordinates = Calculations.calcPosition(from);
        int fromRow = fromCoordinates[1];
        int fromColumn = fromCoordinates[0];
        int[] toCoordinates = Calculations.calcPosition(to);
        int toRow = toCoordinates[1];
        int toColumn = toCoordinates[0];
        Piece movingPiece = game.getPiece(fromRow, fromColumn);
        Piece capturedPiece = game.getPiece(toRow, toRow);

        if (movingPiece == null || !movingPiece.isValidMove(from, to) || capturedPiece instanceof King || (game.getTurn() && !movingPiece.getColor()) || (!game.getTurn() && movingPiece.getColor())) {
            return false;
        }
        canMoveToBoard = game.allValidMoves(movingPiece);
        if (canMoveToBoard[toRow][toColumn] == 'f') {
            return false;
        }
        return true;
    }

    public void run() {

        frameSetup();

        panelSetup();

        chess.add(board);
        chess.setVisible(true);

        mouseActions();
        undoKeyAction();

        chess.addKeyListener(undoKey);
        chess.getContentPane().addMouseListener(mouse);
    }

}
