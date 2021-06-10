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
    public PlayersField(Team bot, Integer[] strategy) {
        Comparator<Player> cmpPlayer = new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return p1.globalSkill() - p2.globalSkill();

            }
        };
        this.strategy = strategy;
        playersPlaying = new ArrayList<>();
        playersBench = new ArrayList<>();
        List<Player> onField = new ArrayList<>();
        List<Player> onBench = new ArrayList<>();
         onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof GoalKeeper).sorted(cmpPlayer).limit(1).collect(Collectors.toList()));
        if (strategy[0] != 0)onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof Defender).sorted(cmpPlayer).limit(strategy[0]).collect(Collectors.toList()));
        if (strategy[1] != 0)onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof Midfield).sorted(cmpPlayer).limit(strategy[1]).collect(Collectors.toList()));
        if (strategy[2] != 0)onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof Striker).sorted(cmpPlayer).limit(strategy[2]).collect(Collectors.toList()));
        if (strategy[3] != 0)onField.addAll(bot.getPlayers().stream().filter(x -> x instanceof BackWing).sorted(cmpPlayer).limit(strategy[3]).collect(Collectors.toList()));
        //Problema no filter
        onBench.addAll(bot.getPlayers().stream().filter(x -> !onField.contains(x)).collect(Collectors.toList()));
        for (Player bench : onBench) playersBench.add(new PlayerField(bench));
        playersPlaying.add(new PlayerField(onField.remove(0)));
        int lidos = 0;
        for (int position = 0; position < 4; position++)
            for (int player = 0; player < strategy[position] && lidos < onField.size(); player++, lidos++) {
                 if(strategy[position] != 0) playersPlaying.add(new  PlayerField(onField.get(lidos), ((float)player) / ((float)strategy[position])));

            }
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

