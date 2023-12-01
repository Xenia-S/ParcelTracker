package com.example.parceltracker.utils;

import java.util.Random;

public class CodeUtil {

    public static int generateCode() {
        Random random = new Random();
        // Генерируем случайное четырёхзначное число
        int min = 1000;
        int max = 9999;
        return random.nextInt(max - min + 1) + min;
    }
}
