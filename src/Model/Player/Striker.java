package Model.Player;


import java.time.LocalDate;
import java.util.List;

public class Striker extends Player {
    public Striker(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Striker(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Striker(Striker s) {
        super(s.getName(), s.getBirthDate(), s.getResistance(), s.getDexterity(), s.getImpulsion(), s.getHeadGame(), s.getFinish(), s.getPassing(), s.getHistorial());
    }

    public Striker(String[] data) {
        super(data);
    }

    @Override
    public String toString() {
        return "S " +
                getName() +
                " res " + super.getResistance() +
                " dex " + super.getDexterity() +
                " imp " + super.getImpulsion() +
                " head " + super.getHeadGame() +
                " fin " + super.getFinish() +
                " pass " + super.getPassing();
        //", historial=" + super.getHistorial() +
    }

    @Override
    public Player clone() {
        return new Striker(this);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }

}
