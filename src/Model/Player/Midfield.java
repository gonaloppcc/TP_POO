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

    public Midfield(String[]data) {
        super(data);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }

}
