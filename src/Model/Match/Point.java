package Model.Match;

import java.util.Random;

public class Point {
    private double x;
    private double y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Point p2) {
        return Math.sqrt((p2.getX() - this.getX()) + (p2.getY() - this.getY()));
    }

    public void addVector(double x, double y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
