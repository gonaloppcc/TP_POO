package Model;

import Model.Player.*;

import java.io.*;
import java.time.LocalDate;
import Model.Match.*;
import View.StatusView;

import java.util.*;
import java.util.stream.Collectors;

public class Status implements Serializable {
    private String gameName;
    private int playersPerTeam;
 /*
         <<<<<<< HEAD
    private List<Team> teams; // Informações sobre o save atual
    private Map<LocalDate, List<Match>> games; // Ainda falta desenvolver!!!!!!!!! A forma de ordenar seria a data do jogo
    // A lista seria os jogos que tinhamos ocorrido naquele dia
=======
*/
    private Map<String, Team> teams; // Informações sobre o save atual
    private Map<LocalDate, List<MatchRegister>> games;    // A lista seria os jogos que tinhamos ocorrido naquele dia
    private String playerTeam;


    /*
    This class is use to contenting all data regarding the game
    It's here where we comand basically everything (Create a team, transfer a player)
    It has methods for saving and loading game purposes
    */
    private void addgame(MatchRegister toInsert){
        if (games.containsKey(toInsert.getDate()))
        {
            List<MatchRegister> one = games.get(toInsert.getDate());
            one.add(toInsert);
            games.put(toInsert.getDate(), one);

        }
        else {
            List <MatchRegister> newList = new ArrayList();
            newList.add(toInsert);
            games.put(toInsert.getDate(),newList);
        }
    }
    public Status() { // Construtor básico, cria com o jogo "Futebol"
        this.gameName = "Futebol";
        this.teams = new HashMap<>();
        this.playersPerTeam = 11;
        this.games = new TreeMap<>();
    }


    private void addgame(Match toInsert){
        if (games.containsKey(toInsert.getDate()))
        {
            List<MatchRegister> one = games.get(toInsert.getDate());
            one.add(toInsert);
            games.put(toInsert.getDate(), one);

        }
        else {
            List<MatchRegister> newList = new ArrayList<>();
            newList.add(toInsert);
            games.put(toInsert.getDate(),newList);
        }
    }
    public Status(String gameName, int playersPerTeam, Map<String, Team> teams) {
        this.gameName = gameName;
        this.playersPerTeam = playersPerTeam;
        this.teams = new HashMap<>(teams);
    }

    public void save(String filePath) throws IOException {
        System.out.println("Gonçalo?");
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(this);

        // Closing stuff
        out.close();
        fos.close();
    }

    public static Status load(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fis);
        Status status = (Status) in.readObject();

        // Closing stuff
        in.close();
        fis.close();

        return status;
    }
//Quando o ficheiro original é .txt
    public void loadText(String filePath) throws NotValidException {
        try{
            File fd = new File(filePath);
            Scanner file = new Scanner(fd);
            String line ;//= file.nextLine();
            //boolean NotlastLine = true;
            line = file.nextLine();

            while (file.hasNext() ) {

                // System.out.println("lin " + line);
                if (line.startsWith("Jogo:")) {
                    addMatch(line);
                    line = file.nextLine();
                }
                else {
                    if (line.startsWith("Equipa:")) {
                        line = addTeam(line, file);

                    } else {
                        //Linha inválida
                        //Para fazer debugging
                        System.out.println("Inválida . -> "+ line);
                        line = file.nextLine();
                    }
                }
            }
            file.close();
            if (line.startsWith("Jogo:")) {
                  addMatch(line);
            }
            else if (line.startsWith("Equipa:")) {
                line = addTeam(line, file);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void addTeam(String name){
        Team byPlayer = new Team();
        byPlayer.setName(name);
        teams.put(name, byPlayer);
    }
    private String addTeam(String line, Scanner file ) throws NotValidException {
        StringBuilder team = new StringBuilder(line.substring(7)+"###");
        line = file.nextLine();
        while (isTeam(line)) {
            team.append(line + "\n");
            line = file.nextLine();
        }
        //System.out.println("Equipa: "+team.toString());
        Team toInsert = new Team(team.toString());
        teams.put(toInsert.getName(), toInsert);
        return line;
    }
    private void addMatch(String match){
        String[] info = match.substring(5).split(",");
        Team home  = null;
        Team away= null;
        if (teams.containsKey(info[0])) home = new Team(teams.get(info[0]));
        if (teams.containsKey(info[1])) away = new Team(teams.get(info[1]));

        if (home != null && away != null)
        {
            MatchRegister one = new MatchRegister(info, home, away);
            if (games.containsKey(one.getDate())) games.get(one.getDate()).add(one);
            else games.put(one.getDate(), new ArrayList<MatchRegister>(List.of(one)));
        }
    }
    private boolean isTeam(String lineFile){
        if (!lineFile.startsWith("Equipa:") &&
                !lineFile.startsWith("Jogo:")
        ) return true;
        else return false;
    }

    public void loadPath(String path)  {
        try{
            load(path);

        } catch (Exception e) {
            try {
                loadText(path);
            } catch (NotValidException notValidException) {
                StatusView.InvalidLine();
            }
        }
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public void setPlayersPerTeam(int playersPerTeam) {
        this.playersPerTeam = playersPerTeam;
    }

    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    public Team getTeam(String name) {
        return teams.get(name);
    }


    public void setTeams(Map<String, Team> teams) {
        this.teams = teams;
    }

    public Map<LocalDate, List<MatchRegister>> getGames() {
        return games.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setGames(Map <LocalDate, List<MatchRegister>> games) {
        this.games = new HashMap<LocalDate, List<MatchRegister>>(games) ;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    @Override
    public String toString() {
        return "Status{" +
                "\n\tGameName= " + gameName +
                "\n\tPlayersPerTeam= " + playersPerTeam +
                "\n\tTeams= " + teams +
                "\n\t}";
    }

    public Status clone() {
        Status res = new Status(gameName, playersPerTeam, teams);
        res.games = new HashMap<>(games);
        res.playerTeam = playerTeam;
        return res;
    }


}
