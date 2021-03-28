package Model;

import Model.Player.*;
import View.ChosingPlayers;

import javax.sound.midi.MidiDevice;
import java.util.List;
import java.util.Random;
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
        setHomeTeam(homeTeam);
        setAwayTeam(awayTeam);
        //Número total de jogadores em campo (jogadores substitutos mais jogadores no campo)
        int total_players = 23;
        ChosingPlayers stdout = new ChosingPlayers();
        PlayersField fieldHome = new PlayersField();
        //Para já só na Home
        //Quando nem todos os jogadores da equipa jogam
        //É preciso escolher quais é que jogam e quais é que não
        if (homeTeam.getNumberOfPlayers()>total_players){
            int [] playersChoosen =returnPosPlayers(homeTeam.getPlayers(),"Choose the players: "+ homeTeam.getName(), total_players);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : playersChoosen) fieldHome.setBenched(homeTeam.getPlayer(i));
        }
        //Vai a equipa toda para o seated
        else fieldHome.setBenched(homeTeam.getPlayers());
        //Separar por zonas as escolhas de equipas
        boolean printAll = stdout.printAllQuestion();
        int []strategy = strategy(3 );

        if (printAll) {
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int [] goalKeepersPos = returnPosPlayers(fieldHome.getBenched(), "GoalKeepers", 1 );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos) fieldHome.setGoalKeeper(fieldHome.getBenched(i));
            //Defesas
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int [] backsPos = returnPosPlayers(fieldHome.getBenched(), "Back", strategy[0] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos) fieldHome.setDefender(fieldHome.getBenched(i));
            //Médios
            int [] midPos = returnPosPlayers(fieldHome.getBenched(), "Midfields", strategy[1] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos) fieldHome.setMidfield(fieldHome.getBenched(i));
            //Ataque
            int [] strikPos = returnPosPlayers(fieldHome.getBenched(), "Strikers", strategy[2] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos) fieldHome.setStriker(fieldHome.getBenched(i));
        }
        else {
            //Filtra os Guarda-Redes
            int [] goalKeepersPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof GoalKeeper), "GoalKeepers", 1 );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos) fieldHome.setGoalKeeper(((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof GoalKeeper)).get(i));
            //Filtra Defesas
            int [] backsPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Defender), "Back", strategy[0] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos) fieldHome.setGoalKeeper(((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Defender)).get(i));
            //Defesas
            int [] midPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Midfield), "Midfield", strategy[1] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos) fieldHome.setMidfield(((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Midfield)).get(i));
            //Atacantes
            int [] strikPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Striker), "Striker", strategy[2] );
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos) fieldHome.setMidfield(((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Striker)).get(i));

        }
        //Inicializa as coisas normais, tipo tempo e posição da bola
        this.standard();
        //Inicialização da equipa oposta
        setAwayField(setBot(awayTeam));
    }
    /*
    Funções que inicializam a classe PlayersField de forma automática da melhor forma.
    Estas funções selecionam os jogadores mais capazes para cada uma das posições, baseado nas suas posições base
     */

    private PlayersField setBot(Team away){
        PlayersField fieldAway = new PlayersField();
        //Number of players in bench + field in bot team
        int goalkeepers = (int) awayTeam.getPlayers().stream().filter(item -> item instanceof GoalKeeper).count();
        if (goalkeepers > 2) goalkeepers = 2;
        int backs = (int) awayTeam.getPlayers().stream().filter(item -> position(item, Zones.DEFENSE)).count();
        if (backs > 8) backs = 8;

        int mediuns = (int) awayTeam.getPlayers().stream().filter(item -> position (item, Zones.MIDDLE)).count();
        if (mediuns > 7) mediuns = 7;
        int strikers = (int) awayTeam.getPlayers().stream().filter(item -> position(item, Zones.OPPOSITE)).count();
        if (strikers > 5) strikers = 5;
        //Se tiver menos que 17 e a equipa tiver a mais, faz qualquer coisa, mas não sei o quê
        //Verificar onde se podem meter mais jogadores
        //Talvez um bool que a partir daqui passa a iserir mais?
        if (goalkeepers + backs + mediuns + strikers < 17 && awayTeam.getPlayers().stream().count() > 17) ;
        //No banco ficam todos
        //Se não separarmos não garantimos que a equipa não é só Ronaldos sem Ruis Patrícios
        filterAndSort(awayTeam.getPlayers(), goalkeepers, Zones.GOAL).stream().forEach(p ->  fieldAway.setBenched(p));
        filterAndSort(awayTeam.getPlayers(), backs, Zones.DEFENSE).stream().forEach(p ->  fieldAway.setBenched(p));
        filterAndSort(awayTeam.getPlayers(), mediuns, Zones.MIDDLE).stream().forEach(p ->  fieldAway.setBenched(p));
        filterAndSort(awayTeam.getPlayers(), strikers, Zones.OPPOSITE).stream().forEach(p ->  fieldAway.setBenched(p));

        //meter o guarda redes no campo
        Player toField = filterAndSort(fieldAway.getBenched(), 1, Zones.GOAL).get(1);
        fieldAway.leaveBench(toField);
        fieldAway.setGoalKeeper(toField);
        filterAndSort(fieldAway.getBenched(), 4, Zones.DEFENSE).
                stream().forEach(x -> setPlayerInField(fieldAway, x, Zones.DEFENSE) );
        filterAndSort(fieldAway.getBenched(), 4, Zones.MIDDLE).
                stream().forEach(x -> setPlayerInField(fieldAway, x, Zones.MIDDLE) );
        filterAndSort(fieldAway.getBenched(), 3, Zones.OPPOSITE).
                stream().forEach(x -> setPlayerInField(fieldAway, x, Zones.OPPOSITE) );
        return awayField;

    }
    private void setPlayerInField(PlayersField lists, Player p, Zones zone){
        lists.leaveBench(p);
        switch (zone){
            case  DEFENSE:
                lists.setDefender(p);
                break;
            case MIDDLE:
                lists.setMidfield(p);
                break;
            case OPPOSITE:
                lists.setStriker(p);
                break;
        }
    }

    private List<Player> filterAndSort(List<Player> players, int howMany, Zones position)
    {
        return players.stream().filter(player -> position(player, position)).
            sorted().limit(howMany).collect(Collectors.toList());
    }
    private boolean position(Player p, Zones z){
        return ((p instanceof GoalKeeper && z == Zones.GOAL)
                || ((p instanceof BackWing || p instanceof Defender) && z == Zones.DEFENSE)
                || (p instanceof Midfield && z == Zones.MIDDLE)
                || (p instanceof Striker && z == Zones.OPPOSITE));
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
        Player out = team.getPlayersPosition(posPlayerOut[0]).get(posPlayerOut[0]);
       // team.replace(in, out, pos_absoluteOut);
    }
    private void movePlayers(PlayersField team){
        ChosingPlayers stdout = new ChosingPlayers();
        int pos_absoluteStart = stdout.whereIsPlayer("Where it is?");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        int [] posPlayerStart = returnPosPlayers(team.getPlayersPosition(pos_absoluteStart),
                "Wich player leaves?", 1);
        int pos_absoluteEnd = stdout.whereIsPlayer("Where player goes?");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        //team.move(team.getPlayersPosition(pos_absoluteStart).get(0),
        //        pos_absoluteStart,  pos_absoluteEnd);
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

