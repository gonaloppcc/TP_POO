package Model.Match;

import Model.Player.Player;

class PlayerField{
    private Player player;
    private Point point;

    public PlayerField(Player player, Point point) {
        this.player = player;
        this.point = point;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}