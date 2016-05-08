package com.finanse.model;

public enum LoanStatus {

    APPROVED("1"),
    REJECTION("2");
    private String value;

    LoanStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
