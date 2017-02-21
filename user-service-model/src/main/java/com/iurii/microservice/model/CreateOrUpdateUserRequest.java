package com.iurii.microservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
public class CreateOrUpdateUserRequest {
    private String userId;
    private String userName;
    private Date birthDate;
}
