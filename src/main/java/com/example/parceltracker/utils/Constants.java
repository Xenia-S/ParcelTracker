package com.example.parceltracker.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String LOCAL_DATE_TIME_SERIALIZATION_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Almaty");


    public static final String STATUS_NOT_FOUND = "Статус не найден";
    public static final String STATUS_EXISTS = "Статус уже существует: ";
    public static final String STATUS_DELETED = "Статус уже удалён";
    public static final String STATUS_NOT_DELETED = "Статус не удалён";


    public static final String PARCEL_NOT_FOUND = "Отправление не найдено";
    public static final String NO_RECEIVE_CODE = "Не передан код получения";
    public static final String RECEIVE_CODE_NOT_CORRECT = "Код получения неверный";
}
