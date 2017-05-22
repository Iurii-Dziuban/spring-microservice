package com.iurii.microservice.api;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.api.utils.AuthHeaderBuilder;
import com.iurii.microservice.api.utils.RestTemplateBuilder;
import com.iurii.microservice.api.utils.Timeoutable;
import com.iurii.microservice.web.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

public class DefaultRestUserService extends Timeoutable implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestUserService.class);

    private final String serviceUri;

    private final RestTemplate restTemplate;

    private final String authHeader;

    public DefaultRestUserService(final String serviceUri, String userName, final String password) {
        this.serviceUri = serviceUri;
        LOGGER.info("Using service uri '{}'", serviceUri);
        authHeader = new AuthHeaderBuilder().build(userName, password);
        restTemplate = new RestTemplateBuilder().build();
    }

    @Override
    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public ResponseEntity<?> createOrUpdate(String id, String mode, UserResource userResource) {
        Assert.notNull(id, "id is mandatory");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        ResponseEntity<Void> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("{serviceUri}/userService/v1/users/{id}?mode={mode}",
                    HttpMethod.POST,
                    new HttpEntity<>(userResource, headers),
                    new ParameterizedTypeReference<Void>() {
                    }, serviceUri, id, mode);
        } catch (Exception e) {
            LOGGER.error("Exception from RestTemplate {}", e);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Assert.notNull(id, "id is mandatory");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        ResponseEntity<Void> responseEntity;
        try {
            responseEntity = restTemplate.exchange("{serviceUri}/userService/v1/users/{id}",
                    HttpMethod.DELETE,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Void>() {
                    }, serviceUri, id);
        } catch (Exception e) {
            LOGGER.error("Exception from RestTemplate {}", e);
            return null;
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<UserResource> get(String id) {
        Assert.notNull(id, "id is mandatory");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        ResponseEntity<UserResource> responseEntity;
        try {
            responseEntity = restTemplate.exchange("{serviceUri}/userService/v1/users/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<UserResource>() {
                    }, serviceUri, id);
        } catch (Exception e) {
            LOGGER.error("Exception from RestTemplate {}", e);
            return null;
        }

        return responseEntity;
    }
}