package Model;

import Model.Player.*;

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

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Team() {
        this.name = "Default";
        this.players = new ArrayList<>();
    }
//Diogo Fazer esta
    public Team(String fromFile) throws NotValidException {
        String [] divisoes = fromFile.split("###");
        this.name = divisoes[0];
        //for (String x : divisoes) System.out.println("Jogador: "+x);
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

    public Team(String name, ArrayList<Player> players, int numberOfPlayers) {
        this.name = name;
        this.players = players.stream().map(Player::clone).collect(Collectors.toList());
        //this.players = (ArrayList) players.clone();
        //this.players = players;
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

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

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
        return players.stream().map(Player::clone).collect(Collectors.toList());
    }

    public void setPlayers(List<Player> players) {
        this.players = players.stream().map(Player::clone).collect(Collectors.toList());
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    public List<Player> getPlayer(String name){
        return players.stream().filter(x -> x.getName().equals(name)).collect(Collectors.toList());
    }

    public void addPlayer(String line) throws NotValidException {
        if (!line.contains(":") || !line.contains(",")) throw new NotValidException(line);
        String[] info = line.split(":");
        String[] atributes =  info[1].split(",");
        for (String x : atributes) x =  x.trim();
        if (line.startsWith("Guarda-Redes")) {
            if (atributes.length != 10) {
                System.out.println("Inválido");
                throw new NotValidException(atributes.toString());
            }
            players.add(new GoalKeeper(atributes));
        }
        if (line.startsWith("Lateral")) {
            if (atributes.length != 10) {
                System.out.println("Inválido");
                throw new NotValidException(atributes.toString());
            }
            players.add(new BackWing(atributes));
        }
        if (line.startsWith("Defesa")) {
            if (atributes.length != 9) {
                System.out.println("Inválido");
                throw new NotValidException(atributes.toString());
            }
            players.add(new Defender(atributes));
        }
        if (line.startsWith("Medio")) {
            if (atributes.length != 10) {
                System.out.println("Inválido");
                throw new NotValidException(atributes.toString());
            }
            players.add(new Midfield(atributes));
        }
        if (line.startsWith("Avancado")) {
            if (atributes.length != 9) {
                System.out.println("Inválido");
                throw new NotValidException(atributes.toString());
            }
            players.add(new Striker(info[1].split(",")));
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public String toString() {
        return name + ":\n\t " +
                players.stream().map(Player :: toString).collect(Collectors.joining("\n")) +
                "\n";
    }

    public String toStringSkillsAndHistory() {
        return name + ":\n\t " +
                players.stream().map(Player :: toStringSkills).collect(Collectors.joining("\n")) +
                "\n And global skill of team is: " + globalSkill() +
                "\n";
    }

    public Team clone() {
        return new Team(this);
    }

    public int globalSkill(){
        return players.stream().mapToInt(Player::globalSkill).sum()/players.size();
    }
}
