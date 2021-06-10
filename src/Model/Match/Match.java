package Model.Match;

import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Match extends MatchRegister implements Serializable {


    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.

    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (0,0) o centro do campo.

    private Point ball_tracker;

    public Match clone () {

        //Perguntar ao Gonçalo.

        return this;
    }

    public Match (Team homeTeam, Team awayTeam) {

        super(LocalDate.now(), homeTeam, awayTeam, 0, 0, new ArrayList<>(), new ArrayList<>());

        Random rand = new Random();

        this.ball_pos = rand.nextBoolean();

        this.ball_tracker.setX(0);
        this.ball_tracker.setY(0);
    }

    public Match (LocalDate gameDate, Team homeTeam, Team awayTeam, int homeGoals, int awayGoals, boolean ball_pos, Point ball_tracker) {
        this(homeTeam, awayTeam);

        super.setScoreHome(homeGoals);
        super.setScoreAway(awayGoals);

        this.ball_pos = ball_pos;

        this.ball_tracker.setX(ball_tracker.getX());
        this.ball_tracker.setY(ball_tracker.getY());
    }

    public Match (Match match) {
        this(match.getDate(), match.getHomeTeam(), match.getAwayTeam(), match.getScoreHome(), match.getScoreAway(), match.ball_pos, match.ball_tracker);
    }

    public Match game_play (Team homeTeam, Team awayTeam) {

        Match game = new Match(homeTeam,awayTeam); // Criar o jogo com os estados base.
        boolean swap_side = game.ball_pos; // Variável para o intervalo, é precisa para saber o state drive.
        float time; // Iniciar o contador.

        for (time = 0; time <= 45; time+= 0.25) confrontation(game); // Primeira metade do jogo.

        game.ball_pos = !swap_side;
        game.ball_tracker.setX(0);
        game.ball_tracker.setY(0);

        for (time = 45; time <= 90; time += 0.25) confrontation(game); // Segunda metade do jogo.

        return game;

    }

    public Match confrontation (Match jogo) {
        return jogo;
    }

}