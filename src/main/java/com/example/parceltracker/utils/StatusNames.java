package com.example.parceltracker.utils;

public enum StatusNames {

    SENT("Отправлено"),
    DELIVERED("Доставлено"),
    RECEIVED("Получено");

    private final String name;

    StatusNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
