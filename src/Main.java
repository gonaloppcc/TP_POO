import Controller.StatusController;
import Model.*;
import Model.DefaultGames.Football;
import Model.Player.GoalKeeper;
import View.StatusView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Football footballGame = new Football();
        Status model = new Status("Football", 11, footballGame.generateTeams());
        StatusView view = new StatusView();
        StatusController controller = new StatusController(model, view);
        //Match teste = new Match(model.getTeams().get(0), model.getTeams().get(1));
        System.out.println(controller.getTeamsName());

        controller.updateView();

        try {
            model.save("save.bin");
        } catch (IOException e) {
            System.out.println("Error saving the game!");
        }

        try {
            model = Status.load("save.bin");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //Match test = new Match("/home/banderitas/Desktop/2_ano_2_sem/POO/TP_POO/src/Files/profFicheiro");

    }

    private static void createGoalKeeper() {
        String name = "Manuel Neuer";
        LocalDate birth = LocalDate.of(1986, 3, 27);
        ArrayList<String> neuerHistorial = new ArrayList<>();
        neuerHistorial.add("Bayern");
        GoalKeeper neuer = new GoalKeeper(name, birth, 96, 87, 87, 88, 95, 96, neuerHistorial, 97);
        System.out.println(neuer.globalSkill());
    }
}
