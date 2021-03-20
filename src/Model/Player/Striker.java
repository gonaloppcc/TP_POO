package Model.Player;


import java.util.ArrayList;

public class Striker extends Player {
    public Striker(String[] data) {
        super(data);
    }

    public Striker(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
}
