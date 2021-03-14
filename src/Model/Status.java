package Model;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

public class Status { // Mudar para save?
    private String GameName;
    private int PlayersPerTeam;
    private Equipa[] Teams; // Informações sobre o save atual


/*
    This class is use to contenting all data regarding the game
    It's here where we comand basically everything (Create a team, transfer a player)
    It has methods for saving and loading game purposes
    */

    public Status() {
        this.GameName = "Futebol";
        this.Teams = new Equipa[8];
        this.PlayersPerTeam = 11;
    }

    public Status(String gameName, int playersPerTeam, Equipa[] teams) {
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

    public Equipa[] getTeams() {
        return Teams;
    }

    public void setTeams(Equipa[] teams) {
        Teams = teams;
    }

    public void Load(String filename) {
        try {
            //Equipa atual a ser preenchida
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
                    data = scan.nextLine();
                    Equipa nova = new Equipa(data);
                    this.Teams[equipa_atual] = nova;
                    while (!data.equals(".") && scan.hasNextLine()) {
                        data = scan.nextLine();
                        String[] gAux = data.split(";");

                        switch(data.charAt(0)){
                            case 'G':
                                Jogador.guarda_redes g = new Jogador.guarda_redes(gAux);
                                this.Teams[equipa_atual].addJogador(g);
                                //Jogador.guarda_redes g = new Jogador.guarda_redes(gAux);
                                //this.Teams[equipa_atual].addJogador(new Jogador.guarda_redes(gAux));
                            break;
                            case 'A':
                                this.Teams[equipa_atual].addJogador(new Jogador.avancado(gAux));
                                break;
                            case 'L':
                                this.Teams[equipa_atual].addJogador(new Jogador.laterais(gAux));
                                break;
                            case 'M':
                                this.Teams[equipa_atual].addJogador(new Jogador.medio(gAux));
                                break;
                            case 'D':
                                this.Teams[equipa_atual].addJogador(new Jogador.defesa(gAux));

                                break;
                            default:
                                System.out.println("Quem?");
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
                        String[] gAux = data.split(";");
                        Jogador.guarda_redes g = new Jogador.guarda_redes(Integer.parseInt(gAux[0]), Integer.parseInt(gAux[1]),
                                Integer.parseInt(gAux[2]), Integer.parseInt(gAux[3]), Integer.parseInt(gAux[4]),
                                Integer.parseInt(gAux[5]), gAux[7], Integer.parseInt(gAux[8]));
                        this.Teams[0].addJogador(g);
                    }
                }

*/  this.toString();
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
    private String toStringAux(Equipa [] teams){
        String equipas = "";
        Equipa x;
        for(int i = 0; teams[i] != null; i++) equipas.concat(teams[i].toString());
        return equipas;
    }

}
