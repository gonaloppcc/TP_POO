package Model.Player;

import java.time.LocalDate;
import java.util.List;

public class Back extends Player {

    public Back(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Back(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Back(String[] data) {
        super(data);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }


}