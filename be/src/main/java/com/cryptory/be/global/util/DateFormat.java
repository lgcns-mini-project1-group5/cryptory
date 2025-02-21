package com.cryptory.be.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
