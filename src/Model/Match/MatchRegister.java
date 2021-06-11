package Model.Match;

import Model.Team;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class MatchRegister implements Serializable {
    //Variáveis de instância
    private LocalDate date;
    private Team homeTeam;
    private Team awayTeam;

    private int scoreHome;
    private int scoreAway;

    List<Replaces> homeRepl;
    List<Replaces> awayRepl;

    public MatchRegister() {
        this.date = LocalDate.of(2010, 10,10);
        homeRepl = new ArrayList<>();
        awayRepl = new ArrayList<>();
        scoreAway = 0;
        scoreHome = 0;
        homeTeam = null;
        awayTeam = null;
    }

    public MatchRegister(LocalDate date, Team homeTeam, Team awayTeam, int scoreHome, int scoreAway, List<Replaces> homeRepl, List<Replaces> awayRepl) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.homeRepl = homeRepl;
        this.awayRepl = awayRepl;
    }

    public MatchRegister(String[] line, Team home, Team awayTeam) {

        homeTeam = home;
        this.awayTeam = awayTeam;
        scoreHome = Integer.parseInt(line[2]);
        scoreAway = Integer.parseInt(line[3]);
       // DateTimeFormatterBuilder formatter = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4)
        //       .appendValue(ChronoField.MONTH_OF_YEAR, 2)
        //        .appendValue(ChronoField.DAY_OF_MONTH, 2);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatter);
        //       date = LocalDate.parse(line[4], formatter.toFormatter());
        //Não estou a guardar os jogadores
        String[] data = line[4].split("-");
        date = LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        homeRepl = new ArrayList<>();
        awayRepl = new ArrayList<>();
        boolean isItHome = true;
        for (int i = 5; i < line.length; i++) {
            if (line[i].contains("->")) {
                Replaces one = new Replaces(Integer.parseInt(line[i].split("->")[0]), Integer.parseInt(line[i].split("->")[1]));
                if (isItHome) homeRepl.add(one);
                else awayRepl.add(one);
            }
            if (isItHome && !line[i].contains("->") && homeRepl.size() > 0)  isItHome = !isItHome;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
        return scoreAway;
    }

    public void setScoreAway(int scoreAway) {
        this.scoreAway = scoreAway;
    }

    @Override
    public String toString() {
        return "MatchRegister{" +
                "date=" + date +
                ", homeTeam=" + homeTeam.getName() +
                ", awayTeam=" + awayTeam.getName() +
                ", scoreHome=" + scoreHome +
                ", scoreAway=" + scoreAway +
                ", homeRepl=" + homeRepl +
                ", awayRepl=" + awayRepl +
                '}';
    }
}
