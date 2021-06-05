package Model.Match;
//Coisas para fazer, importante
/*
Nos getter e setter meter clones, se não é apenas o endereço que vai e pode dar problemas
Meter os enum a funcionar para ele poder usar aqui os enum, caso contrário é muito chato
 */
import Model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersField {
    List<PlayerField> playersPlaying;
    List<PlayerField> playersBench;

/*

    //Função que substitui
    //Aqui há encapsulamento, porque acedo diretamente ao seated e assim
    public void replace(Player in, Player out, int posOut) {
        //Está no banco sai
        benched.remove(in);
        //Do campo vai para o banco
        setBenched(out);
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
    public void move(Player startP, int startZ, int endZ){
        switch(startZ){
            case 0: leaveZone(startP, Zones.GOAL);
                SETZone(startP, Zones.GOAL );
            break;
            case 1: leaveZone(startP, Zones.DEFENSE);
                SETZone(startP, Zones.DEFENSE );
                break;
            case 2: leaveZone(startP, Zones.MIDDLE);
                SETZone(startP, Zones.MIDDLE );
                break;
            
            case 3: leaveZone(startP, Zones.OPPOSITE);
                SETZone(startP, Zones.OPPOSITE );
                break;
            default:
                System.out.println("Mas, queres tirar o guarda-redes e meter onde óóóóó...");

    };*/
    //Construtores


    public PlayersField(List<PlayerField> playersPlaying, List<PlayerField> playersBench) {
        this.playersPlaying = playersPlaying;
        this.playersBench  = playersBench;
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
    public Player getBenched(int i) {
        return benched.get(i);
    }

    public void leaveBench(Player in){
        this.getBenched().remove(in);
    }
   /* private void leaveZone(Player out, Zones zone){
            switch (zone) {
                case Zones.DEFENSE:
                    this.getDefender().remove(out);
                    break;
                case Zones.MIDDLE:
                    this.getMidfield().remove(out);
                    break;
                case Zones.OPPOSITE:
                    this.getStriker().remove(out);
                    break;
                case Zones.GOAL:
                    this.setGoalKeeper(null);
                    break;
            }
        }
        private void SETZone(Player out, Zones zone){
            switch (zone) {
                case Zones.DEFENSE:
                    this.setDefender(out);
                    break;
                case Zones.MIDDLE:
                    this.setMidfield(out);
                    break;
                case Zones.OPPOSITE:
                    this.setStriker(out);
                    break;
                case Zones.GOAL:
                    this.setGoalKeeper(out);
                    break;
            }
        }

    */
    public void setBenched(List<Player> benched) {
        this.benched = benched;
    }
    public void setBenched(Player seated) {
        this.benched.add(seated);
    }
}
