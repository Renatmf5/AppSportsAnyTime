package Objetos;

import java.io.Serializable;

public class PosicaoDisponivelJogo implements Serializable {
    private Jogo jogo;
    private String posicao;

    public PosicaoDisponivelJogo(String posicao) {
        this.posicao = posicao;
    }

    public PosicaoDisponivelJogo(Jogo jogo, String posicao) {
        this.jogo = jogo;
        this.posicao = posicao;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getPosicaoValueStr() {
        switch (this.posicao) {
            case "gol": return "Goleiro";
            case "zag": return "Zagueiro";
            case "ld": return "Lateral-direito";
            case "le": return "Lateral-esquerdo";
            case "vol": return "Volante";
            case "mei": return "Meio-campo";
            case "ata": return "Atacante";
            case "fixo": return "Fixo";
            case "ala": return "Ala";
            case "pivo": return "Pivô";
        }

        return null;
    }
}
