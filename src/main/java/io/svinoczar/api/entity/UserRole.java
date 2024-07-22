package io.svinoczar.api.entity;

public enum UserRole {
    USER(1, "user"),
    ADMIN(2, "admin");

    private final Integer value;
    private final String name;

    UserRole(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer status() {return value;}

    @Override
    public String toString() {
        return name;
    }
}
