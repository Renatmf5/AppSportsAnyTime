package Objetos;

import org.json.JSONException;
import org.json.JSONObject;

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

    public Time(JSONObject json) {
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
            if (json.has("responsible_phone")) {
                this.setTelefone(json.get("responsible_phone").toString());
            }
            if (json.has("user")) {
                this.setUsuario(new Usuario(json.getJSONObject("user")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
