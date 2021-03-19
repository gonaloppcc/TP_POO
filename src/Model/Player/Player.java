package Model.Player;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private int resistance, dexterity, impulsion, headGame, finish, passing;
    private List<String> historial;

    // Construtores


    public Player(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, List<String> historial) {
        this.resistance = resistance;
        this.dexterity = dexterity;
        this.impulsion = impulsion;
        this.headGame = headGame;
        this.finish = finish;
        this.passing = passing;
        this.historial = historial;
    }

    public Player() {
        new Player(0, 0, 0, 0, 0, 0, "");
    }

    public Player(int resistance, int dexterity, int impulsion, int headGame, int finish, int passing, String historial) {
        this.resistance = resistance;
        this.dexterity = dexterity;
        this.impulsion = impulsion;
        this.headGame = headGame;
        this.finish = finish;
        this.passing = passing;
        this.historial = new ArrayList<>();
        this.historial.add(historial);
    }

    public Player(String[] data) {
        new Player(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
    }

    @Override
    public String toString() {
        return "Player{" +
                "resistance=" + resistance +
                ", destreza=" + dexterity +
                ", implusao=" + impulsion +
                ", jogo_de_cabeca=" + headGame +
                ", remate=" + finish +
                ", capacidade_de_passe=" + passing +
                ", historial=" + historial +
                '}';
    }


    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getImpulsion() {
        return impulsion;
    }

    public void setImpulsion(int impulsion) {
        this.impulsion = impulsion;
    }

    public int getHeadGame() {
        return headGame;
    }

    public void setHeadGame(int headGame) {
        this.headGame = headGame;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getPassing() {
        return passing;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public List<String> getHistorial() {
        //List<String> his = new ArrayList<>();
        //his = (List<String>) this.getHistorial().clone();
        return this.historial;
    }

    public void setHistorial(List<String> historial) {
        this.historial = historial;
    }

    public void addHistorial(String team) {
        this.historial.add(team);
    }
}
