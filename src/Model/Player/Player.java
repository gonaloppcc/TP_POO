package Model.Player;

import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


abstract public class Player implements Serializable {
    private String name;
    private LocalDate birthDate;

    // v. i. que adicionei // Gonçalo
    private String nationality;
    private Team currentTeam;
    //

    private int num, velocity, resistance, dexterity, impulsion, headGame, finish, passing;
    private List<String> historial;

    // Construtores


    public Player(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        this.name = name;
        this.birthDate = birthDate;
        this.num = num;
        this.velocity = velocity;
        this.resistance = resistance;
        this.dexterity = dexterity;
        this.impulsion = impulsion;
        this.headGame = headGame;
        this.finish = finish;
        this.passing = passing;
        this.historial = historial;
    }


    public Player(String name, LocalDate birthDate, int num, int velocity,int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        this.name = name;
        this.birthDate = birthDate;
        this.num = num;
        this.velocity = velocity;
        this.resistance = resistance;
        this.dexterity = dexterity;
        this.impulsion = impulsion;
        this.headGame = headGame;
        this.finish = finish;
        this.passing = passing;
        this.historial = new ArrayList<>();
        this.historial.add(historial);
    }

    public Player(String[] data) {
        LocalDate birth = LocalDate.of(2000, 1, 1);
        this.name = data[0];
        this.birthDate = birth;
        this.num = Integer.parseInt(data[1]);
        this.velocity = Integer.parseInt(data[2]);
        this.resistance = Integer.parseInt(data[3]);
        this.dexterity = Integer.parseInt(data[4]);
        this.impulsion = Integer.parseInt(data[5]);
        this.headGame = Integer.parseInt(data[6]);
        this.finish = Integer.parseInt(data[7]);
        this.passing = Integer.parseInt(data[8]);
        this.historial = new ArrayList<>();
        //this.historial.add("");
    }

    public abstract int globalSkill();

    @Override
    public abstract String toString();


    public String toStringAlter() {
        return "Player " + name + ": "
                        + this.globalSkill();
    }


    /*------------------------------------------ Getters e Setters -----------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getImpulsion() {
        return impulsion;
    }

    public void setImpulsion(int impulsion) {
        this.impulsion = impulsion;
    }

    public int getHeadGame() {
        return headGame;
    }

    public void setHeadGame(int headGame) {
        this.headGame = headGame;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public String getHistorial() {
        return this.historial.stream().collect(Collectors.joining());
    }

    public void setHistorial(List<String> historial) {this.historial = historial;}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    public int getIdade() {
        // Idade do jogador relativa a agora
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void addHistorial(String team) {
        this.historial.add(team);
    }

    abstract public Player clone();
}
