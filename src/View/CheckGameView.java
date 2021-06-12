package View;

import Model.Match.MatchRegister;
import Model.Player.Player;
import Model.Team;

import java.util.List;
import java.util.stream.Collectors;

/**
 * View of the CheckGame class.
 */
public class CheckGameView {
    public static void printOptions() {
        System.out.print("""
                What do you want to do?       
                1) Create Team
                2) Create player
                3) Check team
                4) Exchange player 
                5) See history 
                6) Go back to previous menu
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
        System.out.println("Choose team to " + msg + " :\n");
    }

    public static void SuccessfullOperation(String msg) {
        System.out.println(msg + " done");
    }

    public static void printTeam(Team toPrint) {
        if (toPrint != null) System.out.println(toPrint.toStringSkillsAndHistory());
    }
    public static void playersWithNumber(List<Player> sameName){
        System.out.println(concatWithNumbers(sameName));
    }

    private static String concatWithNumbers(List<Player> list){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) res.append(i+")"+list.get(i));
        return res.toString();
    }

    /**
     * Concatenate one list of string to one final string, with changing lines.
     * @param line List of String to print.
     * @param num Number of elements per line.
     * @return
     */
    public static String myPrint(List<String> line, int num){
        for (int i = num; i < line.size(); i+= num)
            line.set(i, line.get(i).concat(" \n "));
        return String.join(" // ", line);
    }

    public static void InsertDateToSeeGames(){
        System.out.println("Please insert a date to see the games in between" +
                "\nThe format is 'year-month-day/year-month-day' (two digits for month)");
    }

    public static void printGames(List<MatchRegister> x){
        System.out.println("Those are the games in between\n" +
                myPrint(x.stream().map(MatchRegister::toString).collect(Collectors.toList()),1));
    }


}


