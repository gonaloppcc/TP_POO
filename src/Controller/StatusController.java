package Controller;

import Model.Status;
import Model.Team;
import View.SlideTextSwing;
import View.StatusView;
import java.io.IOException;
import java.util.*;

/**
 * Main controller of the game.
 * Manages the different options of the game.
 */
public class StatusController {
    private Status model;
    private final StatusView view;
    private final Scanner terminal;


    /*------------------------------- Constructors ------------------------*/

    public StatusController(Status model, StatusView view) {
        this.model = model;
        this.view = view;
        this.terminal = new Scanner(System.in);
    }

    /*------------------------------- Main functon ------------------------*/


    public void interactions() {
        if (this.model.getTeams().size() > 0) chooseTeam();
        while (true) {
            this.view.printOptions();
            String num = this.terminal.nextLine();
            try {
                switch (Integer.parseInt(num)) {
                    case 1: //Verificar jogo, talvez trocar jogadores
                        StatusCheckGame novo = new StatusCheckGame();
                        this.model = novo.run(this.model.clone());
                        break;
                    case 2: //Jogar random
                        playOption();

                        break;
                    case 3: //Salvar ficheiro
                        saveLoadOption();
                        break;
                    case 4: //End Game
                        SlideTextSwing noveo = new SlideTextSwing();
                        this.view.EndGame();
                        return;
                    default:
                        StatusView.InvalidOption();
                }
            } catch (NumberFormatException e) {
                StatusView.InvalidLine();
            }
            //if (Integer.parseInt(num) == 5)
        }
    }

    /*------------------------------- Other functions ------------------------*/

    /**
     * Sets the favorite team of the player.
     * This team is used for the confrontations, option 2 of the menu, because it is the home team.
     */
    private void chooseTeam() {
        boolean validTeam = false;
        if (this.model.getTeams().size() < 1) {
            StatusView.noValidTeam();
            return;
        }
        while (!validTeam) {
            //   System.out.print("\033[H\033[2J");
            //   System.out.flush();
            StatusView.chooseTeam(this.model.getTeams().keySet(), "Please choose your favorite team (write name):");
            String teamChoosen = this.terminal.nextLine();
            if (teamChoosen.equals("default")) {
                List<String> valuesList = new ArrayList<>(this.model.getTeams().keySet());
                this.model.setPlayerTeam(valuesList.get(0));
                return;
            }
            if (this.model.getTeams().containsKey(teamChoosen.trim())) {
                this.model.setPlayerTeam(teamChoosen);
                validTeam = true;
            } else StatusView.InvalidOption();
        }
    }

    /**
     * Third option of the menu.
     */
    public void saveLoadOption() {
        while (true) {
            this.view.SaveOrLoad();
            String choice = this.terminal.nextLine();
            if (choice.trim().equals("Exit")) return;
            if (choice.trim().equals("Save")) {
                this.view.Path();
                try {
                    this.model.save(this.terminal.nextLine());
                    return;
                } catch (IOException e) {
                    this.view.IOerror();
                }
            }
            if (choice.trim().equals("Load")) {
                this.view.Path();
                try {
                    this.model = Status.load(this.terminal.nextLine());
                    System.out.println("Load");
                    return;
                } catch (IOException e) {
                    this.view.IOerror();
                } catch (ClassNotFoundException e) {
                    this.view.wrongPath();
                }
            }
            StatusView.InvalidOption();
        }

    }

    /**
     * Second option of the menu.
     * It asks some questions before calling the Match.
     */
    private void playOption() {
        if (this.model.getTeams().size() < 2) {
            StatusView.noValidTeam();
            return;
        }
        //If the opposite team is choosen or not.
        this.view.randomOrChoose();
        Team opposite;

        while (true) {
            String option = this.terminal.nextLine();
            if (option.trim().toLowerCase(Locale.ROOT).equals("s")) {
                StatusCheckGame toChoose = new StatusCheckGame(this.model);
                opposite = toChoose.getTeam("To play against");
                break;
            }
            if (option.trim().toLowerCase(Locale.ROOT).equals("n")) {
                Random rand = new Random();
                List<Team> teams = new ArrayList<>(this.model.getTeams().values());
                //Só existe uma equipa, joga contra ele próprio
                if (teams.size() == 1) opposite = teams.get(0);
                else {
                    int rand_num = rand.nextInt(teams.size() - 1);
                    teams.remove(this.model.getTeam(this.model.getPlayerTeam()));
                    opposite = teams.get(rand_num);
                }
                break;
            }
            StatusView.InvalidLine();
        }
        if (opposite.getPlayers().size() < 12) {
            StatusView.invalidTeam();
            return;
        }
        MatchController simulation = new MatchController(this.model.getTeam(this.model.getPlayerTeam()), opposite, this.model.getPlayersPerTeam());
        this.model.addMatch(simulation.getGame());
    }

}
