package Model.Player;


import java.time.LocalDate;
import java.util.List;

public class GoalKeeper extends Player {
    private int elasticity;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public GoalKeeper(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int elasticity) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.elasticity = elasticity;
    }

    public GoalKeeper(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial, int elasticity) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.elasticity = elasticity;
    }

    public GoalKeeper(GoalKeeper g) {
        this(g.getName(), g.getBirthDate(), g.getNum(), g.getVelocity(), g.getResistance(), g.getDexterity(), g.getImpulsion(), g.getHeadGame(), g.getFinish(), g.getPassing(), g.getHistorial(), g.getElasticity());
    }

    public GoalKeeper(String[] data) {
        super(data);
        elasticity = Integer.parseInt(data[9]);
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public int getElasticity() {
        return elasticity;
    }

    public void setElasticity(int elasticity) {
        this.elasticity = elasticity;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */

    @Override
    public String toString() {
        return "G " +
                getName() +
                " numero " + getNum() +
                " vel " + super.getVelocity() +
                " res " + super.getResistance() +
                " dex " + super.getDexterity() +
                " imp " + super.getImpulsion() +
                " head " + super.getHeadGame() +
                " fin " + super.getFinish() +
                " pass " + super.getPassing() +
                //", historial=" + super.getHistorial() +
                " elas " + this.elasticity;
    }

    @Override
    public String toStringSkills() {
        return "G " +
                getName() +
                " num " + getNum() +
                " globalSkill " + globalSkill() +
                " historial=" + super.getHistorial();
    }

    @Override
    public Player clone() {
        return new GoalKeeper(this);
    }


    public int globalSkill() {
        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing() + 0.15 * getElasticity());
    }
}
