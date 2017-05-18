package com.iurii.microservice.service.converters;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.entity.User;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserResourceConverter implements UserResourceConverter {

    @Override
    public UserResource convert(User user) {
        UserResource userResource = null;
        if (user != null) {
            userResource = UserResource.builder().name(user.getName()).birthDate(user.getBirthDate().toString())
                    .money(String.valueOf(user.getMoney())).updatedTime(user.getUpdatedTime().toString()).build();
        }
        return userResource;
    }

}