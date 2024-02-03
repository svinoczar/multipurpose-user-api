package io.svinoczar.api.entity;

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
