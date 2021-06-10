package Model.Match;

import Model.Player.*;
import com.sun.nio.sctp.PeerAddressChangeNotification;

import java.util.List;

class PlayerField {
    private static final double distance = 5;
    private Player player;
    private Point position; // current position on the field
    private Position mainPosition; // main position
    private boolean lateral;
    private Energy energy; // range [0, 100]
    private int yellowCards;//0 não tem nenhum, 1 tem amarelo, 2 tem vermelho

    // Manipular isto dps para que não ultrapasse certos valores
    private boolean redCard;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public PlayerField(Player playerToSet) {
        this.player = playerToSet;
        mainPosition = bestPosition(playerToSet);
        lateral = mainPosition.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        position = new Point(-1, -1);
    }

    public PlayerField(Player playerToSet, float where) {
        this.player = playerToSet;
        mainPosition = bestPosition(playerToSet);
        lateral = mainPosition.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        position = Point.createInitialPosition(where, lateral, mainPosition);
    }

    /**
     * Quando não existem jogadores suficientes numa dada posição
     * Metemos um jogador que não devia estar numa zona nessa zona
     *
     * @param playerToSet
     * @param where
     * @param positionGiven
     */
    public PlayerField(Player playerToSet, float where, Integer positionGiven) {
        Position notNatural = numberToPosition(positionGiven);
        this.player = playerToSet;
        mainPosition = notNatural;
        lateral = notNatural.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        position = Point.createInitialPosition(where, lateral, notNatural);
    }

    public PlayerField(Player player, Point position, Position mainPosition, boolean lateral, Energy energy, int yellowCards, boolean redCards) {
        this.player = player.clone();
        this.position = position;
        this.mainPosition = mainPosition;
        this.lateral = lateral;
        this.energy = energy.clone();
        this.yellowCards = yellowCards;
        this.redCard = redCards;
    }

    public PlayerField(PlayerField playerField) {
        this(playerField.getPlayer(), playerField.getPosition(), playerField.getMainPosition(), playerField.isLateral(), playerField.getEnergy(), playerField.getYellowCards(), playerField.isRedCard());
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Position getMainPosition() {
        return mainPosition;
    }

    public void setMainPosition(Position mainPosition) {
        this.mainPosition = mainPosition;
    }

    public boolean isLateral() {
        return lateral;
    }

    public void setLateral(boolean lateral) {
        this.lateral = lateral;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public boolean isRedCard() {
        return redCard;
    }

    public void setRedCard(boolean redCard) {
        this.redCard = redCard;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    private Position numberToPosition(Integer x) {
        switch (x) {
            case 0:
                return Position.GOALKEEPER;
            case 1:
                return Position.DEFENDER;
            case 2:
                return Position.MIDFIELD;
            case 3:
                return Position.STRIKER;
            default:
                return Position.LATERAL;
        }
    }

    private Position bestPosition(Player x) {
        if (x instanceof GoalKeeper) return Position.GOALKEEPER;
        if (x instanceof Defender) return Position.DEFENDER;
        if (x instanceof Midfield) return Position.MIDFIELD;
        if (x instanceof Striker) return Position.STRIKER;
        return Position.LATERAL;
    }
    
    public double distance(Point point) {
        return this.position.distance(point);
    }

    public double skill() { // Skill formula
        return player.globalSkill() * (energy.getEnergy() / 100); // Falta ter em atenção o facto do jogador não estar na sua posição
    }

    // Combines the x and y arguments with the position of the player
    public void move(int x, int y) {
        this.position.addVector(x, y);
    }

    /**
     * Moves the player accordingly with the position of the ball.
     * @param pos_ball
     * @param homeHasBall
     */
    public void movePlayer(Point pos_ball, boolean homeHasBall) {
        double distance = (this.energy.getEnergy() / 100) * PlayerField.distance;
        if (homeHasBall) this.moveForward(pos_ball, distance);
        else this.moveBack(pos_ball, distance);
        this.energy.decrease();
    }

    private void moveBack(Point pos_ball, double distance) {
        this.position.addVector(getPosition().getX() - pos_ball.getX(), getPosition().getY() - pos_ball.getY());
        /*
        // y = m*x + b
        double m = getSlope(pos_ball);
        double b = getB(m);

        double x = form(m, b, distance, false);
        double y = m * x + b;

        this.position.addVector(x, y);

         */
    }

    private double form(double m, double b, double distance, boolean forward) {
        double sqrt = Math.min(Math.sqrt(-Math.pow(b, 2) + distance * (Math.pow(m, 2)) + distance), 0);
        if (forward) return (sqrt - b * m) / (Math.pow(m, 2) + 1);
        return (-sqrt - b * m) / (Math.pow(m, 2) + 1);
    }

    private void moveForward(Point pos_ball, double distance) {
        this.position.addVector(pos_ball.getX() - getPosition().getX(), pos_ball.getY() - getPosition().getY());

        // y = m*x + b
        /*
        double m = getSlope(pos_ball);
        double b = getB(m);

        double x = -form(m, b, distance, true);
        double y = m * x + b;

        this.position.addVector(x, y);

         */
    }

    private double getB(double m) {
        return this.position.getY() - (m * this.position.getX());
    }

    private double getSlope(Point pos_ball) {
        double mY = this.position.getY() - pos_ball.getY();
        double mX = this.position.getX() - pos_ball.getX();
        if (mY == 0 && mX == 0) return 0;
        return (mY/mX);
    }

    @Override
    public String toString() {
        return "PlayerField{" +
                "player=" + player +
                "joga a=" + mainPosition +
                ", position=" + position +
                '}';
    }
    
    public PlayerField clone() {
        return new PlayerField(this);
    }
}