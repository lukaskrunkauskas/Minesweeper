import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;

public class UserInterface extends JFrame {

    int space = 2;

    int neighs = 0;

    public int mousePositionX = -100;
    public int mousePositionY = -100;

    public int boardLength = 16;
    public int boardHeight = 9;

    public int resetButtonX = 10;
    public int resetButtonY = 55;

    public boolean gameRun = true;
    public boolean victory = false;
    public boolean defeat = false;

    public boolean resetter = false;

    public int victoryMessageX = 300;
    public int victoryMessageY = 55;
    public String victoryMessage = "";


    Random rand = new Random();

    int[][] mines = new int[boardLength][boardHeight];
    int[][] neighbours = new int[boardLength][boardHeight];
    boolean[][] uncovered = new boolean[boardLength][boardHeight];
    boolean[][] flagged = new boolean[boardLength][boardHeight];


    public UserInterface() {
        this.setTitle("Minesweeper");
        this.setSize(1286, 829);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);

        Board board = new Board();
        this.setContentPane(board);

        MouseActions mouseActions = new MouseActions();
        this.addMouseMotionListener(mouseActions);

        MouseClick mouseClick = new MouseClick();
        this.addMouseListener(mouseClick);

        buildGame();
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics graphics) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, 1280, 800);
            for (int i = 0; i < boardLength; i++) {
                for (int j = 0; j < boardHeight; j++) {
                    graphics.setColor(Color.gray);


                    if (uncovered[i][j] == true) {
                        graphics.setColor(Color.white);
                    }

                    graphics.fillRect(space + i * 80, space + j * 80 + 80, 80 - 2 * space, 80 - 2 * space);
                    if (uncovered[i][j] == true) {
                        if (mines[i][j] == 1) {
                            graphics.setColor(Color.red);
                            graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
                            graphics.drawString("X", i * 80 + 27, j * 80 + 135);
                        } else {
                            graphics.setColor(Color.black);
                            graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
                            graphics.drawString(Integer.toString(neighbours[i][j]), i * 80 + 27, j * 80 + 135);
                        }
                        if (victory == true) {
                            graphics.setColor(Color.yellow);
                            victoryMessage = "YOU WIN!";
                            graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
                            graphics.drawString(victoryMessage, victoryMessageX, victoryMessageY);
                        }
                        if (defeat == true) {
                            graphics.setColor(Color.red);
                            victoryMessage = "YOU LOSE!";
                            graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
                            graphics.drawString(victoryMessage, victoryMessageX, victoryMessageY);
                        }

                    }
                    if (flagged[i][j] == true) {
                        graphics.setColor(Color.blue);
                        graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
                        graphics.drawString("F", i * 80 + 27, j * 80 + 135);
                    }
                }
            }
            graphics.setColor(Color.green);
            graphics.setFont(new Font("Tahoma", Font.BOLD, 40));
            graphics.drawString("RESET", resetButtonX, resetButtonY);
        }
    }

    public class MouseActions implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mousePositionX = e.getX();
            mousePositionY = e.getY();


        }
    }

    public class MouseClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (boxX() != -1 && boxY() != -1) {
                    uncovered[boxX()][boxY()] = true;
                    flagged[boxX()][boxY()] = false;
                }
                if (resetClick() == true) {
                    resetGame();
                }

            }

            if (SwingUtilities.isRightMouseButton(e)) {
                if (boxX() != -1 && boxY() != -1 && uncovered[boxX()][boxY()] == false) {
                    flagged[boxX()][boxY()] = true;
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
    }

    public void checkIfWon() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (uncovered[i][j] == true && mines[i][j] == 1) {
                    defeat = true;

                }
            }
        }
        if (numberOfBoxUncovered() >= boardHeight * boardLength - numberOfMines()) {
            victory = true;
        }
    }

    public int numberOfMines() {
        int totalMines = 0;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (mines[i][j] == 1) {
                    totalMines++;
                }
            }
        }
        return totalMines;
    }

    public int numberOfBoxUncovered() {
        int totalUncovered = 0;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (uncovered[i][j] == true) {
                    totalUncovered++;
                }
            }
        }
        return totalUncovered;
    }

    public int boxX() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (mousePositionX >= space + i * 80 && mousePositionX < i * 80 + 80 - space && mousePositionY >= space + j * 80 + 106 && mousePositionY < j * 80 + 186 - space) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int boxY() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (mousePositionX >= space + i * 80 && mousePositionX < i * 80 + 80 - space && mousePositionY >= space + j * 80 + 106 && mousePositionY < j * 80 + 186 - space) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean areNeighbours(int i, int j, int m, int n) {
        if (i - m < 2 && i - m > -2 && j - n < 2 && j - n > -2 && mines[m][n] == 1) {
            return true;
        }
        return false;
    }

    public void resetGame() {
        resetter = true;
        gameRun = true;
        victory = false;
        defeat = false;
        buildGame();
        resetter = false;

    }

    public void buildGame() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (rand.nextInt(10) < 2) {
                    mines[i][j] = 1;
                } else {
                    mines[i][j] = 0;
                }
                uncovered[i][j] = false;
                flagged[i][j] = false;

            }
        }
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardHeight; j++) {
                neighs = 0;
                for (int m = 0; m < boardLength; m++) {
                    for (int n = 0; n < boardHeight; n++) {
                        if (!(m == i && n == j)) {
                            if (areNeighbours(i, j, m, n)) {
                                neighs++;
                            }
                        }
                    }
                }
                neighbours[i][j] = neighs;
            }
        }
    }

    public boolean resetClick() {
        if (mousePositionX >= 10 && mousePositionX < 210 && mousePositionY >= 55 && mousePositionY < 100) {
            return true;
        } else {
            return false;
        }
    }
}
