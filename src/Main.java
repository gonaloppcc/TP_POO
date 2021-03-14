import Model.*;

public class Main { // A main fica aqui???

    public static void main(String[] args) {
        Status s = new Status();
        s.Load("Files/teste"); //s.Load("teste");
        createGoalKeeper("Bayern");

    }

    private static void createGoalKeeper(String Team) {
        String[] neuerHistorial = {Team};
        Jogador.guarda_redes neuer = new Jogador.guarda_redes(96,87,87,88,95,96, neuerHistorial, 97);
        System.out.println(neuer.habilidadeGlobal());
    }
}
