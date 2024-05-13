package com.example.app.data.enums;

public enum Severity {

    CRITICAL("치명적인 장애"),
    HIGH("심각한 장애"),
    MEDIUM("일반 장애"),
    LOW("사소한 장애");

    private final String description;
    Severity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}