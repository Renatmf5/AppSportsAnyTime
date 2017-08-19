package Objetos;

import java.security.KeyStore;

/**
 * Created by RenatoMorenoFerreira on 13/08/2017.
 */

public class Pessoa {

    private String email;
    private KeyStore.PasswordProtection senha;
    private String sexo;
    private int idade;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public KeyStore.PasswordProtection getSenha() {
        return senha;
    }

    public void setSenha(KeyStore.PasswordProtection senha) {
        this.senha = senha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}



