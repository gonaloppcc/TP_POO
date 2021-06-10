package Model.Match;
//Coisas para fazer, importante
/*
Nos getter e setter meter clones, se não é apenas o endereço que vai e pode dar problemas
Meter os enum a funcionar para ele poder usar aqui os enum, caso contrário é muito chato
 */

import Model.Player.*;
import Model.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayersField {
    private List<PlayerField> playersPlaying;
    private List<PlayerField> playersBench;
    private Integer[] strategy;
    private Point dimensionField;
    private static final double radius = 5;

//static class PlayerComparator (Player p1, Player p2){
//        return p1.globalS
//}

        public List<Point> playersPosition(){
            return playersPlaying.stream().map(PlayerField::getPosition).collect(Collectors.toList());
        }

    /**
     * Get the players in a given position, position 0 is the GoalKeeper.
     * A lista é ordenada
     * @param team
     * @param position
     * @return
     */

    public static List<Player> getHowManyInThatPosition(List<Player> team, int position){
    Comparator<Player> cmpPlayer = new Comparator<Player>() {
        public int compare(Player p1, Player p2) {
            return p1.globalSkill() - p2.globalSkill();
        }
    };
    if (position == 0) return team.stream().filter(x -> x instanceof GoalKeeper).sorted(cmpPlayer)
            .collect(Collectors.toList());
    if (position == 1 ) return team.stream().filter(x -> x instanceof Defender).sorted(cmpPlayer)
            .collect(Collectors.toList());
    if (position == 2 ) return team.stream().filter(x -> x instanceof Midfield).sorted(cmpPlayer)
            .collect(Collectors.toList());
    if (position == 3 ) return team.stream().filter(x -> x instanceof Striker).sorted(cmpPlayer)
            .collect(Collectors.toList());
    if (position == 4 ) return team.stream().filter(x -> x instanceof BackWing).sorted(cmpPlayer)
            .collect(Collectors.toList());
    if (position == -1) return team.stream().sorted(cmpPlayer.reversed()).collect(Collectors.toList());
    System.out.println("Erro neste posição: "+position);
    return null;
}

    public PlayersField(Team bot, Integer[] strategy) {

        this.strategy = strategy;
        playersPlaying = new ArrayList<>();
        playersBench = new ArrayList<>();
        List<Player> onField = new ArrayList<>();
        List<Player> allPlayers = bot.getPlayers();
         //onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof GoalKeeper).sorted(cmpPlayer).limit(1).collect(Collectors.toList()));
//        onField.add(getHowManyInThatPosition(allPlayers, 0).get(0));
//        allPlayers.remove(onField.get(0));
        for (int i = 0; i < strategy.length; i++)
            if (strategy[i] != 0) {
                List<Player> x = getHowManyInThatPosition(allPlayers, i);
                //Quando não existem jogadores suficientes para aquela posição
                if (x.size() >= strategy[i]) {
                    onField.addAll(x.subList(0, strategy[i]));
                    allPlayers.removeAll(x.subList(0, strategy[i]));
                }
                else {
                    //Adiciona jogadores corretos nessa posição
                    onField.addAll(x);
                    allPlayers.removeAll(x);
                    //Adiciona maus jogadores para encher
                    List<Player> positionNotNatural = getHowManyInThatPosition(allPlayers, -1).subList(0, strategy[i]-x.size());
                    onField.addAll(positionNotNatural);
                    allPlayers.removeAll(positionNotNatural);
                }
            }
        //Jogadores que restam
        for (Player bench : allPlayers) playersBench.add(new PlayerField(bench));
        playersPlaying.add(new PlayerField(onField.remove(0)));
        int lidos = 0;
        for (int position = 1; position < strategy.length; position++)
            for (int player = 0; player < strategy[position] && lidos < onField.size(); player++) {
                 if(strategy[position] != 0){
                     playersPlaying.add(new  PlayerField(onField.get(lidos), ((float)player) / ((float)strategy[position]), position));
                    lidos++;
                 }

            }
        System.out.println("Uma equipa: ");
        for(PlayerField x : playersPlaying) System.out.println("Um: "+x);
    }

    // Construtores
    public PlayersField(List<PlayerField> playersPlaying, List<PlayerField> playersBench) {
        this.playersPlaying = playersPlaying;
        this.playersBench  = playersBench;
    }

    public List<PlayerField> getPlayersCloseToTheBall(Point ballPosition) {
        return this.playersPlaying.stream().
                filter(player -> player.distance(ballPosition) > radius).
                map(PlayerField::clone).
                collect(Collectors.toList());
    }

    public List<PlayerField> getBenched() {
        return playersBench;
    }

    public void setBenched(List<PlayerField> benched) {
        this.playersBench = benched;
    }

    public List<PlayerField> getPlayersPlaying() {
        return playersPlaying;
    }

    public void setPlayersPlaying(List<PlayerField> playersPlaying) {
        this.playersPlaying = playersPlaying;
    }

    public List<PlayerField> getPlayersBench() {
        return playersBench;
    }

    public void setPlayersBench(List<PlayerField> playersBench) {
        this.playersBench = playersBench;
    }

    //Função que faz um substituição
    public void replace(PlayerField in, PlayerField out) {
        if (!playersBench.contains(in) || !playersPlaying.contains(out)) return;
        // ^ Talvez retorna um valor de retorno para dizer se a substituição correu bem

        // Vai do banco para o campo
        playersBench.remove(in);
        playersPlaying.add(in);

        // Do campo vai para o banco
        playersPlaying.remove(out);
        playersBench.add(out);
    }

    public void replace(PlayerField in, PlayerField out, Position inPosition, boolean lateral) {
        this.replace(in, out);

        // Colocar uma nova mainPosition e meter a lateral
        in.setMainPosition(inPosition);
        in.setLateral(lateral);
    }

    // Function that moves players in the field
    public void movePlayers(Point pos_ball, boolean homeHasBall) {
        this.playersPlaying.forEach(playerField -> playerField.movePlayer(pos_ball, homeHasBall));

    }

    public Integer[] getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer[] strategy) {
        this.strategy = strategy;
    }
}

