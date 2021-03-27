package Model.Player;

import java.time.LocalDate;
import java.util.List;

public class Midfield extends Player {

    public Midfield(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Midfield(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Midfield(Midfield m) {
        super(m.getName(), m.getBirthDate(), m.getResistance(), m.getDexterity(), m.getImpulsion(), m.getHeadGame(), m.getFinish(), m.getPassing(), m.getHistorial());
    }

    public Midfield(String[] data) {
        super(data);
    }

    public Midfield(Defender m) {
        super(m.getName(), m.getBirthDate(), m.getResistance(), m.getDexterity(), m.getImpulsion(), m.getHeadGame(), m.getFinish(), m.getPassing(), m.getHistorial());
    }

    @Override
    public String toString() {
        return "M" +
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
        return new Midfield(this);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }

}
