import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class board extends JPanel implements ActionListener {
    private static final int boardWidth = 10;
    private static final int boardHeight = 32;
    private int curX = 0;
    private int curY = 0;
    private shape curShape;
    private boolean isDropped = false;
    private boolean isStarted = false;
    private int numLineRemoved = 0;
    private tetrominoes[] board;
    private Timer timer;
    private JLabel statusBar;

    public int squareWidth() {
        return (int) getSize().getWidth() / boardWidth;
    }
    public int squareHeight() {
        return (int) getSize().getHeight() / boardHeight;
    }

    public board(tetris tetris) {
        setFocusable(true);
        curShape = new shape();
        timer = new Timer(400, this);
        statusBar = tetris.getStatusBar();
        board = new tetrominoes[boardWidth * boardHeight];
        clearBoard();
        addKeyListener(new TetrisAdapter());
    }

    public void clearBoard() {
        for (int i = 0; i < boardWidth * boardHeight; i++) {
            board[i] = tetrominoes.NoShape;
        }
    }

    public tetrominoes shapeAt(int x, int y) {
        return board[y * boardWidth + x];
    }

    public boolean moveShape(shape newShape, int newX, int newY) {
        // check if the shape can move or not
        // if not, return false;
        for (int i = 0; i < 4; i++) {
            int x = newX + newShape.getX(i);
            int y = newY - newShape.getY(i);

            if (x < 0  || x >= boardWidth || y < 0 || y >= boardHeight) {
                return false;
            }
            if (shapeAt(x, y) != tetrominoes.NoShape) {
                return false;
            }
        }

        // checked, the shape can move, update new value and call method repaint().
        // repaint() cannot be overwrite, it means update() -> paint()
        curShape = newShape;
        curX = newX;
        curY = newY;
        repaint();

        return true;
    }

    public void shapeDrop() {
        int newY = curY;
        while (newY > 0) {
            // check shape to make sure that the shape can drop through method moveShape
            if (!moveShape(curShape, curX, newY - 1))
                break;
            //if the shape is movable, drop it.
            newY--;
        }

        shapeDropped();
    }

    public void shapeDropped() {
        for (int i = 0; i < 4; i++) {
            int x = curX + curShape.getX(i);
            int y = curY - curShape.getY(i);
            board[y * boardWidth + x] = curShape.getShape();
        }

        removeLines();

        if (!isDropped) {
            newShape();
        }
    }

    public void removeLines() {
        int numFulledLines = 0;

        //check from bottom to top.
        for (int y = boardHeight - 1; y >= 0; y--) {
            boolean fulledLine = true;

            // check if line is full
            for (int x = 0; x < boardWidth; x++) {
                if (shapeAt(x, y) == tetrominoes.NoShape) {
                    fulledLine = false;
                    break;
                }
            }

            //if full
            if (fulledLine) {
                numFulledLines++;

                for (int id = y; id < boardHeight - 1; id++) {
                    for (int x = 0; x < boardWidth; x++) {
                        board[id * boardWidth + x] = shapeAt(x, id + 1);
                    }
                }
            }

            if (numFulledLines > 0) {
                numLineRemoved = numLineRemoved + numFulledLines;
                statusBar.setText(String.valueOf(numLineRemoved));
                isDropped = true;
                curShape.setShape(tetrominoes.NoShape);
            }
        }
    }

    public void newShape() {
        curShape.setRandomShape();
        curX = boardWidth / 2 + 1;
        curY = boardHeight - 1;

        if (!moveShape(curShape, curX, curY - 1)) {
            curShape.setShape(tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            statusBar.setText("Game Over");
        }
    }

    public void oneLineDown() {
        if (!moveShape(curShape, curX, curY - 1)) shapeDropped();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isDropped) {
            isDropped = false;
            newShape();
        } else {
            oneLineDown();
        }
    }

    public void start() {
        isStarted = true;
        isDropped = false;
        numLineRemoved = 0;
        clearBoard();
        newShape();
        timer.start();
    }

    class TetrisAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            if (!isStarted || curShape.getShape() == tetrominoes.NoShape)
                return;

            int keyCode = ke.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    moveShape(curShape, curX - 1, curY);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveShape(curShape, curX + 1, curY);
                    break;
                case KeyEvent.VK_DOWN:
                    shapeDrop();
                    break;
                case KeyEvent.VK_SPACE:
                    moveShape(curShape.rotate(), curX, curY);
                    break;
                case 'd':
                case 'D':
                    oneLineDown();
                    break;
            }

        }
    }

    private void drawSquare(Graphics g, int x, int y, tetrominoes shape) {
        Color color = shape.color;
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - boardHeight * squareHeight();

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; ++j) {
                tetrominoes shape = shapeAt(j, boardHeight - i - 1);

                if (shape != tetrominoes.NoShape) {
                    drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curShape.getShape() != tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + curShape.getX(i);
                int y = curY - curShape.getY(i);
                drawSquare(g, x * squareWidth(), boardTop + (boardHeight - y - 1) * squareHeight(), curShape.getShape());
            }
        }
    }
}
