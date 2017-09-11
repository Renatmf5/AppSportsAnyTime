package Objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Jogador implements Serializable {

    private String id;
    private String nome;
    private String tipoDeJogo;
    private String posicao;
    private String telefone;
    private Endereco endereco;
    private Usuario usuario;

    public Jogador(JSONObject json) {
        try {
            if (json.has("id")) {
                this.setId(json.get("id").toString());
            }
            if (json.has("name")) {
                this.setNome(json.get("name").toString());
            }
            if (json.has("pitch_type")) {
                this.setTipoDeJogo(json.get("pitch_type").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Jogador(String id) {
        this.id = id;
    }

    public Jogador(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Jogador(String id, String nome, String tipoDeJogo) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
    }

    public Jogador(String id, String nome, String tipoDeJogo, String posicao) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.posicao = posicao;
    }

    public Jogador(String id, String nome, String tipoDeJogo, String posicao, String telefone) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.posicao = posicao;
        this.telefone = telefone;
    }

    public Jogador(String id, String nome, String tipoDeJogo, String posicao, String telefone, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.posicao = posicao;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Jogador(String id, String nome, String tipoDeJogo, String posicao, String telefone, Endereco endereco, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.tipoDeJogo = tipoDeJogo;
        this.posicao = posicao;
        this.telefone = telefone;
        this.endereco = endereco;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}