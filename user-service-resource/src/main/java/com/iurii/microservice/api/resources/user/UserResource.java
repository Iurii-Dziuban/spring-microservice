package com.iurii.microservice.api.resources.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@JsonRootName("")
@AllArgsConstructor
@NoArgsConstructor
public class UserResource extends ResourceSupport {

    @NotNull
    private String name;

    //@Pattern(regexp = "^[A-Z]{3}$")
    private String birthDate;

    private String updatedTime;

    private String money;
}
