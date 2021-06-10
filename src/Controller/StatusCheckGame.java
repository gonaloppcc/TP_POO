package Controller;

import Model.NotValidException;
import Model.Player.Player;
import Model.Status;
import Model.Team;
import View.CheckGameView;
import View.StatusView;

import java.util.List;
import java.util.Scanner;

/**
 * Class used in the first option of the menu "1) Check current game, manage teams and players?"
 * The program only exits this class when the user press buttom 5.
 */

public class StatusCheckGame {
    private Status status;
    private final Scanner terminal;

    /*------------------------Constructors --------------------------------*/

    public StatusCheckGame() {
        this.status = null;
        terminal = new Scanner(System.in);
    }
    public StatusCheckGame(Status status) {
        this.status = status;
        terminal = new Scanner(System.in);
    }
    /*------------------------ Public functions ---------------------------*/

    /**
     * Runs the main program
     * Prints Options and wait for a valid input
     * @param toStore
     * @return
     */
    public Status run(Status toStore){
        status = toStore;
//        CheckGameView prints = new CheckGameView();
            boolean stayHere = true;
            while (stayHere){
                CheckGameView.printOptions();
              try {
                  switch (Integer.parseInt(terminal.nextLine())) {
                      case 1: //Create Team
                          createTeam();
                          break;
                      case 2: //Create player
                          createPlayer();
                          break;
                      case 3: //Check team
                          seeTeam();
                          break;
                      case 4: //Change player
                          changePlayer();
                          break;
                      case 5:
                          stayHere = false;//Go back
                          break;
                      default:

                  }
              }
              catch (NumberFormatException e){
                  StatusView.InvalidLine();
              }

            }
        return toStore;
    }

    /**
     * Print teams and gets a valid team
     * @param message
     * @return
     */
    public Team getTeam(String message){
        while (true){
            String possibleTeam = printTeamReturnName(message);
            if (status.getTeams().containsKey(possibleTeam.trim())) return status.getTeam(possibleTeam.trim());
            else StatusView.InvalidOption();
        }
    }
    /*---------------------------- Private Functions ----------------------*/


    private void createTeam(){
        while (true){
            CheckGameView.CreateTeamInsertTeam();
            String possibleName = terminal.nextLine();
            if (status.getTeams().containsKey(possibleName)) CheckGameView.NameAlreadyTaken();
            else {
                status.addTeam(possibleName);
                break;
            }
        }
    }

    private void createPlayer(){
        Team toAdd = getTeam("insert player");
        while (true){
            CheckGameView.CreatePlayerInsertPlayer();
            String possiblePlayer = terminal.nextLine();
            try {
                toAdd.addPlayer(possiblePlayer);
                CheckGameView.SuccessfullOperation("Addin player");
                return;
            } catch (NotValidException e) {
                StatusView.InvalidLine();
            }
        }
    }

    /**
     * Prints all teams with a phrase, and returns what the user inserted
     * @param message
     * @return
     */
    private String printTeamReturnName(String message){
        CheckGameView.ChooseTeam(message);

        StatusView.chooseTeam(status.getTeams().keySet());
        return terminal.nextLine();
    }

    /**
     * Prints one team, that is choosen by the player
      */
    private void seeTeam(){
            CheckGameView.printTeam(getTeam("to check"));
    }



    /**
     * When a team has more than one player with the same name
     * Prints all and choose one
     * @param sameName
     * @return
     */
    private Player chooserPlayerManyNames(List<Player> sameName) {
        CheckGameView.playersWithNumber(sameName);
        int option;
        while(true) {
            CheckGameView.playersWithNumber(sameName);
            Integer number = terminal.nextInt();

            if (number < sameName.size() &&  number > 0) return sameName.get(number);
            StatusView.InvalidLine();
        }
    }
   private Player getPlayer(Team team){
       Player target;
       //Escolhe o jogador que sai
       while (true){
           CheckGameView.printTeam(team);
           String player = terminal.nextLine();
           List<Player> sameName = team.getPlayer(player);
            if (sameName.size() > 0) {
                if (sameName.size() == 1) target = sameName.get(0);
                else target = chooserPlayerManyNames(sameName);
                return target;

            }
            else StatusView.InvalidOption();

           }

       }

    private void changePlayer(){
            Team origin = getTeam("from where it comes");
            Player moved = getPlayer(origin);
            moved.addHistorial(origin.getName());
            Team destin = getTeam("to where he goest");
            destin.addPlayer(moved);
        }
    }


