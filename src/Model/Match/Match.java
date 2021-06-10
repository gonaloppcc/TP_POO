package Model.Match;

import Model.Player.Player;
import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Match extends MatchRegister implements Serializable {

    private PlayersField homePl;
    private PlayersField awayPl;

    // Tamanho do campo. (Tenho de perguntar ao Pires se a variável pode ser final ou static dado que é um valor constante.)

    private static Point dimensionField;

    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.

    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (0,0) o centro do campo.

    private Point ball_tracker;

    public Match clone () {

        //Perguntar ao Gonçalo.

        return this;
    }
//Tenho de fazer este
    public Match (Team homeTeam, Team awayTeam) {

        super(LocalDate.now(), homeTeam, awayTeam, 0, 0, new ArrayList<>(), new ArrayList<>());

        Random rand = new Random();
        this.ball_pos = rand.nextBoolean();
        awayPl = new PlayersField(awayTeam, new Integer[]{4,3,3,0});
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

        Random rand = new Random();

        // Obtém todos os jogadores perto da bola.

        List<PlayerField> closeByPlayers = getPlayersCloseToTheBall(jogo.ball_tracker);

        // Obtém todos os jogadores perto da bola, por equipa.

        List<PlayerField> homeSquad = closeByPlayers.stream().filter(PlayerField -> homePl.getPlayersPlaying().contains(PlayerField)).collect(Collectors.toList());
        List<PlayerField> awaySquad = closeByPlayers.stream().filter(PlayerField -> awayPl.getPlayersPlaying().contains(PlayerField)).collect(Collectors.toList());

        // Cálculo de probabilidade(utiliza o Random)

        double homeSquadSkill = 0;
        for (PlayerField p : homeSquad) {
            homeSquadSkill += p.skill();
            p.getEnergy().decrease();
        }

        double awaySquadSkill = 0;
        for (PlayerField p : awaySquad) {
            awaySquadSkill += p.skill();
            p.getEnergy().decrease();
        }

        int probResult = rand.nextInt(10); // Número que vai servir como o nosso potencial confronto
        boolean advantage; // Quem ganha o confronto.

        // O cálculo da probabilidade funciona numa escala de 0-9. Dependendo de quem tem a vantagem, a probabilidade muda para que seja mais provável uma equipa ganhar do que outra, mas não torna imo«possível uma vitória contra a probabilidade.

        if (homeSquadSkill < awaySquadSkill) {

        } else if (homeSquadSkill > awaySquadSkill) {

        } else {

        }


        return jogo;
    }

    public void run(){
        //Simulação com refresh's
    }

    public void setStrategy(Integer[] strategy, boolean home){
        if (home) homePl.setStrategy(strategy);
        else awayPl.setStrategy(strategy);
    }

    public PlayersField getHomePl() {
        return homePl;
    }

    public void setHomePl(Team homePl, Integer[] integers) {
        this.homePl = new PlayersField(homePl, integers);
    }

    public void setHomePl(PlayersField homePl) {
        this.homePl = homePl;
    }

    public PlayersField getAwayPl() {
        return awayPl;
    }

    public void setAwayPl(Team awayPl, Integer[] integers) {
        this.awayPl = new PlayersField(awayPl, integers);
    }

    public void setAwayPl(PlayersField awayPl) {
        this.awayPl = awayPl;
    }
}