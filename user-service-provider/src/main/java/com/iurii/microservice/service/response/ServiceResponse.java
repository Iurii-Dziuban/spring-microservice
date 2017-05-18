package com.iurii.microservice.service.response;

import com.iurii.microservice.api.resources.user.Messages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by iurii.dziuban on 03/05/2017.
 */
@RequiredArgsConstructor
@Getter
public class ServiceResponse {
    private final ServiceResponseCode responseCode;
    private final Messages messages;
}
