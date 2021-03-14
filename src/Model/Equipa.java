package Model;

import java.util.Arrays;

public class Equipa {
    /*
    Cada equipa é constituída por um conjunto fixo de jogadores,
    sendo que em cada jogo podem jogar N jogadores titulares e utilizarem-se M suplentes.
    **/
    private String nome;
    private Jogador[] equipa;
    private int numberOfPlayers;

    // Construtores

    public Equipa(String nome) {
        this.nome = nome;
        this.numberOfPlayers = 0;
    }

    public Equipa(int numJogadores) {
        this.equipa = new Jogador[numJogadores];
        this.numberOfPlayers = 0;
    }

    public Equipa(Jogador[] equipa) {
        this.equipa = equipa;
    }

    public Equipa(String nome, Jogador[] equipa, int numberOfPlayers) {
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

    public Jogador[] getEquipa() {
        return equipa;
    }

    public void setEquipa(Jogador[] equipa) {
        this.equipa = equipa;
    }

    public void addJogador(Jogador jogador) {
        if (numberOfPlayers == 0 || this.equipa.length == numberOfPlayers) {
            Jogador[] arr = new Jogador[numberOfPlayers*2+1];
            if (numberOfPlayers == 0) {
            arr[0] = jogador;
            this.setEquipa(arr);
            }
            else {
                System.arraycopy(arr, 0, this.equipa, 0, numberOfPlayers*2+1);
            }
            numberOfPlayers++;
        } else {
            this.equipa[numberOfPlayers++] = jogador; // Fazer clone aqui!!!!!!!!!!!!!!
        }
    }
    public String toString() {
        return "Equipa{" +
                "nome=" + nome + this.getNome() +
                ", Jogadores=" +  Arrays.toString(this.getEquipa()) +
                ", número de jogadores" + numberOfPlayers +
                '}';
    }


}
