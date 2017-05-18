package com.iurii.microservice.persistance;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateAttributeConverterTest {

    private LocalDateAttributeConverter converter = new LocalDateAttributeConverter();

    @Test
    public void convertToDatabaseColumn() {
        LocalDate zonedDate = LocalDate.now(ZoneId.of("Z"));

        Date expectedDate = Date.valueOf(zonedDate);

        Date date = converter.convertToDatabaseColumn(zonedDate);

        assertThat(date).isEqualTo(expectedDate);
    }

    @Test
    public void convertToZonedDateTime() {
        LocalDate now = LocalDate.now();

        Date sqlTimestamp = Date.valueOf(now);

        LocalDate expectedZonedDate = now;

        LocalDate localDate = converter.convertToEntityAttribute(sqlTimestamp);
        assertThat(localDate).isEqualTo(expectedZonedDate);
    }

    @Test
    public void convertNullToDatabaseColumn() {
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    public void convertNullToZonedDateTime() {
        assertThat(converter.convertToEntityAttribute(null)).isNull();
    }
}
