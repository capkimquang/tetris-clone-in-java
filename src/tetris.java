import javax.swing.*;
import java.awt.*;

public class tetris extends JFrame {

    private JLabel statusBar;

    public tetris() {
        statusBar = new JLabel("0");
        add(statusBar, BorderLayout.NORTH);
        board b = new board(this);
        add(b);
        b.start();
        setSize(400, 1280);
        setTitle("My Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JLabel getStatusBar() {
        return statusBar;
    }

    public static void main(String[] args) {
        tetris myTetris = new tetris();
        myTetris.setLocationRelativeTo(null);
        myTetris.setVisible(true);
    }

}