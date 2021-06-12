package View;

import Model.Player.Player;
import java.util.List;
import java.util.Scanner;

public class ChosingPlayers {

    /**
     * Method which prints all the players of a certain list that is given as an argument.
     * @param list
     * @param thing
     */

    public void printPlayers(List<Player> list, String thing) {
        StringBuilder sb = new StringBuilder();
        sb.append("Equipa: ").append(thing);
        int i = 0;
        for (Player atual : list) {
            sb.append("\nJogador número ").append(i).append(": ").append(atual.toString());
            i++;
        }
        sb.append("\nNow you choose the players you want by number.\n");
        System.out.println(sb);
    }

    /**
     * Returns an array with the number of players.
     * @param array
     * @param total
     */

    public void choosePlayers(int[] array, int total) {
        Scanner myInput = new Scanner(System.in);
        for (int i = 0; i < total; i++) {
            System.out.println("\nPlayer number: ");
            array[i] = myInput.nextInt();
        }
        System.out.println("Players read: ");
        for (int i = 0; i < total; i++) {
            System.out.println(array[i] + " | ");
        }
    }

    /**
     * Places the question of whether we should print the whole team at a time or just the ones in a respective position.
     * @return
     */

    public boolean printAllQuestion() {
        Scanner myInput = new Scanner(System.in);
        char read;
        boolean result = false;
        boolean cicle = true;
        while (cicle) {
            System.out.println("\nAre you going to put players in not very good positions? [y/n] ");
            read = myInput.next().charAt(0);
            if (read == 'y' || read == 'Y') {
                result = true;
                cicle = false;
            }
            if (read == 'n' || read == 'N') {
                result = false;
                cicle = false;
            }

        }
        return result;
    }

    /**
     * Places the questions of how many players you want per role.
     * @param num_positions
     */

    public void ChooseStrategy(int num_positions) {
        System.out.println("Choose your strategy, there are " + num_positions + " positions!");
    }

    /**
     * Places the strategy based on role.
     * @param strategy
     */

    public void stategyChoose(int[] strategy) {
        Scanner myInput = new Scanner(System.in);
        System.out.println("Defense: ");
        strategy[0] = myInput.nextInt();
        System.out.println("Midfield: ");
        strategy[1] = myInput.nextInt();
        System.out.println("attack: ");
        strategy[2] = myInput.nextInt();
    }

    /**
     * Returns the position that the user specified the player to be in.
     * @param whatToDo
     * @return
     */

    public int whereIsPlayer(String whatToDo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Diz em que posição está o jogador que queres ").append(whatToDo).append(positions());
        Scanner myInput = new Scanner(System.in);
        return myInput.nextInt();
    }

    private String positions() {
        return "\n\tSelect position of the field:" +
                "\n\t         Baliza de casa 0" +
                "\n\t          Lado de casa 1" +
                "\n\t          Meio campo 2" +
                "\n\t         Baliza de Fora 3" +
                "\n\t          Lado de casa 4\n";
    }
}
