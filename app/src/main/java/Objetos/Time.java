package Objetos;

import java.io.Serializable;
import java.util.Date;

public class Time implements Serializable {
    private String id;
    private String nome;
    private String tipoDeJogo;
    private String telefone;
    private Usuario usuario;
    private Endereco endereco;

    public Time() {}

    public Time(String id, String nome, String tipoDeJogo, String telefone, Usuario usuario, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.telefone = telefone;
        this.usuario = usuario;
        this.endereco = endereco;
    }

    public Time(String nome, String tipoDeJogo, String telefone, Usuario usuario, Endereco endereco) {

        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.telefone = telefone;
        this.usuario = usuario;
        this.endereco = endereco;
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
