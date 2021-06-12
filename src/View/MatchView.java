package View;

import Model.Match.Match;
import Model.Match.Point;

import java.util.List;

public class MatchView {

    public  static void simulateOrNot(){
        System.out.println("Do you want to see the simulation (s/n)?");
        System.out.println("É gira...");
    }

    public void toZone(String message, Integer left){
        System.out.println("Choose how many to position: "+message);
        System.out.println("And you only got "+left+" players.");
    }

    public void ReplacePlayers(){
        System.out.println("Do you want to replace players (s/n)?");
    }

    public void CampoTodo(List<Point> home, List<Point> away, Point ball){
        DisplayGraphics toPrint = new DisplayGraphics(home, away, ball);
        toPrint.campo(home,away, ball);
    }
}
