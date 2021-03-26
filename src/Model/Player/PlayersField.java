package Model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersField {

    private List<Player> benched;

    private List<Player> defender;
    private List<Player> midfield;
    private List<Player> striker;
    private Player goalKeeper;

    //Função que substitui
    //Aqui há encapsulamento, porque acedo diretamente ao seated e assim
    public void replace(Player in, Player out, int posOut) {
        //Está no banco sai
        benched.remove(in);
        //Do campo vai para o banco
        setSeated(out);
        //Tirar do campo
        switch (posOut) {
            case 0:
               break ;
            case 1:
                defender.remove(out);
                break;
            case 2:
                midfield.remove(out);
                break;
            case 3:
                striker.remove(out);
                break;
            default:
                System.out.println("Erro?");
                return;

        }
    }
    //Construtores


    public PlayersField() {
        this.defender = new ArrayList<>();
        this.midfield = new ArrayList<>();
        this.striker = new ArrayList<>();
        this.goalKeeper = null;
    }

    //Getters e Setters
    //0 -> Bola está do frente à baliza de casa
    //1 -> Bola está do lado home
    //2 -> Bola está no meio campo
    //3 -> Bola está no lado visitante
    //4 -> Bola está frente à baliza do visitante
    public List<Player> getPlayersPosition(int pos) {
        switch(pos){
            case 0: return (List<Player>) getGoalKeeper();
            case 1: return getDefender();
            case 2: return getDefender();
            case 3: return getStriker();
            default:
                System.out.println("Erro?");
                return getStriker();
         }
    }


    public List<Player> getDefender() {
        return defender;
    }

    public void setDefender(List<Player> defender) {
        this.defender = defender;
    }
    public void setDefender(Player defender) {
        this.defender.add(defender);
    }

    public List<Player> getMidfield() {
        return midfield;
    }

    public void setMidfield(List<Player> midfield) {
        this.midfield = midfield;
    }
    public void setMidfield(Player midfield) {
        this.midfield.add(midfield);
    }

    public List<Player> getStriker() {
        return striker;
    }

    public void setStriker(List<Player> striker) {
        this.striker = striker;
    }
    public void setStriker(Player striker) {
        this.striker.add(striker);
    }
    public Player getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(Player goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public List<Player> getBenched() {
        return benched;
    }

    public void setBenched(List<Player> benched) {
        this.benched = benched;
    }
    public void setBenched(Player seated) {
        this.benched.add(seated);
    }
}
