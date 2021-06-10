package Model.Player;


import java.time.LocalDate;
import java.util.ArrayList;

public class Defender extends Player {

    public Defender(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Defender(String name, LocalDate birthDate, int num, int velocity,  int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Defender(Defender d) {
        super(d.getName(), d.getBirthDate(),d.getNum(),d.getVelocity(), d.getResistance(), d.getDexterity(), d.getImpulsion(), d.getHeadGame(), d.getFinish(), d.getPassing(), d.getHistorial());
    }

    public Defender(String[] data) {
        super(data);
    }

    @Override
    public String toString() {
        return "D " +
                getName() +
                " num " + super.getNum() +
                " vel " + super.getVelocity() +
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
        return new Defender(this);
    }

    @Override
    public int globalSkill() {

        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing());
    }
}
