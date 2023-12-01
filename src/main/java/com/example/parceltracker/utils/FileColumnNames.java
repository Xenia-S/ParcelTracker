package com.example.parceltracker.utils;

public enum FileColumnNames {
    IDENTIFIER("Идентификатор"),
    SENDER("Отправитель"),
    RECIPIENT("Получатель"),
    RECIPIENT_ADDRESS("Адрес получателя"),
    DATE_SENT("Дата отправления"),
    DATE_PLANNED_DELIVERY("Планируемая дата доставки"),
    WEIGHT("Вес отправления");

    private final String columnName;

    FileColumnNames(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
