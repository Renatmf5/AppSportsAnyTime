package Objetos;

/**
 * Created by RenatoMorenoFerreira on 13/08/2017.
 */

public class Jogador extends Pessoa{

    private String nome;
    private String tipoDeJogo;
    private String posicao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoDeJogo() {
        return tipoDeJogo;
    }

    public void setTipoDeJogo(String tipoDeJogo) {
        this.tipoDeJogo = tipoDeJogo;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }
}
