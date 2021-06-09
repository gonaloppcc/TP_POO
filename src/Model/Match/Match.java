package Model.Match;

import Model.Player.*;
import Model.Team;
import java.awt.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Match implements Serializable {

    // Variável para indicar o dia do jogo.

    private LocalDate gameDate;

    // Variável para contador do jogo (AINDA POR DEFINIR).



    // Equipas que vão jogar. Team possui uma string para o nome da equipa e uma List<Player> para a lista de jogadores.

    private Team homeTeam;
    private Team awayTeam;

    //

    // Variáveis para manter registos dos resultados em termos de golos.

    private int homeGoals;
    private int awayGoals;

    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.

    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (0,0) o centro do campo.

    private Point ball_tracker;

    public Match clone () {

        //Perguntar ao Gonçalo.

        return this;
    }

    public Match (Team homeTeam, Team awayTeam) {

        Random rand = new Random();

        this.gameDate = LocalDate.now();

        this.homeTeam.setName(homeTeam.getName()); this.homeTeam.setPlayers(homeTeam.getPlayers()); // Preciso de ver com o Pires este erro.
        this.awayTeam.setName(awayTeam.getName()); this.awayTeam.setPlayers(awayTeam.getPlayers()); // Preciso de ver com o Pires este erro.

        this.homeGoals = 0;
        this.awayGoals = 0;

        this.ball_pos = rand.nextBoolean();

        this.ball_tracker.setX(0);
        this.ball_tracker.setY(0);
    }

    public Match (LocalDate gameDate, Team homeTeam, Team awayTeam, int homeGoals, int awayGoals, boolean ball_pos, Point ball_tracker) {
        this.gameDate = gameDate;

        this.homeTeam.setName(homeTeam.getName()); this.homeTeam.setPlayers(homeTeam.getPlayers()); // Preciso de ver com o Pires este erro.
        this.awayTeam.setName(awayTeam.getName()); this.awayTeam.setPlayers(awayTeam.getPlayers()); // Preciso de ver com o Pires este erro.

        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;

        this.ball_pos = ball_pos;

        this.ball_tracker.setX(ball_tracker.getX());
        this.ball_tracker.setY(ball_tracker.getY());
    }

    public Match (Match match) {
        this.gameDate = match.gameDate;

        this.homeTeam.setName(match.homeTeam.getName()); this.homeTeam.setPlayers(match.homeTeam.getPlayers()); // Discutir com o Pires a questão da team ser inicializada com ArrayList em vez de List
        this.awayTeam.setName(match.awayTeam.getName()); this.awayTeam.setPlayers(match.awayTeam.getPlayers()); // Discutir com o Pires a questão da team ser inicializada com ArrayList em vez de List

        this.homeGoals = match.homeGoals;
        this.awayGoals = match.awayGoals;

        this.ball_pos = match.ball_pos;

        this.ball_tracker.setX(match.ball_tracker.getX());
        this.ball_tracker.setY(match.ball_tracker.getY());
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