package Controller;


import Model.Status;
import Model.Team;
import View.StatusView;

import java.util.List;
import java.util.stream.Collectors;

public class StatusController {
    private final Status model;
    private final StatusView view;

    public StatusController(Status model, StatusView view) {
        this.model = model;
        this.view = view;
    }

    public List<String> getTeamsName() {
        return model.getTeams().stream().map(Team::getName).collect(Collectors.toList());
    }

    public void updateView() {
        view.printStatus(model.getGameName(), model.getTeams());
    }
}
