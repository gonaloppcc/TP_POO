package Model.Player;

public class Midfield extends Player {


    public Midfield(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        super(resistance, dexterity, impulsion, headGame, finish, passing, historial);
    }
    public Midfield(String[]data) {
        new Midfield(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
    }
}
