package Model.Player;

import java.util.ArrayList;

public class Back extends Player {
    public Back(String[] data) {
        new Back(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
    }

    public int resistance() {
        return (int) ((super.getResistance()) * 0.2 + (super.getDexterity()));
    }

    public Back(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

    public Back(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }

}