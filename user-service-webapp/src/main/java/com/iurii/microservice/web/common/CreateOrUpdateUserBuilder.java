package com.iurii.microservice.web.common;


import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CreateOrUpdateUserBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrUpdateUserBuilder.class);

    public CreateOrUpdateUserRequest getCreateOrUpdateUserRequest(String id, UserResource userResource) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(userResource.getBirthDate(), dateFormat);
        return CreateOrUpdateUserRequest.builder()
                .userName(userResource.getName())
                .userId(id)
                .birthDate(date)
                .build();
    }
}
