package com.iurii.microservice.service.converters;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.entity.User;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultUserResourceConverterTest {

    private DefaultUserResourceConverter converter = new DefaultUserResourceConverter();

    @Test
    public void convertBusinessCaseRestriction() {
        User user = User.builder()
                .id("1")
                .name("iurii")
                .birthDate(LocalDate.of(1990, 4, 16))
                .build();

        UserResource result = converter.convert(user);

        assertThat(result).isNotNull();
        assertThat(result.getBirthDate()).isNotNull();
        assertThat(result.getName()).isEqualTo("iurii");
    }

}
