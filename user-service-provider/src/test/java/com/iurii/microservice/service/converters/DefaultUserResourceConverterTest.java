package com.iurii.microservice.service.converters;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.entity.User;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DefaultUserResourceConverterTest {

    private DefaultUserResourceConverter converter = new DefaultUserResourceConverter();

    @Test
    public void convertBusinessCaseRestriction() {
        User user = User.builder()
                .id("1")
                .name("iurii")
                .birthDate(new Date(1990, 4, 16))
                .build();

        UserResource result = converter.convert(user);

        assertThat(result, is(notNullValue()));
        assertThat(result.getBirthDate(), is(notNullValue()));
        assertThat(result.getName(), is("iurii"));
    }

}
