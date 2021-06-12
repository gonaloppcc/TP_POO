package Model.Match;

import Model.Team;
import java.time.LocalDate;
import java.util.*;

public class Match extends MatchRegister {

    private PlayersField homePl;
    private PlayersField awayPl;

    // Variável que controla quem possui a bola neste momento. True representa homeTeam e False representa awayTeam.
    private boolean ball_pos;

    // Tracker da bola. A posição da bola, sendo o ponto (45, 90) o centro do campo.
    private Point ball_tracker;

    private double time;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Match(Team homeTeam, Team awayTeam, Integer[] defaultBot, Integer[] strategyPlayer) {

        super(LocalDate.now(), homeTeam, awayTeam, 0, 0, new ArrayList<>(), new ArrayList<>());

        Random rand = new Random();
        this.ball_pos = rand.nextBoolean();
        this.awayPl = new PlayersField(awayTeam, defaultBot, false);
        this.homePl = new PlayersField(homeTeam, strategyPlayer, true);
        this.ball_tracker = new Point(60, 45);
        this.time = 0;

    }

    public Match(LocalDate gameDate, Team homeTeam, Team awayTeam, int homeGoals, int awayGoals, PlayersField homePl, PlayersField awayPl, boolean ball_pos, Point ball_tracker) {
        super(gameDate, homeTeam, awayTeam, homeGoals, awayGoals, new ArrayList<>(), new ArrayList<>());

        this.homePl = homePl.clone();
        this.awayPl = awayPl.clone();

        this.ball_pos = ball_pos;
        this.ball_tracker = ball_tracker.clone();
    }

    public Match(Match match) {
        this(match.getDate(), match.getHomeTeam(), match.getAwayTeam(), match.getScoreHome(), match.getScoreAway(), match.homePl, match.awayPl, match.ball_pos, match.ball_tracker);
    }

    /**
     * Função que executa o jogo, verificando que todos os jogadores estão dentro do campo.
     * Esta função também corre pelo tempo e mete a bola no centro do jogo quando o relógio chega aos 45, para indicar mudança de equipa.
     * A função confrontation lida com o confronto entre certos jogadores num certo momento de tempo.
     *
     * @param homeTeam
     * @param awayTeam
     * @param defaultBot
     * @param strategyPlayer
     * @return
     */

    public static Match game_play(Team homeTeam, Team awayTeam, Integer[] defaultBot, Integer[] strategyPlayer) {

        Match game = new Match(homeTeam, awayTeam, defaultBot, strategyPlayer); // Criar o jogo com os estados base.
        boolean swap_side = game.isBall_pos(); // Variável para o intervalo, é precisa para saber o state drive.
        float time; // Iniciar o contador.

        for (time = 0; time <= 45; time += 0.25) {
            game.confrontation();
            boolean b = game.homePl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            boolean b2 = game.awayPl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
          //  System.out.println(b || b2);
        } // Primeira metade do jogo.

        game.ball_pos = !swap_side;
        game.ball_tracker.setX(60);
        game.ball_tracker.setY(45);

        for (time = 45; time <= 90; time += 0.25) {
            game.confrontation(); // Segunda metade do jogo.
         //   System.out.println(game.getBall_tracker());
            boolean b = game.homePl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
            boolean b2 = game.awayPl.getPlayersPlaying().stream().map(PlayerField::getPosition).
                    anyMatch(p -> p.getX() > 120 || p.getX() < 0 || p.getY() > 90 || p.getY() < 0);
           // System.out.println(b || b2);
        }

        return game;

    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public double getTime() {
        return this.time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public PlayersField getHomePl() {
        return this.homePl;
    }

    public void setHomePl(PlayersField homePl) {
        this.homePl = homePl;
    }

    public void setHomePl(Team homePl, Integer[] integers) {
        this.homePl = new PlayersField(homePl, integers, true);
    }

    public PlayersField getAwayPl() {
        return this.awayPl;
    }

    public void setAwayPl(PlayersField awayPl) {
        this.awayPl = awayPl;
    }

    public void setAwayPl(Team awayPl, Integer[] integers) {
        this.awayPl = new PlayersField(awayPl, integers, false);
    }

    public boolean isBall_pos() {
        return this.ball_pos;
    }

    public void setBall_pos(boolean ball_pos) {
        this.ball_pos = ball_pos;
    }

    public Point getBall_tracker() {
        return this.ball_tracker;
    }

    public void setBall_tracker(Point ball_tracker) {
        this.ball_tracker = ball_tracker;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    /**
     * confrontation é uma função que examina o estado actual do campo.
     * Após obter os jogadores que são involvidos nos confrontos de ambas as equipas, são calculadas as global skills de cada sub-equipa.
     * Dependendo desses valores, utiliza a função prob para calcular quem tem a maior probabilidade ganhar o confronto.
     * E aplica essa probabilidade, dando uma "vantagem"  (ou seja, um valor boolean que decide quem ganhou o confronto.) à função aftermath.
     */

    public void confrontation() {

        Random rand = new Random();

        // Obtém todos os jogadores perto da bola, por equipa.
        List<PlayerField> homeSquad = this.homePl.getPlayersCloseToTheBall(this.ball_tracker);
        List<PlayerField> awaySquad = this.awayPl.getPlayersCloseToTheBall(this.ball_tracker);

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

        aftermath(advantage);

    }

    /**
     * A função prob é uma função que calcula a probabilidade de uma equipa ganhar num confronto.
     * Ela irá devolver um valor double que servirá como um limite para a equipa Home ganhar.
     * De forma simplificada, quando o valor x é escolhido de forma aleatória no intervalo [0,1] em confrontation através de um Random(),
     * se o valor for inferior ao limite que prob irá retornar, a equipa Home ganha, mas se for igual, ou mesmo superior, a equipa Away irá ganhar o confronto.
     * @param homeSquadSkill
     * @param awaySquadSkill
     * @return
     */

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

    /**
     * Função que lida com as consequências de um confronto.
     * Através de um valor boolean "vantage", saberemos quem ganhou o confronto (true = Home,false = Away) e iremos depois obter o jogador mais próximo dessa equipa.
     * Com essa informação e com a distância da bola à baliza adversária, as coordenadas da bola são modificadas de forma a fazer prosseguir.
     * Se a distância da bola à baliza adversária for muito pequena, a bola entra e o jogo é reposicionado como no início.
     * @param vantage
     */

    public void aftermath(boolean vantage) {
        Point homeGoal = new Point(0, 45);
        Point awayGoal = new Point(120, 45);

        Comparator<PlayerField> dist = (x, y) -> (int) (x.getPosition().distance(this.ball_tracker) - y.getPosition().distance(this.ball_tracker));

        this.homePl.movePlayers(ball_tracker, ball_pos, true);
        this.awayPl.movePlayers(ball_tracker, ball_pos, false);

        Random rand = new Random();
        this.ball_pos = vantage;
        // Home: + [0, 20] x, [-5, 5] y
        // Home: - [0, 20] x, [-5, 5] y
        if (vantage) {
            Point jog = homePl.getPlayersPlaying().stream().min(dist).get().getPosition();
            this.ball_tracker.setY(jog.getY());
            this.ball_tracker.setX(jog.getX() + 1);
        } //this.ball_tracker.addVector( rand.nextDouble() * 20 , ((rand.nextDouble() * 2) - 1) * 5);
        else {
            Point jog = awayPl.getPlayersPlaying().stream().min(dist).get().getPosition();
            this.ball_tracker.setY(jog.getY());
            this.ball_tracker.setX(jog.getX() - 1);
            //this.ball_tracker.addVector( rand.nextDouble() * -20 , ((rand.nextDouble() * 2) - 1) * 5);
        }

        double rangeAway = awayGoal.distance(this.ball_tracker);
        double rangeHome = homeGoal.distance(this.ball_tracker);

        if (rangeAway <= 10) {
            // Golo
            this.ball_pos = false;
            inicialPositions();
            super.setScoreHome(super.getScoreHome() + 1);
        }

        if (rangeHome <= 10) {
            // Golo
            this.ball_pos = false;
            inicialPositions();
            super.setScoreAway(super.getScoreAway() + 1);
        }

    }

    /**
     * Função que determina se um jogador é mais provável de passar a bola ou fazer um drible, dependendo das suas estatísticas.
     * @param ball_owner
     * @return
     */

    public boolean drible_passe(PlayerField ball_owner) {

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

    /**
     * Função que determina se um jogador é mais provável de rematar a bola ou de passar a bola, dependendo das suas estatísticas.
     * @param ball_owner
     * @return
     */

    public boolean remate_passe(PlayerField ball_owner) {

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

    /**
     * Função que corre uma simulação do jogo.
     * @param refreshTime
     */

    public void run(double refreshTime) {
        //Simulação com refresh's
        confrontation();
        this.time += refreshTime;
    }

    public List<Point> getPlayersPositions(boolean home) {
        if (home) return this.homePl.playersPosition();
        else return this.awayPl.playersPosition();
    }

    public void setStrategy(Integer[] strategy, boolean home) {
        if (home) this.homePl.setStrategy(strategy);
        else this.awayPl.setStrategy(strategy);
    }

    /**
     * Função que irá trocar dois jogadores, um em campo e outro no banco, de lugares.
     * @param in
     * @param out
     */

    public void changePlayer(PlayerField in,PlayerField out,boolean home){
        this.homePl.replace(in, out);
        super.addReplace(in.getPlayer().getNum(), out.getPlayer().getNum(),home);
    }


    public void inicialPositions() {
        // Sets the ball_tracker at midfield
        this.ball_tracker.setX(60);
        this.ball_tracker.setY(45);
        this.homePl.setPlayersPlaying(PlayersField.initialPositionAfterGoal(homePl.getStrategy(),
                this.homePl.getPlayersPlaying(), true));
        this.awayPl.setPlayersPlaying(PlayersField.initialPositionAfterGoal(awayPl.getStrategy(),
                this.awayPl.getPlayersPlaying(), false));
    }

}