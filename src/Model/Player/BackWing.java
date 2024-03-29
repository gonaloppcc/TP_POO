package Model.Player;

import java.time.LocalDate;
import java.util.List;

public class BackWing extends Player {
    private int crossing;

    public BackWing(String name, LocalDate birthDate, int num, int velocity,  int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int crossing) {
        super(name, birthDate, num, velocity,  resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.crossing = crossing;
    }

    public BackWing(String name, LocalDate birthDate, int num, int velocity , int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial, int crossing) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.crossing = crossing;
    }

    public BackWing(String[] data) {
        super(data);
        this.crossing = Integer.parseInt(data[9]);
    }

    public BackWing(BackWing b) {
        super(b.getName(), b.getBirthDate(), b.getNum(), b.getVelocity(), b.getResistance(), b.getDexterity(), b.getImpulsion(), b.getHeadGame(), b.getFinish(), b.getPassing(), b.getHistorial());
        this.crossing = b.crossing;
    }

    @Override
    public String toString() {
        return "B " +
                getName() +
                " num " + super.getNum() +
                " vel " + super.getVelocity() +
                " res " + super.getResistance() +
                " dex " + super.getDexterity() +
                " imp " + super.getImpulsion() +
                " head " + super.getHeadGame() +
                " fin " + super.getFinish() +
                " pass " + super.getPassing() +
                " cross " + crossing;
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