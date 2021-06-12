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
        if (model.getTeams().size() > 0) chooseTeam();
        while (true) {
            view.printOptions();
            String num = terminal.nextLine();
            try {
                switch (Integer.parseInt(num)) {
                    case 1: //Verificar jogo, talvez trocar jogadores
                        StatusCheckGame novo = new StatusCheckGame();
                        this.model = novo.run(model.clone());
                        break;
                    case 2: //Jogar random
                        playOption();

                        break;
                    case 3: //Salvar ficheiro
                        saveLoadOption();
                        break;
                    case 4: //End Game
                        view.EndGame();
                        return;
                    case 5:
                        SlideTextSwing noveo = new SlideTextSwing();
                        break;
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
     * Sets the favourite team of the player.
     * This team is used to the confrontations, option 2 of the menu, because it is the home team.
     */
    private void chooseTeam() {
        boolean validTeam = false;
        if (model.getTeams().size() < 1) {
            StatusView.noValidTeam();
            return;
        }
        while (!validTeam) {
            //   System.out.print("\033[H\033[2J");
            //   System.out.flush();
            StatusView.chooseTeam(model.getTeams().keySet(), "Please choose your favourite team (write name):");
            String teamChoosen = terminal.nextLine();
            if (teamChoosen.equals("meh")) {
                List<String> valuesList = new ArrayList<>(model.getTeams().keySet());
                model.setPlayerTeam(valuesList.get(0));
                return;
            }
            if (model.getTeams().containsKey(teamChoosen.trim())) {
                model.setPlayerTeam(teamChoosen);
                validTeam = true;
            } else StatusView.InvalidOption();
        }
    }

    /**
     * Third option of the menu.
     */
    public void saveLoadOption() {
        while (true) {
            view.SaveOrLoad();
            String choice = terminal.nextLine();
            if (choice.trim().equals("Save")) {
                view.Path();
                try {
                    model.save(terminal.nextLine());
                    return;
                } catch (IOException e) {
                    view.IOerror();
                }
            }
            if (choice.trim().equals("Load")) {
                view.Path();
                try {
                    this.model = Status.load(terminal.nextLine());
                    System.out.println("Load");
                    return;
                } catch (IOException e) {
                    view.IOerror();
                } catch (ClassNotFoundException e) {
                    view.wrongPath();
                }
            }
            StatusView.InvalidOption();
        }

    }

    /**
     * Second option of the menu
     * It asks some questions before calling the Match.
     */
    private void playOption() {
        if (model.getTeams().size() < 2) {
            StatusView.noValidTeam();
            return;
        }
        //If the opposite team is choosen or not.
        view.randomOrChoose();
        Team opposite;

        while (true) {
            String option = terminal.nextLine();
            if (option.trim().toLowerCase(Locale.ROOT).equals("s")) {
                StatusCheckGame toChoose = new StatusCheckGame(model);
                opposite = toChoose.getTeam("To play against");
                break;
            }
            if (option.trim().toLowerCase(Locale.ROOT).equals("n")) {
                Random rand = new Random();
                List<Team> teams = new ArrayList<>(model.getTeams().values());
                //Só existe uma equipa, joga contra ele próprio
                if (teams.size() == 1) opposite = teams.get(0);
                else {
                    int rand_num = rand.nextInt(teams.size() - 1);
                    teams.remove(model.getTeam(model.getPlayerTeam()));
                    opposite = teams.get(rand_num);
                }
                break;
            }
            StatusView.InvalidLine();
        }
        MatchController simulation = new MatchController(model.getTeam(model.getPlayerTeam()), opposite, model.getPlayersPerTeam());
        model.addMatch(simulation.getGame());
    }

}
