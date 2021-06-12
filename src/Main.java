import Controller.CtrlPreStatus;
import Controller.StatusController;
import Model.*;
import View.PreStatusView;
import View.StatusView;

/**
 * Initializates the game, and gives to Status a valid path to one file.
 */
public class Main {

    public static void main(String[] args) {

        PreStatusView novo = new PreStatusView();
        novo.welcomeAndLoad();
        CtrlPreStatus maybeFilePath = new CtrlPreStatus();
        String maybePath = maybeFilePath.readStatus();
        //Status loaded with a file
        if (maybePath != null) {
            Status fromFile = new Status();
            fromFile.loadPath(maybePath);
            //Beggining of interaction with user
            StatusController interacts = new StatusController(fromFile, new StatusView());
            interacts.interactions();
        }
        //Empty Status
        else {
            Status empty = new Status();
            StatusController interacts = new StatusController(empty, new StatusView());
            interacts.interactions();

        }
    }



}
