package Model.Player;

import java.util.ArrayList;

public class Lateral extends Player {
    public Lateral(String[] data) {
        new Lateral(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
    }

    public int resistance() {
        return (int) ((super.getResistance()) * 0.2 + (super.getDexterity()));
    }

    public Lateral(int resistance, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial) {
        super(resistance, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
    }

    public Lateral(int resistance, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, ArrayList<String> historial) {
        super(resistance, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
    }

}