package com.iurii.microservice.persistance;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Converter for ZonedDateTime assuming format is in UTC
 * latest JPA does not support Java 8 Date Time API yet
 */
@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return (zonedDateTime == null ? null : Timestamp.valueOf(zonedDateTime.toLocalDateTime()));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return sqlTimestamp == null ? null : ZonedDateTime.of(sqlTimestamp.toLocalDateTime(), ZoneId.of("Z"));
    }
}
