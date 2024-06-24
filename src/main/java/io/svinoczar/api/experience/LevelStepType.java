package io.svinoczar.api.experience;

public enum LevelStepType {
    MONO ("mono"),
    LINEAR ("linear"),
    EXP ("exp"),
    CUSTOM ("custom");

    private final String description;

    LevelStepType (String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
