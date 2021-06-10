package Controller;

import Model.Match.Match;
import Model.Team;
import View.MatchView;
import View.StatusView;

import java.util.Scanner;

public class MatchController {
    private final Match game;
    private final Integer numberOnField;
    private final Scanner terminal;
    private final MatchView view;

    public MatchController(Team home, Team away, int playerPerTeam) {
        terminal = new Scanner(System.in);
        numberOnField = playerPerTeam;
        view = new MatchView();

        //this.game = new Match(home, away, new Integer[]{1, 3,3,3,1},getStrategy());
        this.game = Match.game_play(home, away, new Integer[]{1, 3, 3, 3, 1}, getStrategy());
        System.out.println("Game score: " + game.getScoreHome() + "-" + game.getScoreAway());
        //view.CampoTodo(game.getPlayersPositions(true), game.getPlayersPositions(false) );
        //game.run();

    }

    private String convertPositionfromNumber(Integer pos) {
        return switch (pos) {
            case 1 -> "Defender";
            case 2 -> "Midfield";
            case 3 -> "Striker";
            default -> "BackWing";
        };

    }

    private Integer[] getStrategy() {
        int total = 1;
        int temp;
        Integer[] res = new Integer[5];
        for (int i = 1; i < res.length && total < numberOnField; i++) {
            view.toZone(convertPositionfromNumber(i), numberOnField - total);
            while (true) {
                temp = terminal.nextInt();
                if (temp + total > numberOnField) StatusView.InvalidLine();
                else {
                    res[i] = temp;
                    total += temp;
                    break;
                }
            }
        }
        //Guarda redes
        res[0] = 1;
        for (int i = 0; i < res.length; i++) if (res[i] == null) res[i] = 0;
        return res;
    }

    public void inicializeAway() {
        game.setStrategy(new Integer[]{1, 3, 3, 3, 1}, false);
        Team away = game.getAwayTeam();
        game.setAwayPl(away, new Integer[]{1, 3, 3, 3, 1});
    }
    //public void inicializeHome(){
    //    Integer[] strategy = getStrategy();
    //    game.setHomePl(game.getHomeTeam(), strategy);
    //}

    public Match getGame() {
        return game;
    }
}
