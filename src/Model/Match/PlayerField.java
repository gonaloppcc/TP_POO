package Model.Match;

import Model.Player.Player;

class PlayerField {
    private Player player;

    private Point position; // current position on the field
    private Position mainPosition; // main position
    private boolean lateral;

    private Energy energy; // range [0, 100]

    private int yellowCards;
    private int redCards;

    // Manipular isto dps para que não ultrapasse certos valores

    private static final double distance = 5;


    // Falta encapsular


    public PlayerField(Player player, Point position, Position mainPosition, boolean lateral, Energy energy, int yellowCards, int redCards) {
        this.player = player.clone();
        this.position = position;
        this.mainPosition = mainPosition;
        this.lateral = lateral;
        this.energy = energy.clone();
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

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

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public PlayerField clone() {
        return new PlayerField(player, position, mainPosition, lateral, energy, yellowCards, redCards);
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public double distance(Point point) {
        return this.position.distance(point);
    }

    public double skill() { // Skill formula
        return player.globalSkill() * (energy.getEnergy()/ 100); // Falta ter em atenção o facto do jogador não estar na sua posição
    }

    // Combines the x and y arguments with the position of the player
    public void move(int x, int y) {
        this.position.addVector(x, y);
    }

    /**
     * Moves the player accordingly with the position of the ball.
     */
    public void movePlayer(Point pos_ball, boolean homeHasBall) {
        double distance = this.energy.getEnergy() * PlayerField.distance;
        if (homeHasBall) this.moveForward(pos_ball, distance);
        else this.moveBack(pos_ball, distance);
        this.energy.decrease();
    }

    private void moveBack(Point pos_ball, double distance) {
        // y = m*x + b
        double m = getSlope(pos_ball);
        double b = getB(m);

        double x = form(m, b, distance);
        double y = m * x + b;

        this.position.addVector(x, y);
    }

    private double form(double m, double b, double distance) {
        return - ((Math.sqrt(-Math.pow(b,2)+ distance * (Math.pow(m, 2)) + distance)) - b * m) / (Math.pow(m, 2) + 1);
    }

    private void moveForward(Point pos_ball, double distance) {
        // y = m*x + b
        double m = getSlope(pos_ball);
        double b = getB(m);

        double x = - form(m, b, distance);
        double y = m * x + b;

        this.position.addVector(x, y);
    }

    private double getB(double m) {
        return this.position.getY() - (m * this.position.getX());
    }

    private double getSlope(Point pos_ball) {
        return (this.position.getY() - pos_ball.getY()) / (this.position.getX() - pos_ball.getX());
    }
}