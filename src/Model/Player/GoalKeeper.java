package Model.Player;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalKeeper extends Player {
    private int elasticity;

    public GoalKeeper(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int elasticity) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.elasticity = elasticity;
    }

    public GoalKeeper(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial, int elasticity) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.elasticity = elasticity;
    }


    public GoalKeeper(String[] data) {
        super(data);
    }

    @Override
    public String toString() {
        return "GoalKeeper{" +
                "resistance=" + super.getResistance() +
                ", dexterity=" + super.getDexterity() +
                ", impulsion=" + super.getImpulsion() +
                ", headGame=" + super.getHeadGame() +
                ", finish=" + super.getFinish() +
                ", passing=" + super.getPassing() +
                ", historial=" + super.getHistorial() +
                ", elasticidade=" + this.elasticity +
                '}';
    }

    public int getElasticity() {
        return elasticity;
    }

    public void setElasticity(int elasticity) {
        this.elasticity = elasticity;
    }


    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing() + 0.15 * getElasticity());
    }

}
