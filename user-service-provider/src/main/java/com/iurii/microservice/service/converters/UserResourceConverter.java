package com.iurii.microservice.service.converters;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.entity.User;

public interface UserResourceConverter {

    UserResource convert(User user);

}