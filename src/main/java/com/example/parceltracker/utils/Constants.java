package com.example.parceltracker.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String LOCAL_DATE_TIME_SERIALIZATION_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Almaty");


    public static final String STATUS_NOT_FOUND = "Статус не найден";
    public static final String STATUS_EXISTS = "Статус уже существует: ";
}
