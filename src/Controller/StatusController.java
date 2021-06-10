package Controller;


import Model.Match.Match;
import Model.Status;
import Model.Team;
import View.SlideTextSwing;
import View.StatusView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatusController {
    private Status model;
    private StatusView view;
    private Scanner terminal;

    public StatusController(Status model, StatusView view) {
        this.model = model;
        this.view = view;
        this.terminal = new Scanner(System.in);
    }

    public List<String> getTeamsName() {
        return model.getTeams().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public void updateView() {
        view.printStatus(model.getGameName(), model.getTeams());
    }

    public void interactions (){
        chooseTeam();
        boolean exit = false;
        while (!exit){
            view.printOptions();
            String num = terminal.nextLine();
            try {
                switch (Integer.parseInt(num)) {
                    case 1: //Verificar jogo, talvez trocar jogadores
                        StatusCheckGame novo = new StatusCheckGame();
                        this.model = novo.run(model.clone());
                        System.out.println("1");
                        break;
                    case 2: //Jogar random
                        playOption();
                        System.out.println("2");

                        break;
                    case 3: //Salvar ficheiro
                        saveLoadOption();
                        System.out.println("3");
                        break;
                    case 4:
                        view.EndGame();
                        return;
                    case 5:
                        SlideTextSwing noveo = new SlideTextSwing();
                        //Depois não faz bem o exit
                        break;
                    default:
                        view.InvalidOption();
                }
            }
            catch  (NumberFormatException e){
                StatusView.InvalidLine();
            }
            //if (Integer.parseInt(num) == 5)
        }
    }
    private void chooseTeam(){
           boolean validTeam = false;
        while (!validTeam){
         //   System.out.print("\033[H\033[2J");
         //   System.out.flush();
            view.chooseTeam(model.getTeams().keySet(), "Please choose your favourite team (write name):");
            String teamChoosen = terminal.nextLine();
            if (teamChoosen.equals("meh")){
                List<String> valuesList = new ArrayList<String>(model.getTeams().keySet());
                model.setPlayerTeam(valuesList.get(0));
                return;
            }
                if (model.getTeams().containsKey(teamChoosen.trim()))
                {
                    model.setPlayerTeam(teamChoosen);
                    validTeam = !validTeam;
                }
                else view.InvalidOption();
        }
    }
    public void saveLoadOption(){
        view.SaveOrLoad();
        boolean validChoice = false;
        while (!validChoice){
            String choice = terminal.nextLine();
            if (choice.trim().equals("Save")) {
                view.Path();
                try {
                    model.save(terminal.nextLine());
                } catch (IOException e) {
                    view.IOerror();
                }
            }
            if (choice.trim().equals("Load")) {
                view.Path();
                try {
                    this.model = Status.load(terminal.nextLine());
                } catch (IOException e) {
                        view.IOerror();
                } catch (ClassNotFoundException e) {
                    view.wrongPath();
                }
            }
        }

    }
    private void playOption() {
        view.randomOrChoose();
        Team opposite;
        while (true) {
            String option = terminal.nextLine();
            if (option.trim().toLowerCase(Locale.ROOT).equals("s")) {
                //Caso escolha contra quem jogar
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
                    int rand_num = rand.nextInt(teams.size()-1);
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
