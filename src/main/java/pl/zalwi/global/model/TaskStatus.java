package pl.zalwi.global.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NEW("Nowe"),
    INPROGRESS("W trakcie"),
    DONE("Zakończone");

    private String description;

    TaskStatus(String description) {
        this.description = description;
    }
}
