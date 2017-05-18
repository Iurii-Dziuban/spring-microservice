package com.iurii.microservice.persistance;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ZonedDateTimeAttributeConverterTest {

    private ZonedDateTimeAttributeConverter converter = new ZonedDateTimeAttributeConverter();

    @Test
    public void convertToDatabaseColumn() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Z"));

        Timestamp expectedTimestamp = Timestamp.valueOf(zonedDateTime.toLocalDateTime());

        Timestamp timestamp = converter.convertToDatabaseColumn(zonedDateTime);

        assertThat(timestamp).isEqualTo(expectedTimestamp);
    }

    @Test
    public void convertToZonedDateTime() {
        LocalDateTime now = LocalDateTime.now();

        Timestamp sqlTimestamp = Timestamp.valueOf(now);

        ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(now, ZoneId.of("Z"));

        ZonedDateTime zonedDateTime = converter.convertToEntityAttribute(sqlTimestamp);
        assertThat(zonedDateTime).isEqualTo(expectedZonedDateTime);
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
