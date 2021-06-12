package Model.Match;

import Model.Player.*;
import Model.Team;
import View.PlayersFieldView;
import View.StatusView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlayersField {
    private List<PlayerField> playersPlaying;
    private List<PlayerField> playersBench;
    private Integer[] strategy;
    private Point dimensionField;
    private static final double radius = 30;

//static class PlayerComparator (Player p1, Player p2){
//        return p1.globalS
//}


    /**
     * Get the players in a given position, position 0 is the GoalKeeper.
     * A lista é ordenada
     *
     * @param team
     * @param position
     * @return
     */

    public static List<Player> getHowManyInThatPosition(List<Player> team, int position) {
        Comparator<Player> cmpPlayer = new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return p1.globalSkill() - p2.globalSkill();
            }
        };
        if (position == 0) return team.stream().filter(x -> x instanceof GoalKeeper).sorted(cmpPlayer)
                .collect(Collectors.toList());
        if (position == 1) return team.stream().filter(x -> x instanceof Defender).sorted(cmpPlayer)
                .collect(Collectors.toList());
        if (position == 2) return team.stream().filter(x -> x instanceof Midfield).sorted(cmpPlayer)
                .collect(Collectors.toList());
        if (position == 3) return team.stream().filter(x -> x instanceof Striker).sorted(cmpPlayer)
                .collect(Collectors.toList());
        if (position == 4) return team.stream().filter(x -> x instanceof BackWing).sorted(cmpPlayer)
                .collect(Collectors.toList());
        if (position == -1) return team.stream().sorted(cmpPlayer.reversed()).collect(Collectors.toList());
        System.out.println("Erro neste posição: " + position);
        return null;
    }

    public PlayersField(Team bot, Integer[] strategy, boolean home) {

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
                } else {
                    //Adiciona jogadores corretos nessa posição
                    onField.addAll(x);
                    allPlayers.removeAll(x);
                    //Adiciona maus jogadores para encher
                    List<Player> positionNotNatural = getHowManyInThatPosition(allPlayers, -1).subList(0, strategy[i] - x.size());
                    onField.addAll(positionNotNatural);
                    allPlayers.removeAll(positionNotNatural);
                }
            }
        //Jogadores que restam
        for (Player bench : allPlayers) playersBench.add(new PlayerField(bench, home));
        playersPlaying.addAll(initialPosition(strategy, onField, home));
        System.out.println("Uma equipa: ");
        for (PlayerField x : playersPlaying) System.out.println("Um: " + x);
    }

    /**
     * Takes a list of players ready to play, and sorts them in field.
     * This method uses adequate constructors to distribute them.
     *
     * @param strategy
     * @param onField
     * @param home
     * @return
     */
    private static List<PlayerField> initialPosition(Integer[] strategy, List<Player> onField, boolean home) {
        List<PlayerField> initialsPositions = new ArrayList<>();
        initialsPositions.add(new PlayerField(onField.remove(0), home));
        int lidos = 0;
        for (int position = 1; position < strategy.length; position++)
            for (int player = 0; player < strategy[position] && lidos < onField.size(); player++) {
                if (strategy[position] != 0) {
                    initialsPositions.add(new PlayerField(onField.get(lidos), ((float) player) / ((float) strategy[position]), position, home));
                    lidos++;
                }

            }
        return initialsPositions;
    }

    public static List<PlayerField> initialPositionAfterGoal(Integer[] strategy, List<PlayerField> onField, boolean home) {
        List<PlayerField> initialsPositions = new ArrayList<>();
        List<Player> sortGivenList = new ArrayList<>();
        sortGivenList.add(onField.stream().filter(PlayerField::isGoalKeeperInField).collect(Collectors.toList()).get(0).getPlayer());
        sortGivenList.addAll(onField.stream().filter(PlayerField::isDefenderInField).map(PlayerField::getPlayer).collect(Collectors.toList()));
        sortGivenList.addAll(onField.stream().filter(PlayerField::isMiddfieldInField).map(PlayerField::getPlayer).collect(Collectors.toList()));
        sortGivenList.addAll(onField.stream().filter(PlayerField::isStrikerInField).map(PlayerField::getPlayer).collect(Collectors.toList()));
        sortGivenList.addAll(onField.stream().filter(PlayerField::isLateralInField).map(PlayerField::getPlayer).collect(Collectors.toList()));
        return initialPosition(strategy, sortGivenList, home);
    }

    // Construtores
    public PlayersField(List<PlayerField> playersPlaying, List<PlayerField> playersBench) {
        this.playersPlaying = playersPlaying;
        this.playersBench = playersBench;
    }

    public List<PlayerField> getPlayersCloseToTheBall(Point ballPosition) {
        return this.playersPlaying.stream().
                filter(player -> player.distance(ballPosition) <= radius).
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


    public void replace(PlayerField in, PlayerField out) {
        if (playersBench.contains(in) && playersPlaying.contains(out)) {
            PlayerField.copy(in, out);
            playersPlaying.remove(out);
            playersPlaying.add(in);
            playersBench.add(out);
            playersBench.remove(in);
        }

    }

    // Function that moves players in the field
    public void movePlayers(Point pos_ball, boolean homeHasBall, boolean homeTeam) {
        this.playersPlaying.stream().filter(p -> p.getMainPosition() != Position.GOALKEEPER && p.distance(pos_ball) <= 30).
                forEach(playerField -> playerField.movePlayer(pos_ball, homeHasBall, homeTeam));

    }

    public Integer[] getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer[] strategy) {
        this.strategy = strategy;
    }

    public List<Point> playersPosition() {
        List<Point> p = new ArrayList<>(playersPlaying.size());
        for (PlayerField pl : playersPlaying) p.add(pl.getPosition());
        return p;
        //return playersPlaying.stream().map(PlayerField::getPosition).collect(Collectors.toList());
    }

    public static PlayerField getPlayer(List<PlayerField> teamI) {
        Player target;
        Scanner terminal = new Scanner(System.in);
        //Escolhe o jogador que sai
        while (true) {
            PlayersFieldView.printPlayerFields(teamI);
            try{
            Integer playerNum = Integer.parseInt(terminal.nextLine());

            if (teamI.stream().anyMatch(x -> x.getPlayer().getNum() == playerNum))
                return teamI.stream().filter(x -> x.getPlayer().getNum() == playerNum).collect(Collectors.toList()).get(0);
                StatusView.InvalidOption();
            }
            catch(Exception e) {
            StatusView.InvalidOption();
            }

        }
    }
}
