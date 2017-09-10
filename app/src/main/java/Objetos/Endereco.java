package Objetos;

import java.io.Serializable;

public class Endereco implements Serializable {

    private String id;
    private String address;
    private String city;
    private String state;

    public Endereco(String id, String address, String city, String state) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
