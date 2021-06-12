package Model;


import java.io.*;
import java.time.LocalDate;
import Model.Match.*;
import View.StatusView;

import java.util.*;
import java.util.stream.Collectors;

/**
 This class is use to contenting all data regarding the game
 It's here where we comand basically everything (Create a team, transfer a player)
 It has methods for saving and loading game purposes
 */

public class Status implements Serializable {
    private String gameName;
    private int playersPerTeam;

    private Map<String, Team> teams; //All teams stored by name
    private Map<LocalDate, List<MatchRegister>> games;    // Games stored by date
    private String playerTeam; //Team of the player

    /*-------------------- Constructors ------------------------*/

    public Status() { // Construtor básico, cria com o jogo "Futebol"
        this.gameName = "Futebol";
        this.teams = new HashMap<>();
        this.playersPerTeam = 11;
        this.games = new TreeMap<>();
    }


    public Status(String gameName, int playersPerTeam, Map<String, Team> teams) {
        this.gameName = gameName;
        this.playersPerTeam = playersPerTeam;
        this.teams = new HashMap<>(teams);
    }


    /*----------------------- Public functions ----------------------------*/
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

    /**
     * Loads a text file
     * @param filePath Path
     * @throws InvalidLineExcpetion When there is no file
     */
    public void loadText(String filePath) {
        try{
            int invalidLines = 0;
            File fd = new File(filePath);
            Scanner file = new Scanner(fd);
            String line ;
            line = file.nextLine();

            while (file.hasNext() ) {
                try {
                    //If the line is related to a game
                    if (line.startsWith("Jogo:")) {
                        addMatch(line);
                        line = file.nextLine();
                    } else {
                        //If the line is related to a team
                        if (line.startsWith("Equipa:")) {
                            line = addTeam(line, file);

                        } else {
                            //Invalid line, the function ignores
                            line = file.nextLine();
                            throw new InvalidLineExcpetion();
                        }
                    }
                } catch (InvalidLineExcpetion e) {
                    invalidLines++;
                }
            }
            file.close();
            if (invalidLines != 0) StatusView.numberOfInvalidLines(invalidLines);

            try {
                if (line.startsWith("Jogo:")) {
                    addMatch(line);
                } else if (line.startsWith("Equipa:")) {
                    addTeam(line, file);
                }

            } catch (InvalidLineExcpetion e) {
                invalidLines++;
                StatusView.numberOfInvalidLines(invalidLines);

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /*----------------------- Private functions ----------------------------*/

    /**
     * Gets a line from the text file and converts to a full team.
     * @param line First line
     * @param file Scanner of the file, with path included
     * @return String Next line of the file, after the last player of this team.
     * @throws InvalidLineExcpetion In case the line is invalid
     */
    private String addTeam(String line, Scanner file ) throws InvalidLineExcpetion {
        StringBuilder team = new StringBuilder(line.substring(7)+"###");
        line = file.nextLine();
        while (isTeam(line)) {
            team.append(line).append("\n");
            line = file.nextLine();
        }
        //System.out.println("Equipa: "+team.toString());
        Team toInsert = new Team(team.toString());
        teams.put(toInsert.getName(), toInsert);
        return line;
    }

    /**
     * Converts one line of the text file, converts to a MatchRegister, and adds to the status.
     * @param match Line of the txt file
     */
    private void addMatch(String match){
        String[] info = match.substring(5).split(",");
        Team home  = null;
        Team away= null;
        if (teams.containsKey(info[0])) home = new Team(teams.get(info[0]));
        if (teams.containsKey(info[1])) away = new Team(teams.get(info[1]));

        if (home != null && away != null)
        {
            MatchRegister one = null;
            try {
                one = new MatchRegister(info, home, away);
                if (games.containsKey(one.getDate())) games.get(one.getDate()).add(one);
                else games.put(one.getDate(), new ArrayList<>(List.of(one)));
            } catch (InvalidLineExcpetion invalidLineExcpetion) {
                return;
            }
        }
    }

    /**
     * Check if the line is a player of a team or not.
     * @param lineFile line from the text file.
     * @return It it is or not.
     */
    private boolean isTeam(String lineFile){
        return !lineFile.startsWith("Equipa:") &&
                !lineFile.startsWith("Jogo:");
    }

    public void loadPath(String path)  {
        try{
            load(path);

        } catch (Exception e) {
                loadText(path);
            }
        }

        /*------------------------------------------ Getters / Setters and other similiar methods ---------------------------------------------------*/

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
        this.games = new HashMap<>(games) ;
    }

    public String getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void addTeam(String name){
        Team byPlayer = new Team();
        byPlayer.setName(name);
        teams.put(name, byPlayer);
    }

    /**
     * Inserts a new game in the current register
     * @param match Match to be added
     */
    public void addMatch(Match match){
        LocalDate dateGame = match.getDate();
        if(games.containsKey(dateGame)){
            List<MatchRegister> sameDate = games.get(dateGame);
            sameDate.add(match);
        }

        else {
            List<MatchRegister> toAdd = new ArrayList<>();
            toAdd.add(match);
            games.put(dateGame, toAdd);
        }
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
