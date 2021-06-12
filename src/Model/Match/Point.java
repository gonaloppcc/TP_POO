package Model.Match;

import java.util.Random;


public class Point {
    private double x;
    private double y;

    private static final double xMax = 120;
    private static final double xMin = 0;

    private static final double yMax = 90;
    private static final double yMin = 0;
    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Point(double x, double y) {
        if (x > xMax) this.x = xMax;
        else this.x = Math.max(x, xMin);
        if (y > yMax) this.y = yMax;
        else this.y = Math.max(y, yMin);
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    /**
     * Define em que posição um dado jogador vai estar em função da sua posição.
     * Se for lateral, pode ser esquerdo ou direito.
     * As áreas dividem-se da seguinte forma, com campo (250, 50):
     * Centro: 10-40  Lateral 0-10, 40-50
     * Defesa: 0-80
     * Médio: 80-170
     * Ataque: 170-250
     *
     * @param fraction
     * @param lateral
     * @param pos
     * @param home To know which side the player is
     */
    public static Point createInitialPosition(float fraction, boolean lateral, Position pos, boolean home) {
        Random rnd = new Random();
        int lateralsDimension = 15;
        if (lateral) {
            boolean esq = rnd.nextBoolean();
            int positionInX = convertHomeAway(roundFloat(fraction, (int) xMax), home);
            if (esq) return new Point(positionInX, lateralsDimension/2);
            else return new Point(positionInX, yMax-(lateralsDimension/2));

        } else {      //No x ele pode
            if (pos.equals(Position.DEFENDER)) return new Point(convertHomeAway(15, home), lateralsDimension+ roundFloat(fraction, 50));
            else {
                if (pos.equals(Position.MIDFIELD)) return new Point(convertHomeAway(55, home), lateralsDimension+ roundFloat(fraction, 50));
                else return new Point(convertHomeAway(100, home), lateralsDimension+ roundFloat(fraction, 50));
            }

        }
    }

    private static int convertHomeAway(int pos, boolean home){
        if (home) return pos;
        else return (int) xMax - pos;
    }

    private static int roundFloat(float x, int y) {
        return Math.round(x * y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if (x > xMax) this.x = xMax;
        else this.x = Math.max(x, xMin);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if (y > yMax) this.y = yMax;
        else this.y = Math.max(y, yMin);
    }

    public double distance(Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - this.getX(), 2) + Math.pow(p2.getY() - this.getY(), 2));
    }

    public void addVector(double x, double y) {
        this.setX(this.x + x);
        this.setY(this.y + y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Point clone() {
        return new Point(this);
    }
}
