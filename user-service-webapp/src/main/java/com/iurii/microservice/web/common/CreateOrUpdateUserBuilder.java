package com.iurii.microservice.web.common;


import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class CreateOrUpdateUserBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrUpdateUserBuilder.class);

    public CreateOrUpdateUserRequest getCreateOrUpdateUserRequest(String id, UserResource userResource) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(userResource.getBirthDate());
        } catch (ParseException e) {
            LOGGER.error("date has wrong format", e);
        }
        return CreateOrUpdateUserRequest.builder()
                .userName(userResource.getName())
                .userId(id)
                .birthDate(date)
                .build();
    }
}
