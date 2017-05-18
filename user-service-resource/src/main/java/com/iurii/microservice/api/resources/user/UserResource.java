package com.iurii.microservice.api.resources.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

@Value
@ToString
@Builder
@JsonRootName("")
@AllArgsConstructor
public class UserResource extends ResourceSupport {

    @NotNull
    private final String name;

    //@Pattern(regexp = "^[A-Z]{3}$")
    private final String birthDate;

    private final String updatedTime;

    private final String money;
}
