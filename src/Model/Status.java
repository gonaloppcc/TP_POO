package Model;

import Model.Player.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Status implements Serializable {
    private String gameName;
    private int playersPerTeam;
    private List<Team> teams; // Informações sobre o save atual
    private Map<LocalDate, List<Match>> games;    // A lista seria os jogos que tinhamos ocorrido naquele dia

/*
    This class is use to contenting all data regarding the game
    It's here where we comand basically everything (Create a team, transfer a player)
    It has methods for saving and loading game purposes
    */

    public Status() { // Construtor básico, cria com o jogo "Futebol"
        this.gameName = "Futebol";
        this.teams = new ArrayList<>();
        this.playersPerTeam = 11;
        this.games = new TreeMap<LocalDate, List<Match>>();
    }
    private void addgame(Match toInsert){
        if (games.containsKey(toInsert.getDate()))
        {
            List<Match> one = games.get(toInsert.getDate());
            one.add(toInsert);
            games.put(toInsert.getDate(), one);

        }
        else {
            List <Match> newList = new ArrayList();
            newList.add(toInsert);
            games.put(toInsert.getDate(),newList);
        }
    }
    public Status(String gameName, int playersPerTeam, List<Team> teams) {
        this.gameName = gameName;
        this.playersPerTeam = playersPerTeam;
        this.teams = teams;
    }

    public void save(String filePath) throws IOException {
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
    public void loadText(String filePath) {
        try{
            File fd = new File(filePath);
            Scanner file = new Scanner(fd);
            String line = file.nextLine();
            while (file.hasNextLine()) {
                //Caso a linha seja sobre um jogo
                if (line.startsWith("Jogo:")) {
                    Match one = new Match(line);
                    //Adicionar um método para adicionar matchs ao Set
                    //if (one != null) games.contains()
                    line = file.nextLine();
                }
                else {
                    if (line.startsWith("Equipa:")) {

                        StringBuilder team = new StringBuilder(line.substring(7)+"###");
                        line = file.nextLine();
                        while (isTeam(line)) {team.append(line+"\n"); line = file.nextLine();}
                      //System.out.println("Equipa: "+team.toString());
                        teams.add(new Team(team.toString()));
                    } else {
                        //Linha inválida
                        //Para fazer debugging
                        System.out.println("Inválida . -> "+ line);
                        line = file.nextLine();
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    private boolean isTeam(String lineFile){
        if (!lineFile.startsWith("Equipa:") &&
                !lineFile.startsWith("Jogo:")
        ) return true;
        else return false;
    }

    public void loadPath(String path) {
        try{
            load(path);

        } catch (Exception e) {
                loadText(path);
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

    public List<Team> getTeams() {
        return teams /*.stream().map(Team::clone).collect(Collectors.toList())*/;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    
    public Map<LocalDate, List<Match>> getGames() {
        return games.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void setGames(Map <LocalDate, List<Match>> games) {
        this.games = new HashMap<>(games) ;
    }


    @Override
    public String toString() {
        return "Status{" +
                "\n\tGameName= " + gameName +
                "\n\tPlayersPerTeam= " + playersPerTeam +
                "\n\tTeams= " + teams +
                "\n\t}";
    }

}
