package Model.Match;

import Model.Player.*;
import com.sun.nio.sctp.PeerAddressChangeNotification;

import javax.print.attribute.standard.RequestingUserName;
import java.util.List;
import java.util.Random;

class PlayerField {
    private static final double distance = 5;
    private final static Point homeGoal = new Point(0, 45);
    private final static Point awayGoal = new Point(120, 45);
    private Player player;
    private Point position; // current position on the field
    private Position mainPosition; // main position
    private Point beginPosition;
    private boolean lateral;
    private Energy energy; // range [0, 100]
    private int yellowCards;//0 não tem nenhum, 1 tem amarelo, 2 tem vermelho
    // Manipular isto dps para que não ultrapasse certos valores
    private boolean redCard;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public PlayerField(Player playerToSet, boolean home) {
        player = playerToSet;
        mainPosition = bestPosition(playerToSet);
        lateral = mainPosition.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        if (mainPosition != Position.GOALKEEPER) position = new Point(-1, -1);
        else {
            if (home) position = new Point(1, 45);
            else position = new Point(119, 45);
        }

        beginPosition = position.clone();
    }

    public PlayerField(Player playerToSet, float where, boolean home) {
        player = playerToSet;
        mainPosition = bestPosition(playerToSet);
        lateral = mainPosition.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        position = Point.createInitialPosition(where, lateral, mainPosition, home);
        beginPosition = position.clone();
    }

    /**
     * Quando não existem jogadores suficientes numa dada posição
     * Metemos um jogador que não devia estar numa zona nessa zona
     *
     * @param playerToSet
     * @param where
     * @param positionGiven
     */
    public PlayerField(Player playerToSet, float where, Integer positionGiven, boolean home) {
        Position notNatural = numberToPosition(positionGiven);
        player = playerToSet;
        mainPosition = notNatural;
        lateral = notNatural.equals(Position.LATERAL);
        energy = new Energy(100);
        yellowCards = 0;
        redCard = false;
        position = Point.createInitialPosition(where, lateral, notNatural, home);
        beginPosition = position.clone();
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
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getPosition() {
        return position; //.clone();
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

    public Point getBeginPosition() {
        return beginPosition;
    }
    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    public void setBeginPosition(Point beginPosition) {
        this.beginPosition = beginPosition;
    }

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
        return player.globalSkill() * (energy.getEnergy() / 100) * (bestPosition(player).equals(mainPosition) ? 1 : 0.5); // Falta ter em atenção o facto do jogador não estar na sua posição
    }

    // Combines the x and y arguments with the position of the player
    public void move(int x, int y) {
        this.position.addVector(x, y);
    }

    /**
     * Moves the player accordingly with the position of the ball.
     *
     * @param pos_ball
     * @param hasBall
     */
    public void movePlayer(Point pos_ball, boolean hasBall, boolean homeTeam) {
        double distance = (this.energy.getEnergy() / 100) * PlayerField.distance;
        if (hasBall && homeTeam) this.moveBack(pos_ball, distance);
        else if (hasBall) this.moveForward(pos_ball, distance);
        else if (homeTeam && pos_ball.getY() < this.position.getY()) this.moveForward(pos_ball, distance);
        else if (!homeTeam && pos_ball.getY() > this.position.getY()) this.moveBack(pos_ball, distance);
        else ;
        this.energy.decrease();
    }

    private void moveBack(Point pos_ball, double distance) {
        Random r = new Random();
        if (this.position.getY() > 45) this.position.addVector(0, -4);
        this.position.addVector(distance, ((r.nextDouble() * 2) - 1) * 2);
        ;//addVector(-distance / (getPosition().getX() - pos_ball.getX()), distance / (getPosition().getY() - pos_ball.getY()));
    }

    private void moveForward(Point pos_ball, double distance) {
        Random r = new Random();
        if (this.position.getY() > 45) this.position.addVector(0, -4);
        this.position.addVector(-distance, ((r.nextDouble() * 2) - 1) * 2);
        //this.position.addVector(distance / (getPosition().getX() - pos_ball.getX()), distance / (pos_ball.getY() - getPosition().getY()));

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
     * @param in
     * @param out
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