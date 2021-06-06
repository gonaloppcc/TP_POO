package Model.Match;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(Point p2) {
        return Math.sqrt((p2.getX() - this.getX()) + (p2.getY() - this.getY()));
    }

    public void addVector(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
