package Model.Match;

import Model.Player.*;
import com.sun.nio.sctp.PeerAddressChangeNotification;

import javax.print.attribute.standard.RequestingUserName;
import java.util.List;
import java.util.Random;

public class PlayerField {
    private static final double distance = 5;
    private final static Point homeGoal = new Point(0, 45);
    private final static Point awayGoal = new Point(120, 45);
    private Player player;
    private Point position; // current position on the field
    private Position mainPosition; // main position
    private Point beginPosition; // position in which the player begins
    private boolean lateral;
    private Energy energy; // range [0, 100]
    private int yellowCards;
    private boolean redCard;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public PlayerField(Player playerToSet, boolean home) {
        this.player = playerToSet;
        this.mainPosition = bestPosition(playerToSet);
        this.lateral = this.mainPosition.equals(Position.LATERAL);
        this.energy = new Energy(100);
        this.yellowCards = 0;
        this.redCard = false;
        if (this.mainPosition != Position.GOALKEEPER) this.position = new Point(-1, -1);
        else {
            if (home) this.position = new Point(1, 45);
            else this.position = new Point(119, 45);
        }

        this.beginPosition = this.position.clone();
    }

    public PlayerField(Player playerToSet, float where, boolean home) {
        this.player = playerToSet;
        this.mainPosition = bestPosition(playerToSet);
        this.lateral = this.mainPosition.equals(Position.LATERAL);
        this.energy = new Energy(100);
        this.yellowCards = 0;
        this.redCard = false;
        this.position = Point.createInitialPosition(where, this.lateral, this.mainPosition, home);
        this.beginPosition = this.position.clone();
    }

    /**
     * When there aren't enough players for a specific position.
     * We place a player where he shouldn't be in a zone of that zone.
     *
     * @param playerToSet
     * @param where
     * @param positionGiven
     */
    public PlayerField(Player playerToSet, float where, Integer positionGiven, boolean home) {
        Position notNatural = numberToPosition(positionGiven);
        this.player = playerToSet;
        this.mainPosition = notNatural;
        this.lateral = notNatural.equals(Position.LATERAL);
        this.energy = new Energy(100);
        this.yellowCards = 0;
        this.redCard = false;
        this.position = Point.createInitialPosition(where, this.lateral, notNatural, home);
        this.beginPosition = this.position.clone();
    }

    public PlayerField(Player player, Point position, Position mainPosition, Point beginPosition, boolean lateral, Energy energy, int yellowCards, boolean redCards) {
        this.player = player.clone();
        this.position = position;
        this.mainPosition = mainPosition;
        this.beginPosition = beginPosition;
        this.lateral = lateral;
        this.energy = energy.clone();
        this.yellowCards = yellowCards;
        this.redCard = redCards;
    }

    public PlayerField(PlayerField playerField) {
        this(playerField.getPlayer(), playerField.getPosition(), playerField.getMainPosition(), playerField.getBeginPosition(), playerField.isLateral(), playerField.getEnergy(), playerField.getYellowCards(), playerField.isRedCard());
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    private static Position bestPosition(Player x) {
        if (x instanceof GoalKeeper) return Position.GOALKEEPER;
        if (x instanceof Defender) return Position.DEFENDER;
        if (x instanceof Midfield) return Position.MIDFIELD;
        if (x instanceof Striker) return Position.STRIKER;
        return Position.LATERAL;
    }

    public static double getDistance() {
        return distance;
    }

    public Player getPlayer() {
        return this.player.clone();
    }

    public void setPlayer(Player player) {
        this.player = player.clone();
    }

    public Point getPosition() {
        return this.position.clone();
    }

    public void setPosition(Point position) {
        this.position = position.clone();
    }

    public Position getMainPosition() {
        return this.mainPosition;
    }

    public void setMainPosition(Position mainPosition) {
        this.mainPosition = mainPosition;
    }

    public boolean isLateral() {
        return this.lateral;
    }

    public void setLateral(boolean lateral) {
        this.lateral = lateral;
    }

    public Energy getEnergy() {
        return this.energy.clone();
    }

    public void setEnergy(Energy energy) {
        this.energy = energy.clone();
    }

    public int getYellowCards() {
        return this.yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public boolean isRedCard() {
        return this.redCard;
    }

    public void setRedCard(boolean redCard) {
        this.redCard = redCard;
    }

    public Point getBeginPosition() {
        return this.beginPosition.clone();
    }

    public void setBeginPosition(Point beginPosition) {
        this.beginPosition = beginPosition.clone();
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

    public double distance(Point point) {
        return this.position.distance(point);
    }

    public double skill() { // Skill formula
        return this.player.globalSkill() * (this.energy.getEnergy() / 100) * (bestPosition(this.player).equals(this.mainPosition) ? 1 : 0.5);
    }

    /**
     * Combines the x and y arguments with the position of the player
     * @param x x coordinate
     * @param y y coordinate
     */
    public void move(int x, int y) {
        this.position.addVector(x, y);
    }

    /**
     * Moves the player accordingly to the position of the ball.
     *
     * @param pos_ball ball position
     * @param hasBall if the player has the ball
     */
    public void movePlayer(Point pos_ball, boolean hasBall) {
        double distance = (this.energy.getEnergy() / 100) * PlayerField.distance;
        if (hasBall) moveForward(pos_ball, distance);
        else moveBack(pos_ball, distance);

        this.energy.decrease();
    }

    private void moveBack(Point pos_ball, double distance) {
        Random r = new Random();
        if (this.position.getY() > 45) this.position.addVector(0, -4);
        this.position.addVector(-distance, ((r.nextDouble() * 2) - 1) * 1);
    }

    private void moveForward(Point pos_ball, double distance) {
        Random r = new Random();
        if (this.position.getY() > 45) this.position.addVector(0, -4);
        this.position.addVector(distance, ((r.nextDouble() * 2) - 1) * 1);
    }

    @Override
    public String toString() {
        return "PlayerField{" +
                "player=" + this.player +
                "joga a=" + this.mainPosition +
                ", position=" + this.position +
                '}';
    }

    public String stringToChoose() {
        return " PlayerField{" +
                " player=" + this.player.getName() +
                " Número=" + this.player.getNum() +
                " joga a=" + this.mainPosition +
                '}';
    }

    public PlayerField clone() {
        return new PlayerField(this);
    }

    public boolean isGoalKeeperInField() {
        return this.mainPosition.equals(Position.GOALKEEPER);
    }

    public boolean isLateralInField() {
        return this.mainPosition.equals(Position.LATERAL);
    }

    public boolean isDefenderInField() {
        return this.mainPosition.equals(Position.DEFENDER);
    }

    public boolean isMiddfieldInField() {
        return this.mainPosition.equals(Position.MIDFIELD);
    }

    public boolean isStrikerInField() {
        return this.mainPosition.equals(Position.STRIKER);
    }

    /**
     * When a substitution occurs in game.
     * @param in a playerField to enter the game
     * @param out a playerField to leave the game
     */
    public static void copy(PlayerField in, PlayerField out){
        in.player = in.getPlayer();
        in.position = out.beginPosition;
        in.mainPosition = out.mainPosition;
        in.beginPosition = out.beginPosition;
        in.lateral = out.lateral;
        in.energy = new Energy(100);
        in.yellowCards = 0;
    }
}