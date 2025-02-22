package com.cryptory.be.global.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat{

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    // 최근 거래 시간인 timestamp 포맷팅
    public static String formatTimestamp(long timestamp) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("UTC"));
        return dateTime.format(TIMESTAMP_FORMATTER);
    }

    public static String formatNewsDate(String inputDate) throws ParseException {
        // 입력 날짜 형식 정의
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

        // 출력 날짜 형식 정의
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 입력 문자열을 Date 객체로 파싱
        java.util.Date date = inputFormat.parse(inputDate);

        // Date 객체를 원하는 형식의 문자열로 변환
        return outputFormat.format(date);
    }
}
