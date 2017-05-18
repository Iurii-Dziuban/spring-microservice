package com.iurii.microservice.service.converters;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.entity.User;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultUserResourceConverterTest {

    private static final String NAME = "Iurii";
    private static final ZonedDateTime TIME= ZonedDateTime.of(1990,4,16,10,0,0,0, TimeZone.getDefault().toZoneId());
    private static final LocalDate BIRTH_DATE= LocalDate.of(1990,4,16);

    private DefaultUserResourceConverter converter = new DefaultUserResourceConverter();

    @Test
    public void convertBusinessCaseRestriction() {
        User user = User.builder()
                .id("1")
                .name(NAME)
                .birthDate(BIRTH_DATE)
                .updatedTime(TIME)
                .money(1234)
                .build();

        UserResource result = converter.convert(user);

        assertThat(result).isNotNull();
        assertThat(result.getBirthDate()).isEqualTo(BIRTH_DATE.toString());
        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getUpdatedTime()).isEqualTo(TIME.toString());
        assertThat(result.getMoney()).isEqualTo("1234");
    }

}
