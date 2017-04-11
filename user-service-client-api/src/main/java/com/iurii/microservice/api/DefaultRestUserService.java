package com.iurii.microservice.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iurii.microservice.api.resources.user.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DefaultRestUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestUserService.class);

    private static final Integer DEFAULT_CONNECTION_TIMEOUT = 5000;
    private static final Integer DEFAULT_READ_TIMEOUT = 60000;

    private final String serviceUri;

    private final RestTemplate restTemplate;

    private final String authHeader;

    public DefaultRestUserService(final String serviceUri, String userName, final String password) {
        this.serviceUri = serviceUri;
        LOGGER.info("Using service uri '{}'", serviceUri);
        authHeader = createAuthHeader(userName, password);
        restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        converter.setObjectMapper(mapper);

        List<HttpMessageConverter<?>> converterList = new ArrayList<>();
        converterList.add(converter);

        final RestTemplate template = new RestTemplate(converterList);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
        factory.setReadTimeout(DEFAULT_READ_TIMEOUT);
        template.setRequestFactory(factory);

        // we don't want exceptions
        template.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                LOGGER.warn("Received error {}", response.getRawStatusCode());
            }
        });


        return template;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory)restTemplate.getRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
    }

    public void setReadTimeout(int readTimeout) {
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory)restTemplate.getRequestFactory();
        factory.setReadTimeout(readTimeout);
    }

    private String createAuthHeader(final String userName, final String password) {
        final String auth = userName + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    public ResponseEntity<Void> createOrUpdate(String id, String mode, UserResource userResource) {
        Assert.notNull(id, "id is mandatory");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        ResponseEntity<Void> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("{serviceUri}/userService/v1/users/{id}?mode={mode}",
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<Void>() {
                    }, serviceUri, id, mode);
        } catch (Exception e) {
            LOGGER.error("Exception from RestTemplate {}", e);
        }

        return responseEntity;
    }

    public ResponseEntity<Void> delete(String id) {
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