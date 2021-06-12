package Model.Match;

import Model.InvalidLineExcpetion;
import Model.Team;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores information of a game, but not the necessary information to simulate a game.
 * This has the equivalent information of a text file.
 */
public class MatchRegister implements Serializable {

    private List<Replaces> homeRepl;
    private List<Replaces> awayRepl;
    private LocalDate date;
    private Team homeTeam;
    private Team awayTeam;
    private int scoreHome;
    private int scoreAway;

    /* ------------------------------------- Constructors  ---------------------------------------------------------- */

    public MatchRegister() {
        this.date = LocalDate.of(2010, 10, 10);
        this.homeRepl = new ArrayList<>();
        this.awayRepl = new ArrayList<>();
        this.scoreAway = 0;
        this.scoreHome = 0;
        this.homeTeam = null;
        this.awayTeam = null;
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

    public MatchRegister(String[] line, Team home, Team awayTeam) throws InvalidLineExcpetion {
        try {
            this.homeTeam = home;
            this.awayTeam = awayTeam;
            this.scoreHome = Integer.parseInt(line[2]);
            this.scoreAway = Integer.parseInt(line[3]);
            String[] data = line[4].split("-");
            this.date = LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            this.homeRepl = new ArrayList<>();
            this.awayRepl = new ArrayList<>();
            boolean isItHome = true;
            for (int i = 5; i < line.length; i++) {
                if (line[i].contains("->")) {
                    Replaces one = new Replaces(Integer.parseInt(line[i].split("->")[0]), Integer.parseInt(line[i].split("->")[1]));
                    if (isItHome) this.homeRepl.add(one);
                    else this.awayRepl.add(one);
                }
                if (isItHome && !line[i].contains("->") && this.homeRepl.size() > 0) isItHome = false;
            }
        } catch (Exception e) {
            throw new InvalidLineExcpetion();
        }
    }

    /* ------------------------------------- Getters and Setters ---------------------------------------------------------- */


    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return this.homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return this.awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getScoreHome() {
        return this.scoreHome;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public int getScoreAway() {
        return this.scoreAway;
    }

    public void setScoreAway(int scoreAway) {
        this.scoreAway = scoreAway;
    }

    public void addReplace(int in, int out, boolean home) {
        if (home) this.homeRepl.add(new Replaces(in, out));
        else this.awayRepl.add(new Replaces(in, out));
    }
    /* ------------------------------------- To String ---------------------------------------------------------- */

    @Override
    public String toString() {
        return "MatchRegister{" +
                "date=" + this.date +
                ", homeTeam=" + this.homeTeam.getName() +
                ", awayTeam=" + this.awayTeam.getName() +
                ", scoreHome=" + this.scoreHome +
                ", scoreAway=" + this.scoreAway +
                ", homeRepl=" + this.homeRepl +
                ", awayRepl=" + this.awayRepl +
                '}';
    }
}
