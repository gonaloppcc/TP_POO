package Model.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Defender extends Player {
    private int recorvery;

    /*------------------------------------------------Constructors----------------------------------------------------*/

    public Defender(String name, LocalDate birthDate, int num, int velocity, int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial, int recorvery) {
        super(name, birthDate, num, velocity, resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.recorvery = recorvery;
    }

    public Defender(Defender d) {
        this(d.getName(), d.getBirthDate(), d.getNum(), d.getVelocity(), d.getResistance(), d.getDexterity(), d.getImpulsion(), d.getHeadGame(), d.getFinish(), d.getPassing(), d.getHistorial(), d.getRecorvery());
    }

    public Defender(String[] data) {
        super(data);
        Random r = new Random();
        this.recorvery = (r.nextInt() % 100) + 1;
    }

    /*------------------------------------------ Getters e Setters ---------------------------------------------------*/

    public int getRecorvery() {
        return recorvery;
    }

    public void setRecorvery(int recorvery) {
        this.recorvery = recorvery;
    }

    /* ------------------------------------- Other methods ---------------------------------------------------------- */
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
                " pass " + super.getPassing() +
                " rec " + this.recorvery;
    }

    @Override
    public String toStringSkills() {
        return "D " +
                getName() +
                " num " + super.getNum() +
                " globalSkill " + globalSkill() +
                " historial=" + super.getHistorial() ;

    }

    @Override
    public Player clone() {
        return new Defender(this);
    }

    @Override
    public int globalSkill() {
        return (int) (0.2 * (super.getResistance()) + 0.2 * (super.getDexterity()) + 0.15 * super.getImpulsion() + 0.1 * super.getHeadGame() + 0.1 * super.getFinish() + 0.1 * super.getPassing() + 0.15 * this.recorvery);
    }
}
