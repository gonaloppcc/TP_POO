package Model.Match;

import Model.Player.*;
import Model.Team;
import View.ChosingPlayers;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Match implements Serializable {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    Cada jogo tem uma certa duração (tima).
    Existem golos de ambas as equipas
    A bola pode estar na posse de uma ou outra equipa e pode estar em 3 zonas distintas do campo.
    **/
    // Data do encontro
    LocalDate date;

    // Equipas completas,
    private Team homeTeam;
    private Team awayTeam;

    // Jogadores no campo
    private PlayersField playersHome;
    private PlayersField playersAway;

    // Tempo jogado
    private int time;

    // Pontos
    private int scoreHome;
    private int scoreAway;

    // false -> home tem a bola
    // true -> Visitante tem a bola
    private boolean posse_bola;

    // pos_ball is a point in the field.
    private Point pos_ball;

    public Match(LocalDate date, Team homeTeam, Team awayTeam, PlayersField playersHome, PlayersField playersAway, int time, int scoreHome, int scoreAway, boolean posse_bola, Point pos_ball) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.playersHome = playersHome;
        this.playersAway = playersAway;
        this.time = time;
        this.scoreHome = scoreHome;
        this.scoreAway = scoreAway;
        this.posse_bola = posse_bola;
        this.pos_ball = pos_ball;
    }

    public Match() {
    }
}


