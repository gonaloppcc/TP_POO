package Model.Player;

import java.time.LocalDate;
import java.util.List;

public class Midfield extends Player {
    private int recovery;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Midfield(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int recovery) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.recovery = recovery;
    }

    public Midfield(String[] data) {
        super(data);
        this.recovery = Integer.parseInt(data[9]);
    }

    public Midfield(Midfield x) {
        this(x.getName(), x.getBirthDate(), x.getNum(), x.getVelocity(), x.getResistance(), x.getDexterity(), x.getImpulsion(), x.getHeadGame(), x.getFinish(), x.getPassing(), x.getHistorial(), x.getRecovery());
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    @Override
    public String toString() {
        return "M " +
                getName() +
                " num " + super.getNum() +
                " vel " + super.getVelocity() +
                " res " + super.getResistance() +
                " dex " + super.getDexterity() +
                " imp " + super.getImpulsion() +
                " head " + super.getHeadGame() +
                " fin " + super.getFinish() +
                " pass " + super.getPassing() +
                " recovery " + this.recovery;
    }

    @Override
    public Player clone() {
        return new Midfield(this);
    }

    @Override
    public int globalSkill() {
        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }
}
