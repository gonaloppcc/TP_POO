package Controller;

import Model.Match.MatchRegister;
import Model.InvalidLineExcpetion;
import Model.Player.Player;
import Model.Status;
import Model.Team;
import View.CheckGameView;
import View.StatusView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class used in the first option of the menu "1) Check current game, manage teams and players?"
 * The program only exits in this class when the user presses button 5.
 */

public class StatusCheckGame {
    private Status status;
    private final Scanner terminal;

    /*------------------------Constructors --------------------------------*/

    public StatusCheckGame() {
        this.status = null;
        this.terminal = new Scanner(System.in);
    }
    public StatusCheckGame(Status status) {
        this.status = status;
        this.terminal = new Scanner(System.in);
    }
    /*------------------------ Public functions ---------------------------*/

    /**
     * Runs the main program.
     * Prints Options and wait for a valid input.
     * @param toStore Status to check.
     * @return Status changed.
     */
    public Status run(Status toStore){
        this.status = toStore;
//        CheckGameView prints = new CheckGameView();
            boolean stayHere = true;
            while (stayHere){
                CheckGameView.printOptions();
              try {
                  switch (Integer.parseInt(this.terminal.nextLine())) {
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
                      case 5: //See History
                          seeHistory();
                          break;
                      case 6:
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

    /*------------------------ Other functions ---------------------------*/


    /**
     * Prints the teams and gets a valid team. It can be used in a lot of contexts.
     * @param message Message for the user to know what is needed.
     * @return Team chosen.
     */
    public Team getTeam(String message){
        if (this.status.getTeams().size() < 1) StatusView.noValidTeam();
        else {
            while (true){
                String possibleTeam = printTeamReturnName(message);
                if (this.status.getTeams().containsKey(possibleTeam.trim())) return this.status.getTeam(possibleTeam.trim());
                else StatusView.InvalidOption();
            }
        }
    return null;
    }
    /*---------------------------- Private Functions ----------------------*/


    private void createTeam(){
        while (true){
            CheckGameView.CreateTeamInsertTeam();
            String possibleName = this.terminal.nextLine();
            if (this.status.getTeams().containsKey(possibleName)) CheckGameView.NameAlreadyTaken();
            else {
                this.status.addTeam(possibleName);
                break;
            }
        }
    }

    private void createPlayer(){
        Team toAdd = getTeam("Insert player");
        while (true){
            CheckGameView.CreatePlayerInsertPlayer();
            String possiblePlayer = this.terminal.nextLine();
            try {
                toAdd.addPlayer(possiblePlayer);
                CheckGameView.SuccessfullOperation("Add in player");
                return;
            } catch (InvalidLineExcpetion e) {
                StatusView.InvalidLine();
            }
        }
    }

    /**
     * Prints all teams with a phrase, and returns what the user inserted.
     * @param message Message for the user to know what is needed.
     * @return
     */
    private String printTeamReturnName(String message){
        CheckGameView.ChooseTeam(message);

        StatusView.chooseTeam(this.status.getTeams().keySet());
        return this.terminal.nextLine();
    }

    /**
     * Prints one team, that is choosen by the player.
      */
    private void seeTeam(){
            CheckGameView.printTeam(getTeam("to check"));
    }



    /**
     * When a team has more than one player with the same name,
     * Prints all and choose one.
     * @param sameName Name of the player to be chosen.
     * @return Player chosen.
     */
    private static Player chooserPlayerManyNames(List<Player> sameName) {
        CheckGameView.playersWithNumber(sameName);
        Scanner terminal = new Scanner(System.in);
        while(true) {
            CheckGameView.playersWithNumber(sameName);
            int number = terminal.nextInt();

            if (number < sameName.size() &&  number > 0) return sameName.get(number);
            StatusView.InvalidOption();
        }
    }

   private Player getPlayer(Team team){
       Player target;
       //Escolhe o jogador que sai
       while (true){

           CheckGameView.printTeam(team, " Write the full name to select.\n");
           CheckGameView.printTeam(team);
           String player = this.terminal.nextLine();
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

    private void seeHistory(){
        while(true){
            CheckGameView.InsertDateToSeeGames();
            String maybeDate = this.terminal.nextLine();
           try {
               String[] splitted = maybeDate.split("/");
//               DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_);
               LocalDate init;
               LocalDate end;
               init = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(splitted[0]));
               end = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(splitted[1]));
               if (init.isAfter(end)) throw new Exception();
               List<MatchRegister> toPrint = status.getGames().values().stream()
                       .filter(x -> getsGamesInBetween(init, end, x)).flatMap(Collection::stream).collect(Collectors.toList());
               CheckGameView.printGames(toPrint);
               return;
           }
            catch (Exception e){
               StatusView.InvalidOption();
            }
           }

    }

    /**
     * Check if the games in toCheck, all with the same date, are between those two dates.
     * Because they are stored by date.
     * @param init
     * @param end
     * @param toCheck List of all the games.
     */
    private boolean getsGamesInBetween(LocalDate init, LocalDate end, List<MatchRegister> toCheck){
        LocalDate one = toCheck.get(0).getDate();
        return one.isAfter(init) && one.isBefore(end);
    }
}


