package Objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String name;
    private String email;
    private String password;

    public Usuario(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Usuario(JSONObject json) {
        try {
            if (json.has("id")) {
                this.setId(json.getString("id"));
            }
            if (json.has("name")) {
                this.setName(json.getString("name"));
            }
            if (json.has("email")) {
                this.setEmail(json.getString("email"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
