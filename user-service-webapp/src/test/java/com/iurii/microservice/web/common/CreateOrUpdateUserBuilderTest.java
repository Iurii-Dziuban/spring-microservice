package com.iurii.microservice.web.common;


import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrUpdateUserBuilderTest {

    private static final String IURII = "iurii";
    private static final String BIRTH_DATE = "1990-04-16";
    private static final String UPDATED_TIME = "2015-12-24T18:21:05Z";
    private static final String MONEY = "1234";
    public static final String ID = "id";

    private CreateOrUpdateUserBuilder builder = new CreateOrUpdateUserBuilder();

    @Test
    public void test() {
        UserResource userResource = UserResource.builder().birthDate(BIRTH_DATE)
                .money(MONEY).updatedTime(UPDATED_TIME).name(IURII).build();

        CreateOrUpdateUserRequest userRequest = builder.getCreateOrUpdateUserRequest(ID, userResource);

        assertThat(userRequest.getUserId()).isEqualTo(ID);
        assertThat(userRequest.getMoney()).isEqualTo(Long.valueOf(MONEY));
        assertThat(userRequest.getUserName()).isEqualTo(IURII);
        assertThat(userRequest.getUpdatedTime()).isEqualTo(ZonedDateTime.parse(UPDATED_TIME));
        assertThat(userRequest.getBirthDate()).isEqualTo(LocalDate.parse(BIRTH_DATE));
    }
}
