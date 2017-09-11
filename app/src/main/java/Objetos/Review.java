package Objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Review implements Serializable {
    private int grade;
    private String description;

    public Review(JSONObject json) {
        try {
            if (json.has("grade")) {
                this.setGrade(json.getInt("grade"));
            }
            if (json.has("description")) {
                this.setDescription(json.get("description").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Review(int grade, String description) {
        this.grade = grade;
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
