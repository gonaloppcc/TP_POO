package Model.Match;

import Model.Player.*;
import Model.Team;
import View.ChosingPlayers;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
 //   private int scoreHome;
 //   private int scoreAway;

    // false -> home tem a bola
    // true -> Visitante tem a bola
    private boolean posse_bola;

    // pos_ball is a point in the field.
    private Point pos_ball;

    // Classe que tem os jogadores que estão a jogar, válidos para os confrontos
    //                  private PlayersField homeField;
    // Vão ser pares   private PlayersField awayField;
    //Pares de subsituições
    //Uma "lista" para cada equipa

    //(Jogador, posição, cartões)  -> -1 estar no banco


    public Match(List<Player> home, Team homeTeam, List<Player> away, Team awayTeam,
                 int time, int scoreHome, int scoreAway,
                 boolean posse_bola, Point pos_ball,
                 PlayersField homeField, PlayersField awayField) {
        super();

        // Falta dar clone de cenas
        super.setHomeTeam(homeTeam);
        super.setAwayTeam(awayTeam);
        super.setScoreAway(scoreAway);
        super.setScoreHome(scoreHome);
        this.time = time;
        this.posse_bola = posse_bola;
        this.pos_ball = pos_ball;
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
   /*
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
        if (homeTeam.getNumberOfPlayers() > total_players) {
            int[] playersChoosen = returnPosPlayers(homeTeam.getPlayers(), "Choose the players: " + homeTeam.getName(), total_players);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : playersChoosen) fieldHome.setBenched(homeTeam.getPlayer(i));
        }
        //Vai a equipa toda para o seated
        else fieldHome.setBenched(homeTeam.getPlayers());
        //Separar por zonas as escolhas de equipas
        boolean printAll = stdout.printAllQuestion();
        int[] strategy = strategy(3);

        if (printAll) {
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int[] goalKeepersPos = returnPosPlayers(fieldHome.getBenched(), "GoalKeepers", 1);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos) fieldHome.setGoalKeeper(fieldHome.getBenched(i));
            //Defesas
            //Imprime todos os jogadores, e retorna um array com a posição do jogador que será guarda-redes
            int[] backsPos = returnPosPlayers(fieldHome.getBenched(), "Back", strategy[0]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos) fieldHome.setDefender(fieldHome.getBenched(i));
            //Médios
            int[] midPos = returnPosPlayers(fieldHome.getBenched(), "Midfields", strategy[1]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos) fieldHome.setMidfield(fieldHome.getBenched(i));
            //Ataque
            int[] strikPos = returnPosPlayers(fieldHome.getBenched(), "Strikers", strategy[2]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos) fieldHome.setStriker(fieldHome.getBenched(i));
        } else {
            //Filtra os Guarda-Redes
            int[] goalKeepersPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof GoalKeeper), "GoalKeepers", 1);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : goalKeepersPos)
                fieldHome.setGoalKeeper(((List<Player>) fieldHome.getBenched().stream().
                        filter(item -> item instanceof GoalKeeper)).get(i));
            //Filtra Defesas
            int[] backsPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Defender), "Back", strategy[0]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : backsPos)
                fieldHome.setGoalKeeper(((List<Player>) fieldHome.getBenched().stream().
                        filter(item -> item instanceof Defender)).get(i));
            //Defesas
            int[] midPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Midfield), "Midfield", strategy[1]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : midPos)
                fieldHome.setMidfield(((List<Player>) fieldHome.getBenched().stream().
                        filter(item -> item instanceof Midfield)).get(i));
            //Atacantes
            int[] strikPos = returnPosPlayers((List<Player>) fieldHome.getBenched().stream().
                    filter(item -> item instanceof Striker), "Striker", strategy[2]);
            //vai buscar os jogadores que estão naquelas posições
            for (int i : strikPos)
                fieldHome.setMidfield(((List<Player>) fieldHome.getBenched().stream().
                        filter(item -> item instanceof Striker)).get(i));

        }
        //Inicializa as coisas normais, tipo tempo e posição da bola
        this.standard();
        //Inicialização da equipa oposta
    }

    public Match(String path) {
        readFile(path);
    }

    public Match clone() {
        // Fazer cenas;
        return this;
    }
*/

    /*------------------------------------------Getters and Setters---------------------------------------------------*/

    public LocalDate getDate() {
        return super.getDate();
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

    /** returnPosPlayers
        Função muito utilizada que liga os módulos View e Controller. O objetivo desta função é saber que jogadores o utilizador escolhe a partir dum conjunto de jogadores.
        Recebe a lista de jogadores que serão impressos, uma descrição (String) do que será escrito para o utilizador e quantos jogadores serão escolhidos.
        Retorna um array com as posições dos jogadores escolhidos a partir da lista.
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

    /** strategy
        Utilizando o módulo View, descobre que estratégia o utilizador vai utilizar no jogo.
        Esta estratégia consiste na distribuição dos jogadores pelo campo.
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
   /*
    private void changePlayers(PlayersField team) {
        ChosingPlayers stdout = new ChosingPlayers();
        //Imprime e recebe que posição do jogador recebe
        int[] posPlayerIn = returnPosPlayers(team.getPlayersBench(), "Which player gets in?", 1);
        PlayerField in = team.getBenched().get(posPlayerIn[0]);
        //saber onde o jogador que vai sair está
        int pos_absoluteOut = stdout.whereIsPlayer("Que vai sair");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        int[] posPlayerOut = returnPosPlayers(team.getPlayersCloseToTheBall(pos_absoluteOut),
                "Which player leaves?", 1);
        //O Jogador que está nessa posição
        Player out = team.getPlayersCloseToTheBall(posPlayerOut[0]).get(posPlayerOut[0]);
        // team.replace(in, out, pos_absoluteOut);
    }
*/
    /**
    Função que verifica se o jogo chegou ao fim por tempo.     
    */
    /*
    private void movePlayers(PlayersField team) {
        ChosingPlayers stdout = new ChosingPlayers();
        int pos_absoluteStart = stdout.whereIsPlayer("Where it is?");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        int[] posPlayerStart = returnPosPlayers(team.getPlayersPosition(pos_absoluteStart),
                "Which player leaves?", 1);
        int pos_absoluteEnd = stdout.whereIsPlayer("Where player goes?");
        //Saber dos vários jogadores que está aí, qual é que sai, a posição dele
        //team.move(team.getPlayersPosition(pos_absoluteStart).get(0),
        //        pos_absoluteStart,  pos_absoluteEnd);
    }

    private boolean endGame() {
        return (this.getTime() >= 90);
    }
    // Função inventada para isto dar
    private int skills(Player jogador) {
        return 1;
    }

    // IMPORTANTE
    // Falta filtrar os titulares na posição do campo em que a bola está
    private int valuePlayers(List<Player> equipa) {
        int total = 0;
        total += equipa.stream().mapToInt(this::skills).sum();
        return total;
    }
*/
    /** confrontation
        Função que compara as capacidades de duas equipas numa posição do campo.
        A vitória neste confronto resulta em duas possibilidades: recuperar bola ou avançar com a bola.
        Se já estiver ao pé da baliza adveresária, pode rematar.
    **/
    /*
    private int confrontation(int pos_ball){
        Random rd = new Random();
        //Para gerar números aleatórios
        //int randomNum = rand.nextInt((max - min) + 1) + min;
        //3- porque está no outro lado
        //Para gerar números aleatórios
        //int randomNum = rand.nextInt((max - min) + 1) + min;

        // Soma das skills do jogadores perto da bola de ambas equipas
        double homeValue = this.playersHome.getPlayersCloseToTheBall(pos_ball).stream().mapToDouble(PlayerField::skill).sum();
        double awayValue = this.playersAway.getPlayersCloseToTheBall(pos_ball).stream().mapToDouble(PlayerField::skill).sum();

        int probability = rd.nextInt();
        if (homeValue - awayValue < 0) {
            if (probability < 3) {return 1;}
            else return -1;
        } else if (homeValue - awayValue > 0) {
            if (probability < 7) {return 1;}
            else return -1;
        } else {
            if (probability < 5) {return 1;}
            else return -1;
        }
    }

*/
    /**
        Função que interliga as várias funções desta classe.
    */
  /*
    public void Game() {
        int increment_time = 10;
        if (this.endGame()) {
            System.out.println("Fim de jogo");
        } else {
            Point where_ball = getPos_ball();
            if (rematePossivel(where_ball)) remate(where_ball);
            int temp_where_ball = confrontation(where_ball);
            setPos_ball(where_ball + temp_where_ball);
            setTime(getTime() + increment_time);

        }
    }

    //Falta definir
    private void remate(Point pos_ball) {
        int res = confrontation(pos_ball);
        golo(res == -1);

        return;
    }


    //Getters e Setters

    private void golo(boolean team_score) {
        if (team_score) setScoreAway(getScoreAway() + 1);
        else setScoreHome(getScoreHome() + 1);
        setPos_ball(2);
    }

*/
    /**
        Um setter que inicializa as váriáveis da classe no início do jogo.
    */
  /*
    private void standard() {
        setTime(0);
        setScoreAway(0);
        setScoreHome(0);
        setPos_ball(2);
        Random rd = new Random();
        setPosse_bola(rd.nextBoolean());
    }

    public Match readFile(String path) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String curLine = "";
        while (true) {
            try {
                if (!((curLine = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //process the line as you require
            if (curLine.startsWith("Jogo:")) System.out.println(curLine);
        }
        return null;
    }
*/

    }

