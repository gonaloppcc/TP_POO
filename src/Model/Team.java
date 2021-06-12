package Model;

import Model.Player.*;
import View.StatusView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Each team is a list of Players.
 * The class Player don't have the information related to positions in a match.
 */
public class Team implements Serializable {

    private String name;
    private List<Player> players;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Team() {
        this.name = "Default";
        this.players = new ArrayList<>();
    }

    public Team(String fromFile) throws InvalidLineExcpetion {
        String [] divisoes = fromFile.split("###");
        this.name = divisoes[0];
        Scanner jogadores = new Scanner(divisoes[1]);
        this.players = new ArrayList<>();

        while (jogadores.hasNextLine())
        {
            String line = jogadores.nextLine();
            addPlayer(line);
        }
    }


    public Team(int numJogadores) {
        this.name = "Default";
        this.players = new ArrayList<>(numJogadores);
    }

    public Team(ArrayList<Player> players) {
        this.name = "Default";
        this.players = players.stream().map(Player::clone).collect(Collectors.toList());
    }

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players.stream().map(Player::clone).collect(Collectors.toList());
    }

    public Team(Team team) {
        this.name = team.getName();
        this.players = team.getPlayers();//.stream().map(x -> x.clone()).collect(Collectors.toList());
    }

    public Team(String name, int numberOfPlayers) {
        this.name = name;
        this.players = new ArrayList<>(numberOfPlayers);
    }


    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPlayers() {
        return this.players.size();
    }

    public List<Player> getPlayers() {
        return this.players.stream().map(Player::clone).collect(Collectors.toList());
    }

    public void setPlayers(List<Player> players) {
        this.players = players.stream().map(Player::clone).collect(Collectors.toList());
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    /**
     * Get a list of all Players with that name.
     * @param name Name to search.
     * @return All players of that team with the name given as an argument.
     */
    public List<Player> getPlayer(String name){
        return this.players.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
    }

    /**
     * Convert a line related to one player to one object of the class Player, and then stores it in this team.
     * @param line Line from a text file.
     * @throws InvalidLineExcpetion When the line is invalid.
     */
    public void addPlayer(String line) throws InvalidLineExcpetion {
        if (!line.contains(":") || !line.contains(",")) throw new InvalidLineExcpetion(line);
        String[] info = line.split(":");
        String[] atributes =  info[1].split(",");
        for (String x : atributes) x =  x.trim();
        if (line.startsWith("Guarda-Redes")) {
            if (atributes.length != 10) {
                StatusView.InvalidLine();
                throw new InvalidLineExcpetion(Arrays.toString(atributes));
            }
            this.players.add(new GoalKeeper(atributes));
        }
        if (line.startsWith("Lateral")) {
            if (atributes.length != 10) {
                StatusView.InvalidLine();
                throw new InvalidLineExcpetion(Arrays.toString(atributes));
            }
            this.players.add(new BackWing(atributes));
        }
        if (line.startsWith("Defesa")) {
            if (atributes.length != 9) {
                StatusView.InvalidLine();
                throw new InvalidLineExcpetion(Arrays.toString(atributes));
            }
            this.players.add(new Defender(atributes));
        }
        if (line.startsWith("Medio")) {
            if (atributes.length != 10) {
                StatusView.InvalidLine();
                throw new InvalidLineExcpetion(Arrays.toString(atributes));
            }
            this.players.add(new Midfield(atributes));
        }
        if (line.startsWith("Avancado")) {
            if (atributes.length != 9) {
                StatusView.InvalidLine();
                throw new InvalidLineExcpetion(Arrays.toString(atributes));
            }
            this.players.add(new Striker(info[1].split(",")));
        }
    }

    /**
     * Add a player to one team
     * @param player Player to be inserted
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Team clone() {
        return new Team(this);
    }

    /**
     * Calculate the average skill of one team.
     * @return Global Skill of one team.
     */
    public int globalSkill(){
        if (players.size() < 1) return 0;
        return players.stream().mapToInt(Player::globalSkill).sum()/players.size();
    }

    /*-------------------- toString methods --------------------*/
    @Override
    public String toString() {
        return name + ":\n\t " +
                players.stream().map(Player :: toString).collect(Collectors.joining("\n")) +
                "\n";
    }

    /**
     * Returns a string with Skills and history of teams where he passed.
     * @return String ready to be printed
     */
    public String toStringSkillsAndHistory() {
        return name + ":\n" +
                players.stream().map(Player :: toStringSkills).collect(Collectors.joining("\n")) +
                "\n And global skill of team is: " + globalSkill() +
                "\n";
    }


}
