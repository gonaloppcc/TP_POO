package Model;

import Model.Player.*;
import View.ChosingPlayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Match {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    Cada jogo tem uma certa duração (tima).
    Existem golos de ambas as equipas
    A bola pode estar na posse de uma ou outra equipa e pode estar em 3 zonas distintas do campo.
    **/
    //Equipas completas,
    private Team homeTeam;
    private Team awayTeam;
    //Tempo jogado
    private int time;
    //Pontos
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
    //Classe que tem os jogadores que estão a jogar, válidos para os confrontos
    private PlayersField homeField;
    private PlayersField awayField;


    /*
    Construtores
    O jogo pode ser inicializado a partir de um jogo já existente ou a partir do início, com apenas 2 equipas
    **/


    public Match(List<Player> home, Team homeTeam, List<Player> away, Team awayTeam,
                 int time, int scoreHome, int scoreAway,
                 boolean posse_bola, int pos_ball,
                 PlayersField homeField, PlayersField awayField) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.time = time;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.posse_bola = posse_bola;
        this.pos_ball = pos_ball;
        this.homeField = homeField;
        this.awayField = awayField;
    }

    //Quando o jogo começa do início, só recebe as duas equipas
    //Inicializa o tempo a 0, a bola a meio campo e sorteia a posse de bola de forma aleatória
    //É o jogador (humano) que inicializa os jogadores nas suas posições. Para escolher os jogadores, pode ver todos os jogadores ou apenas os adequados para essa posição.
    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        //Número total de jogadores em campo (jogadores substitutos mais jogadores no campo)
        int total_players = 23;
        ChosingPlayers stdout = new ChosingPlayers();
        PlayersField field = new PlayersField();
        //Para já só na Home
        //Quando nem todos os jogadores da equipa jogam
        //É preciso escolher quais é que jogam e quais é que não
        if (homeTeam.getNumberOfPlayers()>total_players){
            int [] playersChoosen =returnPosPlayers(homeTeam.getPlayers(), homeTeam.getName(), total_players);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : playersChoosen) field.setBenched(homeTeam.getPlayer(i));
        }
        //Vai a equipa toda para o seated
        else field.setBenched(homeTeam.getPlayers());
        //Separar por zonas as escolhas de equipas
        boolean printAll = stdout.printAllQuestion();
        int []strategy = strategy(3 );

        if (printAll) {
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int [] goalKeepersPos = returnPosPlayers(homeTeam.getPlayers(), "GoalKeepers", 1 );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos) field.setGoalKeeper(homeTeam.getPlayer(i));
            //Defesas
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int [] backsPos = returnPosPlayers(homeTeam.getPlayers(), "Back", strategy[0] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos) field.setDefender(homeTeam.getPlayer(i));
            //Médios
            int [] midPos = returnPosPlayers(homeTeam.getPlayers(), "Midfields", strategy[1] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos) field.setMidfield(homeTeam.getPlayer(i));
            //Ataque
            int [] strikPos = returnPosPlayers(homeTeam.getPlayers(), "Strikers", strategy[2] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos) field.setStriker(homeTeam.getPlayer(i));
        }
        else {
            //Filtra os Guarda-Redes
            int [] goalKeepersPos = returnPosPlayers((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof GoalKeeper), "GoalKeepers", 1 );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos) field.setGoalKeeper(((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof GoalKeeper)).get(i));
            //Filtra Defesas
            int [] backsPos = returnPosPlayers((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Defender), "Back", strategy[0] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos) field.setGoalKeeper(((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Defender)).get(i));
            //Defesas
            int [] midPos = returnPosPlayers((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Midfield), "Midfield", strategy[1] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos) field.setMidfield(((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Midfield)).get(i));
            //Atacantes
            int [] strikPos = returnPosPlayers((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Striker), "Striker", strategy[2] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos) field.setMidfield(((List<Player>) homeTeam.getPlayers().stream().
                    filter(item -> item instanceof Striker)).get(i));

        }
        //Inicializa as coisas normais, tipo tempo e posição da bola
        this.standard();
    }
    /*
       returnPosPlayers
Função muito utilizada que liga os módulos View e Controller. O objetivo desta função é saber que jogadores o utilizador escolhe a partir dum conjunto de jogadores.
Recebe a lista de jogadores que serão impressos, uma descrição (String) do que será escrito para o utilizador e quantos jogadores serão escolhidos.
Retorna um array com as posições dos jogadores escolhidos a partir da lista.
**/

    
    //Lista de Jogadores + Descrição para o output + número de valores lidos
    // Retorna o que o jogador escolhe, serão inteiros
    private int [] returnPosPlayers(List<Player> list, String name, int total_players){
        ChosingPlayers stdout = new ChosingPlayers();
        stdout.printPlayers(list, name);
        int [] playersChoosen = new int[total_players];
        stdout.choosePlayers(playersChoosen, total_players);
        return playersChoosen;
   }
    /*
    strategy
    Utilizando o módulo View, descobre que estratégia o utilizador vai utilizar no jogo.
    Esta estratégia consiste na distribuição dos jogadores pelo campo.
**/ 
    private int [] strategy(int total_players){
        ChosingPlayers stdout = new ChosingPlayers();
        stdout.ChooseStrategy(3);
        int [] playersChoosen = new int[total_players];
        stdout.stategyChoose(playersChoosen);
        return playersChoosen;
    }
    
    
    /*
    Funções sobre a lógica do programa
    **/
    
    /*
    changePlayers
    Função que utiliza o módulo View para saber que jogadores serão substítuidos, isto é, uma troca entre um jogador que está no campo por um que está no banco. 
**/ 
    private void changePlayers(PlayersField team){
        ChosingPlayers stdout = new ChosingPlayers();
        //Imprime e recebe que posição do jogador recebe
        int [] posPlayerIn = returnPosPlayers(team.getBenched(), "Wich player gets in?", 1);
        Player in = team.getBenched().get(posPlayerIn[0]);
        //saber onde o jogador que vai sair está
        int pos_absoluteOut = stdout.whereIsPlayer("Que vai sair");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        int [] posPlayerOut = returnPosPlayers(team.getPlayersPosition(pos_absoluteOut),
                "Wich player leaves?", 1);
        //O Jogador que está nessa posição
        Player out = team.getPlayersPosition(posPlayerOut[0]).get(posPlayerIn[0]);
        team.replace(in, out, pos_absoluteOut);
    }
    
    /*
    Função que verifica se o jogo chegou ao fim por tempo.     
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
    private int valuePlayers (List<Player> equipa){
        int total = 0;
        total += equipa.stream().mapToInt(n -> skills(n)).sum();
        return total;
    }
    
/*
    confrontation
    Função que compara as capacidades de duas equipas numa posição do campo. 
    A vitória neste confronto resulta em duas possibilidades: recuperar bola ou avançar com a bola.
    Se já estiver ao pé da baliza adveresária, pode rematar.
    **/
    private int confrontation(int pos_ball){
        Random rd = new Random();
        int homeTeam = valuePlayers(homeField.getPlayersPosition(pos_ball));
        //Para gerar números aleatórios
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        homeTeam *= rd.nextInt((6 - 2) + 1) + 2;
        //3- porque está no outro lado
        int visitorTeam = valuePlayers(homeField.getPlayersPosition(4-pos_ball));
        //Para gerar números aleatórios
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        visitorTeam *= rd.nextInt((6 - 2) + 1) + 2;
        System.out.println("Confronto");
        if (homeTeam == visitorTeam) return 0;
        if (homeTeam > visitorTeam) return 1;
        else return -1;

    }
    
/*
    Função que verifica se é possível rematar por causa da posição da bola.    
    **/
    private boolean rematePossivel(int pos_ball){
        return (pos_ball <= 0 || pos_ball >= 4);
    }
    
/*
    Função que interliga as várias funções desta classe. 
    
    **/
    public void Game (){
        int increment_time = 10;
        if (this.endGame())
        {
            System.out.println("Fim de jogo");
        }
        else {
            int where_ball = getPos_ball();
            if (rematePossivel(where_ball)) remate(where_ball);
            int temp_where_ball = confrontation(where_ball);
            setPos_ball(where_ball + temp_where_ball);
            setTime(getTime()+increment_time);

        }
    }
    //Falta definir
    private void remate (int pos_ball){
        int res = confrontation(pos_ball);
        if (res == -1) golo(true);
        else golo(false);

        return;
    }
    private void golo(boolean team_score){
        if (team_score) setScoreAway(getScoreAway()+1);
        else setScoreHome(getScoreHome()+1);
        setPos_ball(2);
    }


    //Getters e Setters

    public int getScoreHome() {
        return scoreHome;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public void setScoreAway(int scoreAway) {
        this.scoreAway = scoreAway;
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
    
/*
    Um setter que inicializa as váriáveis da classe no início do jogo.
    **/
    private void standard(){
        setTime(0);
        setScoreAway(0);
        setScoreHome(0);
        setPos_ball(2);
        Random rd = new Random();
        setPosse_bola(rd.nextBoolean());
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public PlayersField getHomeField() {
        return homeField;
    }

    public void setHomeField(PlayersField homeField) {
        this.homeField = homeField;
    }

    public PlayersField getAwayField() {
        return awayField;
    }

    public void setAwayField(PlayersField awayField) {
        this.awayField = awayField;
    }
}

