package Model;

import Model.Player.*;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Status { // Mudar para save?
    private String GameName;
    private int PlayersPerTeam;
    private ArrayList<Team> Teams; // Informações sobre o save atual
    //private Jogos;

/*
    This class is use to contenting all data regarding the game
    It's here where we comand basically everything (Create a team, transfer a player)
    It has methods for saving and loading game purposes
    */

    public Status() {
        this.GameName = "Futebol";
        this.Teams = new ArrayList<>();
        this.PlayersPerTeam = 11;
    }

    public Status(String gameName, int playersPerTeam, ArrayList<Team> teams) {
        GameName = gameName;
        PlayersPerTeam = playersPerTeam;
        Teams = teams;
    }


    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public int getPlayersPerTeam() {
        return PlayersPerTeam;
    }

    public void setPlayersPerTeam(int playersPerTeam) {
        PlayersPerTeam = playersPerTeam;
    }

    public ArrayList<Team> getTeams() {
        return Teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        Teams = teams;
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
                    this.Teams.add(nova);
                    while (!data.equals(".") && scan.hasNextLine()) {
                        data = scan.nextLine();
                        String[] gAux = data.split(";");

                        //Player.Goalkeeper g = new Player.Goalkeeper(gAux);
                        //this.Teams[equipa_atual].addJogador(new Player.Goalkeeper(gAux));
                        switch (data.charAt(0)) {
                            case 'G' -> {
                                Goalkeeper g = new Goalkeeper(gAux);
                                this.Teams.get(equipa_atual).addJogador(g);
                            }
                            case 'A' -> this.Teams.get(equipa_atual).addJogador(new Striker(gAux));
                            case 'L' -> this.Teams.get(equipa_atual).addJogador(new Back(gAux));
                            case 'M' -> this.Teams.get(equipa_atual).addJogador(new Midfield(gAux));
                            case 'D' -> this.Teams.get(equipa_atual).addJogador(new Defender(gAux));
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
                        Player.Goalkeeper g = new Player.Goalkeeper(Integer.parseInt(gAux[0]), Integer.parseInt(gAux[1]),
                                Integer.parseInt(gAux[2]), Integer.parseInt(gAux[3]), Integer.parseInt(gAux[4]),
                                Integer.parseInt(gAux[5]), gAux[7], Integer.parseInt(gAux[8]));
                        this.Teams[0].addJogador(g);
                    }
                }

*/  //this.toString();
            }

    } catch(
    FileNotFoundException e)

    {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

}

    public static void Saving(String filename) {
        // return ...
    }
    @Override
    public String toString() {
        return "Status{" +
                "GameName='" + GameName + '\'' +
                ", PlayersPerTeam=" + PlayersPerTeam +
                ", Teams=" + toStringAux(this.Teams) +
                '}';
    }
    //Função auxiliar da toString, para imprimir uma equipa de cada vez
    private String toStringAux(ArrayList<Team> teams){
        String equipas = "";
        Team x;
        for(int i = 0; teams.get(i) != null; i++) equipas.concat(teams.get(i).toString());
        return equipas;
    }

}
