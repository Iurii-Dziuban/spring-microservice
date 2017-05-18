package com.iurii.microservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Builder
public class CreateOrUpdateUserRequest {
    private String userId;
    private String userName;
    private LocalDate birthDate;
    private ZonedDateTime updatedTime;
    private long money;
}
