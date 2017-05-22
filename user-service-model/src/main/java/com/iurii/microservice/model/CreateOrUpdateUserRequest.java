package com.iurii.microservice.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Builder
public class CreateOrUpdateUserRequest {
    private final String userId;
    private final String userName;
    private final LocalDate birthDate;
    private final ZonedDateTime updatedTime;
    private final long money;
}
