package View;

import Model.Match.Match;
import Model.Match.Point;

import java.util.List;

/**
 * View of one match, except Java Swing part.
 */
public class MatchView {

    public static void simulateOrNot() {
        System.out.println("Do you want to see the simulation (s/n)?");
    }

    public void toZone(String message, Integer left) {
        System.out.println("Choose how many to position: " + message);
        System.out.println("And you only got " + left + " players.");
    }

    public void ReplacePlayers() {
        System.out.println("Do you want to replace players (s/n)?");
    }

}
