package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Match {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private String teamHome;
    private Equipa home;
      private String teamOutside;
        private Equipa visitado;
  private int time;
    // Construtores

    public Match() {
    this.teamHome = "";
    this.teamOutside = "";
    this.home=new Equipa();
    this.visitada=new Equipa();
      time = 0;
    }

    public Match(String t1, Equipa tt1, String t2, Equipa tt2) {
    this.teamHome = t1;
    this.teamOutside = tt1;
    this.home= t2;
    this.visitada= tt2;
     this.time = 0;
    }
    
    
}

