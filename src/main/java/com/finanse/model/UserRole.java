package com.finanse.model;

public enum UserRole {

    ADMIN("1"),
    USER("2");
    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
