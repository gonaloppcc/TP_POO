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

    public Jogador() {
        new Jogador(0, 0, 0, 0, 0, 0, "");
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

    public Jogador(String[] data) {
        new Jogador(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                Integer.parseInt(data[6]), data[7]);
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


    static public class guarda_redes extends Jogador {
        protected int elasticidade;

        public guarda_redes(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial, int elasticidade) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
            this.elasticidade = elasticidade;
        }

        public guarda_redes(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial, int elasticidade) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
            this.elasticidade = elasticidade;
        }

        public guarda_redes(String[] data) {
            new guarda_redes(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), data[7], Integer.parseInt(data[8]));
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

            return (int) (0.2 * (resistencia) + 0.2 * (destreza) + 0.15 * implusao + 0.1 * jogo_de_cabeca + 0.1 * remate + 0.1 * capacidade_de_passe + 0.15 * elasticidade);
        }

    }

    static public class defesa extends Jogador {


        public defesa(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
        public defesa(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial) {

            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
        public defesa(String[] data) {
                new defesa(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6]), data[7]);
            }
        }


    static public class medio extends Jogador {


        public medio(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
        public medio(String[] data) {
            new medio(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), data[7]);
        }
    }

    static public class avancado extends Jogador {
        public avancado(String[] data) {
            super(data);
        }

        public avancado(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String[] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }
    }

    static public class laterais extends Jogador {
        public laterais (String[] data) {
            new laterais (Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), data[7]);
        }
        public int habilidadeGlobal() {
            return (int) ((resistencia) * 0.2 + (destreza));
        }
        public laterais(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }

        public laterais(int resistencia, int destreza, int implusao, int jogo_de_cabeca, int remate, int capacidade_de_passe, String [] historial) {
            super(resistencia, destreza, implusao, jogo_de_cabeca, remate, capacidade_de_passe, historial);
        }

    }
}
