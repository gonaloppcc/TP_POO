package Model.Match;

import Model.Player.Player;

class PlayerField {
    private Player player;

    private Point position; // current position on the field
    private Position mainPosition; // main position
    private boolean lateral;

    private Energy energy; // range [0, 100]

    private int yellowCards;//0 não tem nenhum, 1 tem amarelo, 2 tem vermelho
    private boolean redCard;
    // Manipular isto dps para que não ultrapasse certos valores


    // Falta encapsular


    public PlayerField(Player player, Point position, Position mainPosition, boolean lateral, Energy energy, int yellowCards, boolean redCards) {
        this.player = player.clone();
        this.position = position;
        this.mainPosition = mainPosition;
        this.lateral = lateral;
        this.energy = energy.clone();
        this.yellowCards = yellowCards;
        this.redCard = redCards;
    }

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

    public PlayerField clone() {
        return new PlayerField(player, position, mainPosition, lateral, energy, yellowCards, redCard);
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
}