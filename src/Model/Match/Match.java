package Model.Match;

import Model.Player.GoalKeeper;
import Model.Player.Player;
import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Match extends MatchRegister {

    private static Point dimensionField;
    private PlayersField homePl;

    // Tamanho do campo. (Tenho de perguntar ao Pires se a variável pode ser final ou static dado que é um valor constante.)
    private PlayersField awayPl;
    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.
    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (45, 90) o centro do campo.

    private Point ball_tracker;

    private int time;


    /*------------------------------------------------Constructors----------------------------------------------------*/

    //Tenho de fazer este
    public Match(Team homeTeam, Team awayTeam, Integer[] defaultBot, Integer[] strategyPlayer) {
        //Tenho de fazer este

        super(LocalDate.now(), homeTeam, awayTeam, 0, 0, new ArrayList<>(), new ArrayList<>());

        Random rand = new Random();
        this.ball_pos = rand.nextBoolean();
        this.awayPl = new PlayersField(awayTeam, defaultBot, false);
        this.homePl = new PlayersField(homeTeam, strategyPlayer, true);
        this.ball_tracker = new Point(45, 90);

    }

    public Match(LocalDate gameDate, Team homeTeam, Team awayTeam, int homeGoals, int awayGoals, boolean ball_pos, Point ball_tracker) {
        super.setDate(LocalDate.now());
        super.setScoreHome(homeGoals);
        super.setScoreAway(awayGoals);

        this.ball_pos = ball_pos;

        this.ball_tracker.setX(ball_tracker.getX());
        this.ball_tracker.setY(ball_tracker.getY());
    }

    public Match(Match match) {
        this(match.getDate(), match.getHomeTeam(), match.getAwayTeam(), match.getScoreHome(), match.getScoreAway(), match.ball_pos, match.ball_tracker);
    }

    public static Match game_play(Team homeTeam, Team awayTeam, Integer[] defaultBot, Integer[] strategyPlayer) {

        Match game = new Match(homeTeam, awayTeam, defaultBot, strategyPlayer); // Criar o jogo com os estados base.
        boolean swap_side = game.isBall_pos(); // Variável para o intervalo, é precisa para saber o state drive.
        float time; // Iniciar o contador.

        for (time = 0; time <= 45; time += 0.25) {
            game.confrontation();
            System.out.println(game.getBall_tracker());
            boolean b = game.homePl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            boolean b2 = game.awayPl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            System.out.println(b || b2);
        } // Primeira metade do jogo.

        game.ball_pos = !swap_side;
        game.ball_tracker.setX(60);
        game.ball_tracker.setY(45);

        for (time = 45; time <= 90; time += 0.25) {
            game.confrontation(); // Segunda metade do jogo.
            System.out.println(game.getBall_tracker());
            boolean b = game.homePl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            boolean b2 = game.awayPl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            System.out.println(b || b2);
        }

        return game;

    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public static Point getDimensionField() {
        return dimensionField;
    }

    public static void setDimensionField(Point dimensionField) {
        Match.dimensionField = dimensionField;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public PlayersField getHomePl() {
        return homePl;
    }

    public void setHomePl(PlayersField homePl) {
        this.homePl = homePl;
    }

    public void setHomePl(Team homePl, Integer[] integers) {
        this.homePl = new PlayersField(homePl, integers, true);
    }

    public PlayersField getAwayPl() {
        return awayPl;
    }

    public void setAwayPl(PlayersField awayPl) {
        this.awayPl = awayPl;
    }

    public void setAwayPl(Team awayPl, Integer[] integers) {
        this.awayPl = new PlayersField(awayPl, integers, false);
    }

    public boolean isBall_pos() {
        return ball_pos;
    }

    public void setBall_pos(boolean ball_pos) {
        this.ball_pos = ball_pos;
    }

    public Point getBall_tracker() {
        return ball_tracker;
    }

    public void setBall_tracker(Point ball_tracker) {
        this.ball_tracker = ball_tracker;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */
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

        double x = rand.nextDouble(); // Número que vai servir como o nosso potencial confronto.
        boolean advantage; // Quem ganha o confronto.

        // O cálculo da probabilidade funciona numa escala de 0-1. Dependendo de quem tem a vantagem, a probabilidade muda para que seja mais provável uma equipa ganhar do que outra, mas não torna imo«possível uma vitória contra a probabilidade.

        double probHomeWin = prob(homeSquadSkill, awaySquadSkill); // Função que dá a probabilidade da home Team ganhar range = [0, 1]

        advantage = x < probHomeWin; // Dá o sucesso do confronto à equipa de casa.

       // aftermath(advantage);
        homePl.movePlayers(ball_tracker, ball_pos);
        awayPl.movePlayers(ball_tracker, !ball_pos);
        ball_tracker.addVector(rand.nextDouble()*2 - 1, rand.nextDouble()*2-1);
    }

    public double prob(double homeSquadSkill, double awaySquadSkill) {

        double probability = 0;

        if (homeSquadSkill > awaySquadSkill) {
            if (awaySquadSkill < (homeSquadSkill / 2)) {
                probability = 1 - (awaySquadSkill / homeSquadSkill);
            } else if (awaySquadSkill > (homeSquadSkill / 2)) {
                probability = (awaySquadSkill / homeSquadSkill);
            } else {
                probability = 0.75;
            }
        } else if (homeSquadSkill < awaySquadSkill) {
            if (homeSquadSkill < (awaySquadSkill / 2)) {
                probability = (homeSquadSkill / awaySquadSkill);
            } else if (homeSquadSkill > (awaySquadSkill / 2)) {
                probability = 1 - (homeSquadSkill / awaySquadSkill);
            } else {
                probability = 0.25;
            }
        } else {
            probability = 0.5;
        }

        return probability;
    }

    public void aftermath(boolean vantage) {

        Random rand = new Random();
        Point homeGoal = new Point(0,45);
        Point awayGoal = new Point(120,45);
        double range;
        Comparator<PlayerField> dist = (x,y) -> (int) (x.getPosition().distance(this.ball_tracker) - y.getPosition().distance(this.ball_tracker));

        if (vantage) {

            if (this.ball_pos) {

                range =  awayGoal.distance(this.ball_tracker);

                if (range <= 10) { // Golo

                    if (remate_passe(this.homePl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                        this.ball_pos = false;
                        this.ball_tracker.setX(60);
                        this.ball_tracker.setY(45);
                        super.setScoreAway(super.getScoreAway() + 1);
                        this.homePl.initialPositionAfterGoal(this.homePl.getStrategy(), this.homePl.getPlayersPlaying(), true);
                        this.awayPl.initialPositionAfterGoal(this.awayPl.getStrategy(), this.awayPl.getPlayersPlaying(), false);

                    }

                    else {

                        this.homePl.movePlayers(ball_tracker, ball_pos);
                        this.awayPl.movePlayers(ball_tracker, !ball_pos);

                    }

                } else if (range <= 60) { // Meio Campo para Campo Inimigo

                    if (drible_passe(this.homePl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                        // passe ou segue em frente.;

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                } else if (range <= 90) { // Campo Amigo para Meio Campo

                    if (drible_passe(this.homePl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                        // passe ou segue em frente.

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                } else { // Baliza para Campo Amigo

                    if (remate_passe(this.homePl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                        // passe ou lance em frente.

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                }

            } else {

                this.ball_pos = true;

                this.ball_tracker.setX(this.awayPl.getPlayersPlaying().stream().min(dist).get().getPosition().getX());
                this.ball_tracker.setY(this.awayPl.getPlayersPlaying().stream().min(dist).get().getPosition().getY());

            }

        } else {

            if (this.ball_pos) {

                range = homeGoal.distance(this.ball_tracker);

                if (range <= 10) { // Golo

                    if (remate_passe(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                        this.ball_pos = true;
                        this.ball_tracker.setX(60);
                        this.ball_tracker.setY(45);
                        super.setScoreAway(super.getScoreAway() + 1);
                        this.homePl.initialPositionAfterGoal(this.homePl.getStrategy(), this.homePl.getPlayersPlaying(), true);
                        this.awayPl.initialPositionAfterGoal(this.awayPl.getStrategy(), this.awayPl.getPlayersPlaying(), false);

                    }

                    else {


                        this.homePl.movePlayers(ball_tracker, ball_pos);
                        this.awayPl.movePlayers(ball_tracker, !ball_pos);

                    }

                } else if (range <= 60) { // Meio Campo para Campo Inimigo

                    // passe ou segue em frente

                    if (drible_passe(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                } else if (range <= 90) { // Campo Amigo para Meio Campo

                    // passe ou segue em frente.

                    if (drible_passe(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                } else { // Baliza

                    // passe ou lance em frente.

                    if (remate_passe(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0))) {

                    }

                    else {

                    }

                    this.homePl.movePlayers(ball_tracker, ball_pos);
                    this.awayPl.movePlayers(ball_tracker, !ball_pos);

                }

            } else {

                this.ball_pos = false;

                //this.ball_tracker.setX(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0).getPosition().getX());
                //this.ball_tracker.setX(this.awayPl.getPlayersCloseToTheBall(this.ball_tracker).get(0).getPosition().getY());

                this.ball_tracker.setX(this.awayPl.getPlayersPlaying().stream().min(dist).get().getPosition().getX());
                this.ball_tracker.setY(this.awayPl.getPlayersPlaying().stream().min(dist).get().getPosition().getY());

            }

        }

    }

    public boolean drible_passe (PlayerField ball_owner) {

        Random rand = new Random();

        double y = rand.nextDouble();

        if (ball_owner.getPlayer().getDexterity() > ball_owner.getPlayer().getPassing()) {
            return (y < 0.8);
        } else if (ball_owner.getPlayer().getDexterity() < ball_owner.getPlayer().getPassing()) {
            return (y < 0.2);
        } else {
            return (y < 0.5);
        }

    }

    public boolean remate_passe (PlayerField ball_owner) {

        Random rand = new Random();

        double y = rand.nextDouble();

        if (ball_owner.getPlayer().getFinish() > ball_owner.getPlayer().getPassing()) {
            return (y < 0.8);
        } else if (ball_owner.getPlayer().getFinish() < ball_owner.getPlayer().getPassing()) {
            return (y < 0.2);
        } else {
            return (y < 0.5);
        }

    }

    public void run(int refreshTime) {
        //Simulação com refresh's
        confrontation();
        time += refreshTime;
    }

    public List<Point> getPlayersPositions(boolean home) {
        if (home) return homePl.playersPosition();
        else return awayPl.playersPosition();
    }

    public void setStrategy(Integer[] strategy, boolean home) {
        if (home) homePl.setStrategy(strategy);
        else awayPl.setStrategy(strategy);
    }

}