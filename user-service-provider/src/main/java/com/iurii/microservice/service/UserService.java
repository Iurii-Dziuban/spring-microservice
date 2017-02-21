package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;

import java.util.Date;

public interface UserService {

    ServiceResponseCode createUser(String id, String name, Date birthDate);

    ServiceResponseCode updateUser(String id, String name, Date birthDate);

    ServiceResponseCode deleteUser(String id);

    UserResource getRestriction(String id);
}