package Model.DefaultGames;

import Model.Player.*;
import Model.Team;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Football {
    enum playerType {Back, Defender, GoalKeeper, Midfield, Striker}


    //// ---------------> Falta a documentação das v.i.
    private final int ratingMax;
    private final int ratingMin;
    private final int teamNum;
    private final int teamSize; // 4 8 7 8 8
    private final int goalKeepers;
    private final int defenders;
    private final int back;
    private final int midfields;
    private final int strikers;


    /**
     * Class default constructor.
     */
    public Football() {
        this.ratingMax = 100;
        this.ratingMin = 40;
        this.teamNum = 8;
        this.teamSize = 35;
        this.goalKeepers = 4;
        this.defenders = 8;
        this.back = 7;
        this.midfields = 8;
        this.strikers = 8;
    }

    /**
     * Class constructor specifying the following parameters
     * @param ratingMax max rating of a Player
     * @param ratingMin min rating of a Player
     * @param teamNum number of Team's
     * @param teamSize number of Player's in a Team
     * @param goalKeepers number of GoalKeeper's in a Team
     * @param defenders number of Defender's in a Team
     * @param back number of Back's in a Team
     * @param midfields number of Midfield's in a Team
     * @param strikers number of Striker's in a Team
     */
    public Football(int ratingMax, int ratingMin, int teamNum, int teamSize, int goalKeepers, int defenders, int back, int midfields, int strikers) {
        this.ratingMax = ratingMax;
        this.ratingMin = ratingMin;
        this.teamNum = teamNum;
        this.teamSize = teamSize;
        this.goalKeepers = goalKeepers;
        this.defenders = defenders;
        this.back = back;
        this.midfields = midfields;
        this.strikers = strikers;
    }

    /**
     * Generate a list of random teams
     * @return the list of random teams
     */
    public List<Team> generateTeams() {
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamNum; i++) {
            teams.add(generateTeam());
        }
        return teams;
    }

    /**
     * Generate a random team
     * @return the random team
     */
    private Team generateTeam() {
        Team team = new Team(teamSize);
        for (int i = 0; i < goalKeepers; i++) {
            team.addPlayer(generatePlayer(playerType.GoalKeeper));
        }
        for (int i = 0; i < defenders; i++) {
            team.addPlayer(generatePlayer(playerType.Defender));
        }
        for (int i = 0; i < back; i++) {
            team.addPlayer(generatePlayer(playerType.Back));
        }
        for (int i = 0; i < midfields; i++) {
            team.addPlayer(generatePlayer(playerType.Midfield));
        }
        for (int i = 0; i < strikers; i++) {
            team.addPlayer(generatePlayer(playerType.Striker));
        }

        return team;
    }

    /**
     * This method generates a Player with random attributes
     *
     * @param pType The player type
     * @return The random player
     */
    private Player generatePlayer(@NotNull playerType pType) {
        Random r = new Random();
        Player player;
        String name = "Player " + r.nextInt();
        LocalDate birthdate = LocalDate.of(r.nextInt(20) + 1970, r.nextInt(12) + 1,
                r.nextInt(28) + 1);

        switch (pType) {
            case Back -> player = new Back(name, birthdate, playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), "");
            case Striker -> player = new Striker(name, birthdate, playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), "");
            case Defender -> player = new Defender(name, birthdate, playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), "");
            case Midfield -> player = new Midfield(name, birthdate, playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), "");
            case GoalKeeper -> player = new GoalKeeper(name, birthdate, playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax), playerAttribuite(ratingMin, ratingMax),
                    playerAttribuite(ratingMin, ratingMax), "", playerAttribuite(ratingMin, ratingMax));
            default -> throw new IllegalStateException("Unexpected value: " + pType);
        }
        return player;
    }


    /**
     * This method returns a random attribute
     * @param min min value of the random attribute (inclusive)
     * @param max the max value of the random attribute (inclusive)
     * @return The random attribute
     */
    private int playerAttribuite(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
