package Model.Match;

import Model.Player.*;
import Model.Team;
import View.ChosingPlayers;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;


public class Match extends MatchRegister implements Serializable {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    Cada jogo tem uma certa duração (tima).
    Existem golos de ambas as equipas
    A bola pode estar na posse de uma ou outra equipa e pode estar em 3 zonas distintas do campo.
    **/
    // Data do encontro

    // Equipas completas,
//    private Team homeTeam;
//    private Team awayTeam;

    // Jogadores no campo
    private PlayersField playersHome;
    private PlayersField playersAway;

    // Tempo jogado
    private int time;

    // Pontos

    // false -> home tem a bola
    // true -> Visitante tem a bola
    private boolean posse_bola;

    // pos_ball is a point in the field.
    private Point pos_ball;

    //Tamanho do campo
    private int sizeXfield;
    private int sizeYfield;

    // Classe que tem os jogadores que estão a jogar, válidos para os confrontos
    //                  private PlayersField homeField;
    // Vão ser pares   private PlayersField awayField;
    //Pares de subsituições
    //Uma "lista" para cada equipa

    //(Jogador, posição, cartões)  -> -1 estar no banco


    public Match(List<Player> home, Team homeTeam, List<Player> away, Team awayTeam,
                 int time, int scoreHome, int scoreAway,
                 boolean posse_bola, Point pos_ball,
                 PlayersField homeField, PlayersField awayField, int sizeXfield, int sizeYfield) {

        // Falta dar clone de cenas
        super.setHomeTeam(homeTeam);
        super.setAwayTeam(awayTeam);
        super.setScoreAway(scoreAway);
        super.setScoreHome(scoreHome);
        this.time = time;
        this.posse_bola = posse_bola;
        this.pos_ball = pos_ball;
        this.sizeXfield = sizeXfield;
        this.sizeYfield = sizeYfield;
    }

    public Match(String line) {
        //Giro
    }




    /*-----------------------------------------------------Construtores-------------------------------------------------
    O jogo pode ser inicializado a partir de um jogo já existente ou a partir do início, com apenas 2 equipas
    **/

    //Quando o jogo começa do início, só recebe as duas equipas
    //Inicializa o tempo a 0, a bola a meio campo e sorteia a posse de bola de forma aleatória
    //É o jogador (humano) que inicializa os jogadores nas suas posições. Para escolher os jogadores, pode ver todos os jogadores ou apenas os adequados para essa posição.

    /*------------------------------------------Getters and Setters---------------------------------------------------*/

    public LocalDate getDate() {
        return super.getDate();
    }

    public Team getHomeTeam() {
        return super.getHomeTeam();
    }

    public void setHomeTeam(Team homeTeam) {
        super.setHomeTeam(homeTeam);
    }

    public Team getAwayTeam() {
        return super.getAwayTeam();
    }

    public void setAwayTeam(Team awayTeam) {
        super.setAwayTeam(awayTeam);
    }

    public void setDate(LocalDate date) {
        super.setDate(date);
    }

    public PlayersField getPlayersHome() {
        return playersHome;
    }

    public void setPlayersHome(PlayersField playersHome) {
        this.playersHome = playersHome;
    }

    public PlayersField getPlayersAway() {
        return playersAway;
    }

    public void setPlayersAway(PlayersField playersAway) {
        this.playersAway = playersAway;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScoreHome() {
        return super.getScoreHome();
    }

    public void setScoreHome(int scoreHome) {
        super.setScoreHome(scoreHome);
    }

    public int getScoreAway() {
        return super.getScoreAway();
    }

    public void setScoreAway(int scoreAway) {
        super.setScoreAway(scoreAway);
    }


    public boolean isPosse_bola() {
        return posse_bola;
    }

    public void setPosse_bola(boolean posse_bola) {
        this.posse_bola = posse_bola;
    }

    public Point getPos_ball() {
        return pos_ball;
    }

    public void setPos_ball(Point pos_ball) {
        this.pos_ball = pos_ball;
    }


    /*------------------------------------------Other methods---------------------------------------------------------*/

    /**
     * returnPosPlayers
     * Função muito utilizada que liga os módulos View e Controller. O objetivo desta função é saber que jogadores o utilizador escolhe a partir dum conjunto de jogadores.
     * Recebe a lista de jogadores que serão impressos, uma descrição (String) do que será escrito para o utilizador e quantos jogadores serão escolhidos.
     * Retorna um array com as posições dos jogadores escolhidos a partir da lista.
     */

    // Lista de Jogadores + Descrição para o output + número de valores lidos
    // Retorna o que o jogador escolhe, serão inteiros
    private int[] returnPosPlayers(List<Player> list, String name, int total_players) {
        ChosingPlayers stdout = new ChosingPlayers();
        stdout.printPlayers(list, name);
        int[] playersChoosen = new int[total_players];
        stdout.choosePlayers(playersChoosen, total_players);
        return playersChoosen;
    }


    /*------------------------------------------Funções sobre a lógica do programa------------------------------------*/

    /**
     * strategy
     * Utilizando o módulo View, descobre que estratégia o utilizador vai utilizar no jogo.
     * Esta estratégia consiste na distribuição dos jogadores pelo campo.
     */
    private int[] strategy(int total_players) {
        ChosingPlayers stdout = new ChosingPlayers();
        stdout.ChooseStrategy(3);
        int[] playersChoosen = new int[total_players];
        stdout.stategyChoose(playersChoosen);
        return playersChoosen;
    }

    /** changePlayers
     Função que utiliza o módulo View para saber que jogadores serão substítuidos, isto é, uma troca entre um jogador que está no campo por um que está no banco.
     */
    /**
     * confrontation
     * Função que compara as capacidades de duas equipas numa posição do campo.
     * A vitória neste confronto resulta em duas possibilidades: recuperar bola ou avançar com a bola.
     * Se já estiver ao pé da baliza adveresária, pode rematar.
     **/
    private void standard() {
        setTime(0);
        setScoreAway(0);
        setScoreHome(0);
        setPos_ball(new Point(0, 0));
        Random rd = new Random();
        setPosse_bola(rd.nextBoolean());
    }

}
