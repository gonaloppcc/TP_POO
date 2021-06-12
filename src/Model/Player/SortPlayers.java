package Model.Player;

import java.util.Comparator;

public class SortPlayers implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        return p1.globalSkill()-p2.globalSkill();
    }
}
