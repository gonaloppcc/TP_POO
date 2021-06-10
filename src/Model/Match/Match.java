package Model.Match;

import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match extends MatchRegister implements Serializable {

    private static Point dimensionField;
    private PlayersField homePl;

    // Tamanho do campo. (Tenho de perguntar ao Pires se a variável pode ser final ou static dado que é um valor constante.)
    private PlayersField awayPl;

    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.
    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (0,0) o centro do campo.

    private final Point ball_tracker;

    //Tenho de fazer este
    public Match(Team homeTeam, Team awayTeam) {

        super(LocalDate.now(), homeTeam, awayTeam, 0, 0, new ArrayList<>(), new ArrayList<>());

        Random rand = new Random();
        this.ball_pos = rand.nextBoolean();
        awayPl = new PlayersField(awayTeam, new Integer[]{4, 3, 3, 0});
        this.ball_tracker = new Point(0, 0);
    }

    public Match(LocalDate gameDate, Team homeTeam, Team awayTeam, int homeGoals, int awayGoals, boolean ball_pos, Point ball_tracker) {
        this(homeTeam, awayTeam);

        super.setScoreHome(homeGoals);
        super.setScoreAway(awayGoals);

        this.ball_pos = ball_pos;

        this.ball_tracker.setX(ball_tracker.getX());
        this.ball_tracker.setY(ball_tracker.getY());
    }

    public Match(Match match) {
        this(match.getDate(), match.getHomeTeam(), match.getAwayTeam(), match.getScoreHome(), match.getScoreAway(), match.ball_pos, match.ball_tracker);
    }

    public static Match game_play(Team homeTeam, Team awayTeam) {

        Match game = Match.game_play(homeTeam, awayTeam); // Criar o jogo com os estados base.
        boolean swap_side = game.ball_pos; // Variável para o intervalo, é precisa para saber o state drive.
        float time; // Iniciar o contador.

        for (time = 0; time <= 45; time += 0.25) game.confrontation(); // Primeira metade do jogo.

        game.ball_pos = !swap_side;
        game.ball_tracker.setX(0);
        game.ball_tracker.setY(0);

        for (time = 45; time <= 90; time += 0.25) game.confrontation(); // Segunda metade do jogo.

        return game;

    }

    public Match clone() {

        //Perguntar ao Gonçalo.

        return this;
    }

    public void confrontation() {

        Random rand = new Random();

        // Obtém todos os jogadores perto da bola, por equipa.
        List<PlayerField> homeSquad = homePl.getPlayersCloseToTheBall(this.ball_tracker);
        List<PlayerField> awaySquad = awayPl.getPlayersCloseToTheBall(this.ball_tracker);

        // Cálculo de probabilidade(utiliza o Random)

        double homeSquadSkill = 0;
        for (PlayerField p : homeSquad) homeSquadSkill += p.skill();

        double awaySquadSkill = 0;
        for (PlayerField p : awaySquad) awaySquadSkill += p.skill();

        double x = rand.nextDouble(); // Random number

        boolean advantage; // Quem ganha o confronto.

        // O cálculo da probabilidade funciona numa escala de 0-1. Dependendo de quem tem a vantagem, a probabilidade muda para que seja mais provável uma equipa ganhar do que outra, mas não torna imo«possível uma vitória contra a probabilidade.

        double probHomeWin = prob(homeSquadSkill, awaySquadSkill); // Função que dá a probabilidade da home Team ganhar range = [0, 1]

        if (x < probHomeWin) {
            // Home Wins
        } else {
            // Away wins
        }

    }

    private double prob(double homeSquadSkill, double awaySquadSkill) {
        return 0.5;
    }

    public void run() {
        //Simulação com refresh's
    }

    public void setStrategy(Integer[] strategy, boolean home) {
        if (home) homePl.setStrategy(strategy);
        else awayPl.setStrategy(strategy);
    }

    public PlayersField getHomePl() {
        return homePl;
    }

    public void setHomePl(PlayersField homePl) {
        this.homePl = homePl;
    }

    public void setHomePl(Team homePl, Integer[] integers) {
        this.homePl = new PlayersField(homePl, integers);
    }

    public PlayersField getAwayPl() {
        return awayPl;
    }

    public void setAwayPl(PlayersField awayPl) {
        this.awayPl = awayPl;
    }

    public void setAwayPl(Team awayPl, Integer[] integers) {
        this.awayPl = new PlayersField(awayPl, integers);
    }
}