package View;

import Model.Match.Match;
import Model.Match.Point;

import java.util.List;

public class MatchView {
    public void toZone(String message, Integer left){
        System.out.println("Choose how many to positio: "+message);
        System.out.println("And you only got "+left+" players.");
    }
    public void CampoTodo(List<Point> home, List<Point> away){
        DisplayGraphics toPrint = new DisplayGraphics(home, away);
        toPrint.campo(home,away);
    }
}
