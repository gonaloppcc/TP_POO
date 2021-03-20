package Model;

import Model.Player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Match {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    Cada jogo tem uma certa duração (tima).
    Existem golos de ambas as equipas
    A bola pode estar na posse de uma ou outra equipa e pode estar em 3 zonas distintas do campo.
    **/
    private ArrayList<Player> home;
    private ArrayList<Team> titulares_home;
    private ArrayList<Player> away;
    private ArrayList<Team> titulares_away;
    private int time;
    private int scoreHome;
    private int scoreAway;
    //false -> home tem a bola
    //true -> Visitante tem a bola
    private boolean posse_bola;
    //0 -> Bola está do frente à baliza de casa
    //1 -> Bola está do lado home
    //2 -> Bola está no meio campo
    //3 -> Bola está no lado visitante
    //4 -> Bola está frente à baliza do visitante
    private int pos_ball;

    /*
    Construtores
    O jogo pode ser inicializado a partir de um jogo já existente ou a partir do início, com apenas 2 equipas
    **/
    public Match(ArrayList<Player> home, ArrayList<Player> away) {
        this.home = home;
        this.titulares_home = new ArrayList<>();
        this.away = away;
        this.titulares_home = new ArrayList<>();
        this.time = 0;
        this.scoreAway = 0;
        this.scoreHome = 0;
        this.pos_ball = 2;
        Random rd = new Random();
        System.out.println(rd.nextBoolean());
    }

    public Match(ArrayList<Player> home, ArrayList<Team> titulares_home, ArrayList<Player> away, ArrayList<Team> titulares_away,
                 int time, int scoreHome, int scoreAway, boolean posse_bola, int pos_ball) {
        this.home = home;
        this.titulares_home = titulares_home;
        this.away = away;
        this.titulares_away = titulares_away;
        this.time = time;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.posse_bola = posse_bola;
        this.pos_ball = pos_ball;
    }
    /*
    Funções sobre a lógica do programa
    O jogo pode ser inicializado a partir de um jogo já existente ou a partir do início, com apenas 2 equipas
    **/

    private boolean endGame(){
        return (this.getTime() >= 90);
    }
    //Função inventada para isto dar
    private int skills (Player jogador){
        return 1;
    }
    //IMPORTANTE
    //Falta filtrar os titulares na posição do campo em que a bola está
    private int valuePlayers (ArrayList<Player> equipa, int pos_ball){
        int total = 0;
        total += equipa.stream().mapToInt(n -> skills(n)).sum();
        return total;
    }
    private int confrontation(int pos_ball){
        Random rd = new Random();
        int homeTeam = valuePlayers(this.home, pos_ball);
        //Para gerar números aleatórios
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        homeTeam *= rd.nextInt((6 - 2) + 1) + 2;
    }

    public void Game (){
        if (this.endGame())
        {
            System.out.println("Fim de jogo");
        }
        else {
            int where_ball = getPos_ball();
            int temp_where_ball = confrontation(where_ball):
        }
    }

    //Getters e Setters
    public ArrayList<Player> getHome() {
        return home;
    }

    public void setHome(ArrayList<Player> home) {
        this.home = home;
    }

    public ArrayList<Team> getTitulares_home() {
        return titulares_home;
    }

    public void setTitulares_home(ArrayList<Team> titulares_home) {
        this.titulares_home = titulares_home;
    }

    public ArrayList<Player> getAway() {
        return away;
    }

    public void setAway(ArrayList<Player> away) {
        this.away = away;
    }

    public ArrayList<Team> getTitulares_away() {
        return titulares_away;
    }

    public void setTitulares_away(ArrayList<Team> titulares_away) {
        this.titulares_away = titulares_away;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isPosse_bola() {
        return posse_bola;
    }

    public void setPosse_bola(boolean posse_bola) {
        this.posse_bola = posse_bola;
    }

    public int getPos_ball() {
        return pos_ball;
    }

    public void setPos_ball(int pos_ball) {
        this.pos_ball = pos_ball;
    }
}

