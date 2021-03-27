package Model.Player;

import java.time.LocalDate;
import java.util.List;

public class BackWing extends Player {

    public BackWing(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public BackWing(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public BackWing(String[] data) {
        super(data);
    }

    public BackWing(BackWing b) {
        super(b.getName(), b.getBirthDate(), b.getResistance(), b.getDexterity(), b.getImpulsion(), b.getHeadGame(), b.getFinish(), b.getPassing(), b.getHistorial());
    }

    @Override
    public String toString() {
        return "B" +
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
        return new BackWing(this);
    }


    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }


}