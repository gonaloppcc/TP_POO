package Controller;

import Model.Match.Match;
import Model.Match.PlayerField;
import Model.Match.PlayersField;
import Model.Team;
import View.MatchView;
import View.StatusView;
import View.SwingTimerEx;

import java.util.*;

/**
 * This class controls the Match, and interacts with the user.
 * Match is the simulation of one game, and can ask the user to replace players in the middle of the game.
 */
public class MatchController {
    private final Match game;
    private final Integer numberOnField;
    private final Scanner terminal;
    private final MatchView view;

    /**
     * Main function, that controls the interaction between different class.
     *
     * @param home
     * @param away
     * @param playerPerTeam Number of players each team has.
     */
    public MatchController(Team home, Team away, int playerPerTeam) {
        terminal = new Scanner(System.in);
        numberOnField = playerPerTeam;
        view = new MatchView();
//Core a simulação ou não?
        if (simulatioOrNot()) {
            this.game = new Match(home, away, new Integer[]{1, 3, 3, 3, 1}, getStrategy());
            SwingTimerEx.displayField(game);
            replaceQuestion();

            game.setTime(46);
            game.inicialPositions();

            SwingTimerEx.displayField(game);
        } else {
            this.game = Match.game_play(home, away, new Integer[]{1, 3, 3, 3, 1}, getStrategy());
            System.out.println("Game score: " + game.getScoreHome() + "-" + game.getScoreAway());
        }

        //Sai deste menu, devia retornar o match para o guardar no stats
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

    /*---------------------------- Private Functions ----------------------*/

    private boolean simulatioOrNot() {
        while (true) {
            MatchView.simulateOrNot();
            String option = terminal.nextLine();
            if (option.trim().toLowerCase(Locale.ROOT).equals("s"))
                return true;
            if (option.trim().toLowerCase(Locale.ROOT).equals("n"))
                return false;
            StatusView.InvalidOption();

        }
    }

    private String convertPositionfromNumber(Integer pos) {
        return switch (pos) {
            case 1 -> "Defender";
            case 2 -> "Midfield";
            case 3 -> "Striker";
            default -> "BackWing";
        };
    }

    /**
     * Asks the user to insert a valid strategy, where the total of players chosen is equal to the possible players on the field.
     * The last position gets the remaing players. In this case, the last position are the "Backwing".
     * We only got 5 different positions in the game.
     *
     * @return
     */
    private Integer[] getStrategy() {
        int total = 1;
        int temp;
        Integer[] res = new Integer[5];
        for (int i = 1; i < res.length - 1 && total < numberOnField; i++) {
            view.toZone(convertPositionfromNumber(i), numberOnField - total);
            while (true) {
                try{
                temp = terminal.nextInt();
                if (temp + total > numberOnField) StatusView.InvalidLine();
                else {
                    res[i] = temp;
                    total += temp;
                    break;
                }}
                catch(Exception e){
                    StatusView.InvalidOption();
                }
            }
        }
        res[4] = numberOnField - total;
        //Guarda redes
        res[0] = 1;
        for (int i = 0; i < res.length; i++) if (res[i] == null) res[i] = 0;
        return res;
    }

    //public void inicializeHome(){
    //    Integer[] strategy = getStrategy();
    //    game.setHomePl(game.getHomeTeam(), strategy);
    //}

    private void replaceQuestion() {
        while (true) {
            view.ReplacePlayers();
            String option = terminal.nextLine();
            if (option.trim().toLowerCase(Locale.ROOT).equals("s")) {
                PlayerField out = PlayersField.getPlayer(game.getHomePl().getPlayersPlaying());
                PlayerField in = PlayersField.getPlayer(game.getHomePl().getPlayersBench());
                game.changePlayer(in, out, true);

            }
           else {
               if (option.trim().toLowerCase(Locale.ROOT).equals("n")) {
                    //Só existe uma equipa, joga contra ele próprio
                    return;
                }
                StatusView.InvalidLine();
            }
        }
        }
    }

