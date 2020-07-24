package pl.zalwi.login.model;

import lombok.Getter;

@Getter
public enum UserType {
    CHIEF("Szef"),
    ELECTRICIAN("Elektryk"),
    MECHANIC("Mechanik"),
    CARPAINTER("Lakiernik"),
    UPHOLSTERER("Tapicer"),
    TRAINEE("Praktykant"),
    DIAGNOSTICIAN("Diagnosta");

    private String description;

    UserType(String description) {
        this.description = description;
    }
}
