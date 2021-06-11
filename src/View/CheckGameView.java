package View;

import Model.Player.Player;
import Model.Team;

import java.util.List;

public class CheckGameView {
    public static void printOptions() {
        System.out.print("""
                What do you want to do?       
                1) Create Team
                2) Create player
                3) Check team
                4) Exchange player 
                5) Go back to previous menu
                """);
    }

    public static void CreateTeamInsertTeam() {
        System.out.println("Insert the team name");
    }

    public static void NameAlreadyTaken() {
        System.out.println("This name is alrey taken, choose another one, please");
    }

    public static void CreatePlayerInsertPlayer() {
        System.out.print("""
                To create a player, please insert the characteristics in the following order:
                Guarda-Redes:<Nome>,<NumeroCamisola>,<Velocidade>,<Resistência>,<Destreza>,<Impulsão>,<Cabeça>,<Remate>,<Passe>,<Elasticidade>
                Defesa:<Nome>,<NumeroCamisola>,<Velocidade>,<Resistência>,<Destreza>,<Impulsão>,<Cabeça>,<Remate>,<Passe>
                Lateral:<Nome>,<NumeroCamisola>,<Velocidade>,<Resistência>,<Destreza>,<Impulsão>,<Cabeça>,<Remate>,<Passe>,<Cruzamento>
                Medio:<Nome>,<NumeroCamisola>,<Velocidade>,<Resistência>,<Destreza>,<Impulsão>,<Cabeça>,<Remate>,<Passe>,<Recuperacao>
                Avancado:<Nome>,<NumeroCamisola>,<Velocidade>,<Resistência>,<Destreza>,<Impulsão>,<Cabeça>,<Remate>,<Passe>
                """);
    }

    public static void ChooseTeam(String msg) {
        System.out.println("Choose team to " + msg + ":");
    }

    public static void SuccessfullOperation(String msg) {
        System.out.println(msg + " done");
    }

    public static void printTeam(Team toPrint) {
        System.out.println(toPrint.toStringSkillsAndHistory());
    }
    public static void playersWithNumber(List<Player> sameName){
        System.out.println(concatWithNumbers(sameName));
    }

    private static String concatWithNumbers(List<Player> list){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) res.append(i+")"+list.get(i));
        return res.toString();
    }
    private static String myPrint(List<String> teams){
        for (int i = 5; i < teams.size(); i+= 5)
            teams.set(i, teams.get(i).concat(" \n "));
        return String.join(" // ", teams);
    }
}

