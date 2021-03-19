package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Match {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private Team home;
    private Team away;
    private int time;
    // Construtores

    public Match() {
        this.home = new Team();
        this.away = new Team();
        time = 0;
    }

    public Match(Team t1, Team t2) {
        this.home = t1;
        this.away = t2;
        this.time = 0;
    }


}

