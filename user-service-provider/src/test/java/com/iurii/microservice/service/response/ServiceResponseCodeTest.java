package com.iurii.microservice.service.response;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 05/04/2017.
 */
public class ServiceResponseCodeTest {

    @Test
    public void test() {

        assertThat(ServiceResponseCode.OK.getCode()).isEqualTo(200);
        assertThat(ServiceResponseCode.BAD_REQUEST.getCode()).isEqualTo(400);
        //assertThat(ServiceResponseCode.CREATED.getCode()).isEqualTo(201);
        //assertThat(ServiceResponseCode.FORBIDDEN.getCode()).isEqualTo(403);
        assertThat(ServiceResponseCode.NOT_FOUND.getCode()).isEqualTo(404);
        assertThat(ServiceResponseCode.values().length).isEqualTo(3);
        assertThat(ServiceResponseCode.valueOf("OK")).isEqualByComparingTo(ServiceResponseCode.OK);
    }
}