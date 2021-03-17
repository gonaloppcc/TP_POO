package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equipa {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private String nome;
    private List<Jogador> equipa;
    private int numberOfPlayers;

    // Construtores

    public Equipa(String nome) {
        this.nome = nome;
        this.numberOfPlayers = 0;
    }

    public Equipa(int numJogadores) {
        this.equipa = new ArrayList<>();
        this.numberOfPlayers = 0;
    }

    public Equipa(List<Jogador> equipa) {
        this.equipa = equipa;
    }

    public Equipa(String nome, List<Jogador> equipa, int numberOfPlayers) {
        this.nome = nome;
        this.equipa = equipa;
        this.numberOfPlayers = numberOfPlayers;
    }

    // Metodos


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public List<Jogador> getEquipa() {
        return equipa;
    }

    public void setEquipa(List<Jogador> equipa) {
        this.equipa = equipa;
    }

    public void addJogador(Jogador jogador) {
        this.equipa.add(jogador);
    }
    public String toString() {
        return "Equipa{" +
                "nome=" + nome + this.getNome() +
                ", Jogadores=" +  Arrays.asList(equipa) +
                ", número de jogadores" + numberOfPlayers +
                '}';
    }


}
