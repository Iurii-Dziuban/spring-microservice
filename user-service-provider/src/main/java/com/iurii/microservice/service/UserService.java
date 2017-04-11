package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;

import java.time.LocalDate;

public interface UserService {

    ServiceResponseCode createUser(String id, String name, LocalDate birthDate);

    ServiceResponseCode updateUser(String id, String name, LocalDate birthDate);

    ServiceResponseCode deleteUser(String id);

    UserResource getRestriction(String id);
}