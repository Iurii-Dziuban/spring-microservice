package com.iurii.microservice.api;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.iurii.microservice.api.resources.user.UserResource;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

;

public class DefaultRestUserServiceTest {
    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(WireMockConfiguration.wireMockConfig().dynamicPort());

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private int port;

    private DefaultRestUserService defaultRestUserService;

    @Before
    public void init() {
        port = wireMockRule.port();
        defaultRestUserService = new DefaultRestUserService(String.format("http://localhost:%s", port), "user", "user");
    }

    @Test
    public void shouldSuccessfullyGetLimit() throws Exception {
        givenThat(get(urlEqualTo("/userService/v1/users/5"))
                .willReturn(
                        aResponse().
                                withStatus(200).
                                withHeader("Content-Type", "application/json").
                                withBodyFile("body.json")));

        ResponseEntity<UserResource> response = defaultRestUserService.get("5");

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResource userResource = response.getBody();

        assertThat(userResource).isNotNull();
        assertThat(userResource.getBirthDate()).isNotNull();
        assertThat(userResource.getName()).isEqualTo("iurii");
        assertThat(userResource.getUpdatedTime()).isEqualTo("2015-12-24T18:21:05Z");
        assertThat(userResource.getMoney()).isEqualTo("1234");
    }


    @Test
    public void shouldSuccessfullyCreateLimit() throws Exception {
        givenThat(post(urlEqualTo("/userService/v1/users/5?mode=set"))
                .willReturn(
                        aResponse().
                                withStatus(200).
                                withHeader("Content-Type", "application/json")));

        UserResource userResource = UserResource.builder().name("iurii").birthDate("1990-04-16").build();

        ResponseEntity<?> response = defaultRestUserService.createOrUpdate("5", "set", userResource);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldSuccessfullyUpdateLimit() throws Exception {
        givenThat(post(urlEqualTo("/userService/v1/users/5?mode=add"))
                .willReturn(
                        aResponse().
                                withStatus(200).
                                withHeader("Content-Type", "application/json")));

        UserResource restrictionResource = UserResource.builder().name("iurii").birthDate("1990-04-16").build();

        ResponseEntity<?> response = defaultRestUserService.createOrUpdate("5", "add", restrictionResource);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldSuccessfullyDeleteLimit() throws Exception {
        givenThat(delete(urlEqualTo("/userService/v1/users/5"))
                .willReturn(
                        aResponse().
                                withStatus(200).
                                withHeader("Content-Type", "application/json")));

        ResponseEntity<?> response = defaultRestUserService.delete("5");

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void shouldThrowExceptionAndBeNullValue() throws Exception {
        defaultRestUserService.setReadTimeout(1000);
        defaultRestUserService.setConnectionTimeout(5000);

        givenThat(post(urlEqualTo("/userService/v1/users/5?mode=set"))
                .willReturn(
                        aResponse().
                                withFixedDelay(2000).
                                withStatus(200).
                                withHeader("Content-Type", "application/json")));

        UserResource restrictionResource = UserResource.builder().name("iurii").birthDate("1990-04-16")
                .updatedTime("2015-12-24T18:21:05Z").money("1234").build();

        ResponseEntity<?> response = defaultRestUserService.createOrUpdate("5", "set", restrictionResource);

        assertThat(response).isNull();
    }
}