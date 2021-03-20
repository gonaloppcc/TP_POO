package Model.Player;


import java.util.ArrayList;

public class Defender extends Player {

    public Defender(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, ArrayList<String> historial) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
    public Defender(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {

        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
    public Defender(String[] data) {
        new Defender(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
    }
}
