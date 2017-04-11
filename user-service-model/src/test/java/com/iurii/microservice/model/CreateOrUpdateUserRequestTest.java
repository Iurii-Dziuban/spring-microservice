package com.iurii.microservice.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrUpdateUserRequestTest {

    private static final String NAME = "Iurii";
    //private static final ZonedDateTime BIRTH_DATE= ZonedDateTime.of(1990,4,16,10,0,0,0, TimeZone.getDefault().toZoneId());
    private static final LocalDate BIRTH_DATE= LocalDate.of(1990,4,16);
    private static final String ID = "id";

    @Test
    public void test() {
        CreateOrUpdateUserRequest request = CreateOrUpdateUserRequest.builder()
                .userId(ID)
                .birthDate(BIRTH_DATE)
                .userName(NAME)
                .build();

        assertThat(request.getUserId()).isEqualTo(ID);
        assertThat(request.getUserName()).isEqualTo(NAME);
        assertThat(request.getBirthDate()).isEqualTo(BIRTH_DATE);
    }
}
