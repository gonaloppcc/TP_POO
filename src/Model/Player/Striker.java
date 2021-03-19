package Model.Player;


import java.util.ArrayList;

public class Striker extends Player {
    public Striker(String[] data) {
        super(data);
    }

    public Striker(int resistance, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, ArrayList<String> historial) {
        super(resistance, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
    }
}
