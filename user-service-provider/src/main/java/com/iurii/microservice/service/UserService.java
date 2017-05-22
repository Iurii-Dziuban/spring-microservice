package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.service.response.ServiceResponseCode;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface UserService {

    ServiceResponseCode createUser(String id, String name, LocalDate birthDate, ZonedDateTime updatedTime, long money);

    ServiceResponseCode updateUser(String id, String name, LocalDate birthDate, ZonedDateTime updatedTime, long money);

    ServiceResponseCode updateAddAmount(String id, long amount);

    ServiceResponseCode deleteUser(String id);

    UserResource getUser(String id);
}