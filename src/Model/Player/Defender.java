package Model.Player;


import java.time.LocalDate;
import java.util.ArrayList;

public class Defender extends Player {

    public Defender(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial) {
        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
    public Defender(String name, LocalDate birthDate, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {

        super(name, birthDate, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
    public Defender(String[] data) {
        super(data);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }
}
