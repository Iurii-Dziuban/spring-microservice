package com.iurii.microservice.util.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeConverter {

    private static final String DB_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private DateTimeConverter() {}

    public static String convertToDbFormat(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DB_FORMAT).format(dateTime);
    }

    public static String convertToTxnFormat(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DATETIME_FORMAT).format(dateTime);
    }
}
