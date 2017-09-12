package Objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Lugar implements Serializable {

    protected int id;
    protected String nome;
    protected String descricao;
    protected Endereco endereco;

    public Lugar(JSONObject json) {
        try {
            if (json.has("id")) {
                this.setId(json.getInt("id"));
            }
            if (json.has("name")) {
                this.setNome(json.getString("name"));
            }
            if (json.has("description")) {
                this.setDescricao(json.getString("description"));
            }
            if (json.has("address")) {
                this.setEndereco(new Endereco(json.getJSONObject("address")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
