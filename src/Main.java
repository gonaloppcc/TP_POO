import Model.*;
import Model.Player.Goalkeeper;

import java.util.ArrayList;


public class Main { // A main fica aqui???

    public static void main(String[] args) {
        Status s = new Status();
        s.Load("src/Files/teste"); //s.Load("teste");
        createGoalKeeper();

    }

    private static void createGoalKeeper() {
        ArrayList<String> neuerHistorial = new ArrayList<>();
        neuerHistorial.add("Bayern");
        Goalkeeper neuer = new Goalkeeper(96,87,87,88,95,96, neuerHistorial, 97);
        System.out.println(neuer.globalSkill());
    }
}
