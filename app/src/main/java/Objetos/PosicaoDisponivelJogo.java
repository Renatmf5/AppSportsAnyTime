package Objetos;

public class PosicaoDisponivelJogo {
    private Jogo jogo;
    private String posicao;

    public PosicaoDisponivelJogo(String posicao) {
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
}
