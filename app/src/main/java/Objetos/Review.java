package Objetos;

import java.io.Serializable;

public class Review implements Serializable {
    private int grade;
    private String description;

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
