package com.iurii.microservice.util.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeConverter {

    private static final String DB_FORMAT = "dd-mm-yyyy HH:mm:ss";
    private static final String TXN_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private DateTimeConverter() {}

    public static String convertToDbFormat(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(DB_FORMAT).format(dateTime);
    }

    public static String convertToTxnFormat(ZonedDateTime dateTime) {
        return DateTimeFormatter.ofPattern(TXN_FORMAT).format(dateTime);
    }
}
