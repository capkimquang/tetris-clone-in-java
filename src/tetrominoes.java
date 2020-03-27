import java.awt.*;

public enum tetrominoes {
    NoShape (new int[][] {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, new Color(0, 0, 0)),
    ZShape (new int[][] {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, new Color(235, 5, 200)),
    SShape (new int[][] {{-1, -1}, {-1, 0}, {0, 0}, {0, 1}}, new Color(222, 213, 198)),
    LineShape (new int[][] {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, new Color(111, 219, 0)),
    TShape (new int[][] {{0, -1}, {0, 0}, {-1, 0}, {0, 1}}, new Color(100, 8, 0)),
    OShape (new int[][] {{-1, -1}, {0, -1}, {0, 0}, {-1, 0}}, new Color(255, 100, 0)),
    LShape (new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}}, new Color(108, 180, 9)),
    ReflectedLShape (new int[][] {{0, -1}, {0, 0}, {0, 1}, {-1, 1}}, new Color(255, 189, 130));

    public int[][] coordinate;
    public Color color;

    private tetrominoes(int[][] ints, Color color) {
        coordinate = ints;
        this.color = color;
    }
}
