package com.iurii.microservice.api.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthHeaderBuilder {

    public String build(final String userName, final String password) {
        final String auth = userName + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

}
