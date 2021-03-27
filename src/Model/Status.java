package Model;

import Model.Player.*;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class Status {
    private String gameName;
    private int playersPerTeam;
    private List<Team> teams; // Informações sobre o save atual
    private List<Match> games; // Ainda falta desenvolver!!!!!!!!!1

/*
    This class is use to contenting all data regarding the game
    It's here where we comand basically everything (Create a team, transfer a player)
    It has methods for saving and loading game purposes
    */

    public Status() { // Construtor básico, cria com o jogo "Futebol"
        this.gameName = "Futebol";
        this.teams = new ArrayList<>();
        this.playersPerTeam = 11;
        this.games = new ArrayList<>();
    }

    public Status(String gameName, int playersPerTeam, List<Team> teams) {
        this.gameName = gameName;
        this.playersPerTeam = playersPerTeam;
        this.teams = teams;
    }

    public static void Saving(String filename) {
        // return ...
    }

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

    public List<Match> getGames() {
        return games;
    }

    public void setGames(List<Match> games) {
        this.games = games;
    }

    public void Load(String filename) {
        try {
            //Team atual a ser preenchida
            int equipa_atual = 0;
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            // GameName
            String data = scan.nextLine();
            this.setGameName(data);
            // PlayerPerTeam
            data = scan.nextLine();
            this.setPlayersPerTeam(Integer.parseInt(data));
            // Teams
            while (scan.hasNextLine()) {
                data = scan.nextLine();
                //  System.out.println("Meu: "+data.equals("."));
                if (data.equals(".")) {
                    //Define nome equipa
                    data = scan.nextLine();
                    Team nova = new Team(data);
                    this.teams.add(nova);
                    while (!data.equals(".") && scan.hasNextLine()) {
                        data = scan.nextLine();
                        String[] gAux = data.split(";");

                        //Player.GoalKeeper g = new Player.GoalKeeper(gAux);
                        //this.Teams[equipa_atual].addJogador(new Player.GoalKeeper(gAux));
                        switch (data.charAt(0)) {
                            case 'G' -> {
                                GoalKeeper g = new GoalKeeper(gAux);
                                this.teams.get(equipa_atual).addPlayer(g);
                            }
                            case 'A' -> this.teams.get(equipa_atual).addPlayer(new Striker(gAux));
                            case 'L' -> this.teams.get(equipa_atual).addPlayer(new BackWing(gAux));
                            case 'M' -> this.teams.get(equipa_atual).addPlayer(new Midfield(gAux));
                            case 'D' -> this.teams.get(equipa_atual).addPlayer(new Defender(gAux));
                            default -> System.out.println("Quem?");
                        }


                    }
                }
                equipa_atual++;
                        /*    //Nova equipa
                    // System.out.println(data);
                    if (data.charAt(1) != ';') {
                    // Team name(//)
                    // this.Teams[0].setNome(data);
                } else {
                    if (data.charAt(0) == 'G') {
                        ArrayList<String> gAux = data.split(";");
                        Player.GoalKeeper g = new Player.GoalKeeper(Integer.parseInt(gAux[0]), Integer.parseInt(gAux[1]),
                                Integer.parseInt(gAux[2]), Integer.parseInt(gAux[3]), Integer.parseInt(gAux[4]),
                                Integer.parseInt(gAux[5]), gAux[7], Integer.parseInt(gAux[8]));
                        this.Teams[0].addJogador(g);
                    }
                }

*/  //this.toString();
            }

        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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

}
