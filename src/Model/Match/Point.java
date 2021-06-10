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
     */
    public static Point createInitialPosition(float fraction, boolean lateral, Position pos) {
        Random rnd = new Random();

        if (lateral) {
            boolean esq = rnd.nextBoolean();
            if (esq) return new Point(45, roundFloat(fraction, 250));
            else return new Point(5, roundFloat(fraction, 250));
        } else {      //No x ele pode
            if (pos.equals(Position.DEFENDER)) return new Point(roundFloat(fraction, 80), rnd.nextInt(30) + 10);
            else {
                if (pos.equals(Position.MIDFIELD)) return new Point(roundFloat(fraction, 90) + 80, rnd.nextInt(30) + 10);
                else return new Point(roundFloat(fraction, 80) + 170, rnd.nextInt(30) + 10);
            }

        }
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
        return Math.sqrt((p2.getX() - this.getX()) + (p2.getY() - this.getY()));
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
}
