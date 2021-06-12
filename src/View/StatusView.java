package View;

import Model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StatusView {

    public static void chooseTeam(Set<String> teams, String phrase){
        System.out.println( phrase + myPrint(new ArrayList<>(teams)));
    }
    public static void chooseTeam(Set<String> teams){
        System.out.println( myPrint(new ArrayList<>(teams)));
    }
    private static String myPrint(List<String> teams){
            for (int i = 5; i < teams.size(); i+= 5)
                teams.set(i, teams.get(i).concat(" \n "));
            return String.join(" // ", teams);
    }

    public void printStatus(String gameName, Map<String, Team> teams) {
        System.out.printf("""
                You are playing %s       
                You have the currently teams %s
                """, gameName, teams);

    }
    public void SaveOrLoad(){
        //System.out.println("Choose the path to save the file (it will be Stored");
        System.out.println("\\\"Load\\\"   or \\\"Save\\\"?");
    }

    public void Path(){
        System.out.println("Choose the path to the file");
    }

    public void wrongPath(){
        System.out.println("The path is wrong");
    }

    public void IOerror(){
        System.out.println("Error in IO");
    }

    public void printOptions() {
        System.out.print("""
                What do you want to do?       
                1) Check current game, manage teams and players?
                2) Play a random Game?
                3) Save Game or Load (in binary File)?
                4) Exit
                5) See credits
                """);
    }


    public static void InvalidOption(){
        System.out.println("Wrong choice, try again");
    }

    public static void InvalidLine(){
        System.out.println("There was an invalid line in file");
    }

    public void EndGame(){
        System.out.println("Exiting....");
    }

    public void randomOrChoose(){
        System.out.println("Do you want to choose who you are going to play (s/n)?");
    }
    public static void noValidTeam() {
        System.out.println("There is no valid team");
    }

    public static void numberOfInvalidLines(int num){
        System.out.println("The file has "+num+ " invalid lines");
    }
}
