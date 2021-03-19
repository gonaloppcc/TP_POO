package Model.Player;


import java.util.ArrayList;

public class Goalkeeper extends Player {
    private int elasticity;

    public Goalkeeper(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial, int elasticity) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
        this.elasticity = elasticity;
    }

    public Goalkeeper(int resistance, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial, int elasticity) {
        super(resistance, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        this.elasticity = elasticity;
    }

    public Goalkeeper(String[] data) {
        new Goalkeeper(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7], Integer.parseInt(data[8]));
    }

    @Override
    public String toString() {
        return "Goalkeeper{" +
                "resistance=" + super.getResistance() +
                ", destreza=" + super.getDexterity() +
                ", implusao=" + super.getImpulsion() +
                ", jogo_de_cabeca=" + super.getHeadGame() +
                ", remate=" + super.getFinish() +
                ", capacidade_de_passe=" + super.getPassing() +
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
