import java.awt.*;

public enum tetrominoes {
    NoShape (new int[][] {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, new Color(0, 0, 0)),
    ZShape (new int[][] {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, new Color(0, 0, 0)),
    SShape (new int[][] {{-1, -1}, {-1, 0}, {0, 0}, {0, 1}}, new Color(0, 0, 0)),
    LineShape (new int[][] {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, new Color(0, 0, 0)),
    TShape (new int[][] {{0, -1}, {0, 0}, {-1, 0}, {0, 1}}, new Color(0, 0, 0)),
    OShape (new int[][] {{-1, -1}, {0, -1}, {0, 0}, {-1, 0}}, new Color(0, 0, 0)),
    LShape (new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}}, new Color(0, 0, 0)),
    ReflectedLShape (new int[][] {{0, -1}, {0, 0}, {0, 1}, {-1, 1}}, new Color(0, 0, 0));

    public int[][] coordinate;
    public Color color;

    private tetrominoes(int[][] ints, Color color) {
        coordinate = ints;
        this.color = color;
    }
}
