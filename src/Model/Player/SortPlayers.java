package Model.Player;

import java.util.Comparator;

//NA net eu vi isto numa classe seprada da original, mas talvez possa estar na Player
//É uma questão de ver, fui buscar aqui:
// https://www.geeksforgeeks.org/collections-sort-java-examples/

public class SortPlayers implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        return p1.globalSkill()-p2.globalSkill();
    }
}
