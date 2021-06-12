package Model.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Striker extends Player {

    private int precision;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Striker(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int precision) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.precision = precision;
    }

    public Striker(Striker s) {
        this(s.getName(), s.getBirthDate(), s.getNum(), s.getVelocity(), s.getResistance(), s.getDexterity(), s.getImpulsion(), s.getHeadGame(), s.getFinish(), s.getPassing(), s.getHistorial(), s.getPrecision());
    }

    public Striker(String[] data) {
        super(data);
        Random r = new Random();
        this.precision = (r.nextInt() % 100) + 1;
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    @Override
    public String toString() {
        return "S " +
                super.getName() +
                " num " + super.getNum() +
                " vel " + super.getVelocity() +
                " res " + super.getResistance() +
                " dex " + super.getDexterity() +
                " imp " + super.getImpulsion() +
                " head " + super.getHeadGame() +
                " fin " + super.getFinish() +
                " pass " + super.getPassing() +
                " pre " + this.precision;
    }
    @Override
    public String toStringSkills() {
        return "S " +
                getName() +
                " numero " + getNum() +
                " globalSkill " + globalSkill() +
                " historial=" + super.getHistorial();    }

    @Override
    public Player clone() {
        return new Striker(this);
    }

    @Override
    public int globalSkill() {
        return (int) (0.2 * super.getResistance() + 0.2 * super.getDexterity() + 0.15 * super.getImpulsion() + 0.15 * this.precision + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }

}
