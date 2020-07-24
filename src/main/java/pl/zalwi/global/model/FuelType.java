package pl.zalwi.global.model;

import lombok.Getter;

@Getter
public enum FuelType {
    GASOLINE("Benzyna"),
    DIESEL("Diesel"),
    LPG("Benzyna+LPG"),
    OTHER("Inny");

    private String description;

    FuelType(String description) {
        this.description = description;
    }
}
