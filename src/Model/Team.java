package Model;

import Model.Player.*;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Team implements Serializable {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private String name;
    private List<Player> players;

    // Construtores

    public Team() {
        this.name = "Default";
        this.players = new ArrayList<>();
    }
//Diogo Fazer esta
    public Team(String fromFile) {
        String [] divisoes = fromFile.split("###");
        this.name = divisoes[0];
        //for (String x : divisoes) System.out.println("Jogador: "+x);
        Scanner jogadores = new Scanner(divisoes[1]);
        this.players = new ArrayList<>();

        while (jogadores.hasNextLine())
        {
            String line = jogadores.nextLine();
            String[] info = line.split(":");
            if (line.startsWith("Guarda-Redes")) {
                players.add(new GoalKeeper(info[1].split(",")));
            }
            if (line.startsWith("Lateral")) {
                players.add(new BackWing(info[1].split(",")));
            }
            if (line.startsWith("Defesa")) {
                players.add(new Defender(info[1].split(",")));
            }
            if (line.startsWith("Medio")) {
                players.add(new Midfield(info[1].split(",")));
            }
            if (line.startsWith("Avancado")) {
                players.add(new Striker(info[1].split(",")));
            }

        }
        System.out.println(this.toString());
    }

    public Team(int numJogadores) {
        this.name = "Default";
        this.players = new ArrayList<>(numJogadores);
    }

    public Team(ArrayList<Player> players) {
        this.name = "Default";
        this.players = players;
    }

    public Team(String name, ArrayList<Player> players, int numberOfPlayers) {
        this.name = name;
        this.players = new ArrayList<>();
        //this.players = (ArrayList) players.clone();
        this.players = players;
    }

    public Team(Team team) {
        this.name = team.getName();
        this.players = team.getPlayers();//.stream().map(x -> x.clone()).collect(Collectors.toList());
    }

    public Team(String name, int numberOfPlayers) {
        this.name = name;
        this.players = new ArrayList<>(numberOfPlayers);
    }

    public Team(String name, List<Player> players, int numberOfPlayers) {
        this.name = name;
        this.players = players;
    }

    // Metodos


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return this.players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getPlayer(int pos) {
        return players.get(pos);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public String toString() {
        return name + ":\n\t " +
                players +
                "}\n";
    }

    public Team clone() {
        return new Team(this);
    }
}
