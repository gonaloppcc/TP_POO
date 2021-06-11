package Model.Player;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


abstract public class Player implements Serializable {
    private String name;
    private LocalDate birthDate;

    private int num, velocity, resistance, dexterity, impulsion, headGame, finish, passing;
    private List<String> historial;

    /*------------------------------------------------Constructors----------------------------------------------------*/

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

    public Player(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
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
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

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

    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }

    public void setHistorial(List<String> historial) {
        this.historial = new ArrayList<>(historial);
    }

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

    @Override
    public abstract String toString();

    public abstract String toStringSkills();

    abstract public Player clone();

    /**
     * @return the global skill of the player
     */
    public abstract int globalSkill();

    /**
     * @return the of age of the player
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Adds a historial to a player
     * @param teamName the name of the team
     */
    public void addHistorial(String teamName) {
        this.historial.add(teamName);
    }

}
