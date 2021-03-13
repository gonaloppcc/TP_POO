package Model;

import java.util.Arrays;

public class Jogador {
    protected int resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe;
    protected String[] historial;

    // Construtores


    public Jogador(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
        this.resistencia = resistencia;
        this.destreza = destreza;
        this.implusao = implusao;
        this.jogo_de_cabeca = jogo_de_cabeca;
        this.remate = remate;
        this.capacidade_de_passe = capacidade_de_passe;
        this.historial = historial;
    }

    public Jogador(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial) {
        this.resistencia = resistencia;
        this.destreza = destreza;
        this.implusao = implusao;
        this.jogo_de_cabeca = jogo_de_cabeca;
        this.remate = remate;
        this.capacidade_de_passe = capacidade_de_passe;
        this.historial = new String[1];
        this.historial[0] = historial;
    }


    @Override
    public String toString() {
        return "Jogador{" +
                "resistencia=" + resistencia +
                ", destreza=" + destreza +
                ", implusao=" + implusao +
                ", jogo_de_cabeca=" + jogo_de_cabeca +
                ", remate=" + remate +
                ", capacidade_de_passe=" + capacidade_de_passe +
                ", historial=" + Arrays.toString(historial) +
                '}';
    }


    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getImplusao() {
        return implusao;
    }

    public void setImplusao(int implusao) {
        this.implusao = implusao;
    }

    public int getJogo_de_cabeca() {
        return jogo_de_cabeca;
    }

    public void setJogo_de_cabeca(int jogo_de_cabeca) {
        this.jogo_de_cabeca = jogo_de_cabeca;
    }

    public int getRemate() {
        return remate;
    }

    public void setRemate(int remate) {
        this.remate = remate;
    }

    public int getCapacidade_de_passe() {
        return capacidade_de_passe;
    }

    public void setCapacidade_de_passe(int capacidade_de_passe) {
        this.capacidade_de_passe = capacidade_de_passe;
    }

    public String[] getHistorial() {
        return historial;
    }

    public void setHistorial(String[] historial) {
        this.historial = historial;
    }








    static public class guarda_redes extends Jogador{
        protected int elasticidade;

        public guarda_redes(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial, int elasticidade) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
            this.elasticidade = elasticidade;
        }

        public guarda_redes(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial, int elasticidade) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
            this.elasticidade = elasticidade;
        }


        @Override
        public String toString() {
            return "guarda_redes{" +
                    "resistencia=" + resistencia +
                    ", destreza=" + destreza +
                    ", implusao=" + implusao +
                    ", jogo_de_cabeca=" + jogo_de_cabeca +
                    ", remate=" + remate +
                    ", capacidade_de_passe=" + capacidade_de_passe +
                    ", historial=" + Arrays.toString(historial) +
                    ", elasticidade=" + elasticidade +
                    '}';
        }

        public int getElasticidade() {
            return elasticidade;
        }

        public void setElasticidade(int elasticidade) {
            this.elasticidade = elasticidade;
        }


        public int habilidadeGlobal() {

            return (int) (0.2*(resistencia) + 0.2*(destreza) + 0.15*implusao + 0.1*jogo_de_cabeca + 0.1*remate + 0.1*capacidade_de_passe + 0.15*elasticidade);
        }

    }

    class defesa extends Jogador{


        public defesa(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
    }

    class medio extends Jogador{


        public medio(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
    }

    class avancado extends Jogador{


        public avancado(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
    }

    class laterais extends Jogador{

        public int habilidadeGlobal() {
            return (int) ((resistencia) * 0.2 + (destreza));
        }

        public laterais(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
    }
}
