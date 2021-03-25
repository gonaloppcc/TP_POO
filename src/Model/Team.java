package Model;

import Model.Player.*;

import java.util.ArrayList;
import java.util.List;

public class Team {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private String name;
    private List<Player> players;
    private int numberOfPlayers;

    // Construtores

    public Team() {
        this.name = "Default";
        this.players = new ArrayList<>();
        this.numberOfPlayers = 0;
    }

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.numberOfPlayers = 0;
    }

    public Team(int numJogadores) {
        this.name = "Default";
        this.players = new ArrayList<>(numJogadores);
        this.numberOfPlayers = numJogadores;
    }

    public Team(ArrayList<Player> players) {
        this.name = "Default";
        this.players = players;
        this.numberOfPlayers = 0;
    }

    public Team(String name, ArrayList<Player> players, int numberOfPlayers) {
        this.name = name;
        this.players = new ArrayList<>();
        //this.players = (ArrayList) players.clone();
        this.players = players;
        this.numberOfPlayers = numberOfPlayers;
    }



    // Metodos


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int pos) {
        return players.get(pos);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}
