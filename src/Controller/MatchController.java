package Controller;

import Model.Match.Match;
import Model.Team;
import View.MatchView;
import View.StatusView;

import java.util.Scanner;

public class MatchController {
    private Match game;
    private Integer numberOnField;
    private Scanner terminal;
    private MatchView view;

    public MatchController(Team home, Team away, int playerPerTeam) {
        this.game = new Match(home, away);
        terminal = new Scanner(System.in);
        numberOnField = playerPerTeam;
        view = new MatchView();
        inicializeHome();
        inicializeAway();
        game.run();

    }
    private String convertPositionfromNumber(Integer pos){
        switch (pos){
            case 1: return "Defender";
            case 2: return "Midfield";
            case 3: return "Striker";
            default: return "BackWing";
        }

    }
    private Integer[] getStartegy(){
        int total = 0;
        int temp;
        Integer[] res = new Integer[5];
        for (int i = 1; i < 5 && total < numberOnField; i++){
            view.toZone(convertPositionfromNumber(i), numberOnField-total);
            while (true) {
                temp = terminal.nextInt();
                if (temp+total > numberOnField) StatusView.InvalidLine();
                else {
                    res[i] = temp;
                    total += temp;
                    break;
                }
            }
        }
        return res;
    }
    public void inicializeAway (){
        game.setStrategy(new Integer[]{4,3,3,0}, false);
        Team away = game.getAwayTeam();
        game.setAwayPl(away, new Integer[]{4,3,3,0});
    }
    public void inicializeHome(){
        Integer[] strategy = getStartegy();
        game.setAwayPl(game.getHomeTeam(), new Integer[]{4,3,3,0});
    }

    public Match getGame() {
        return game;
    }
}
