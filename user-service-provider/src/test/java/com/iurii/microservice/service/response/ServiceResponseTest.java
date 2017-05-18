package com.iurii.microservice.service.response;

import com.iurii.microservice.api.resources.user.Messages;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 05/04/2017.
 */
public class ServiceResponseTest {

    @Test
    public void test() {
        Messages messages = new Messages("message");
        ServiceResponse serviceResponse = new ServiceResponse(ServiceResponseCode.OK, messages);

        assertThat(serviceResponse.getMessages()).isEqualTo(messages);
        assertThat(serviceResponse.getResponseCode()).isEqualTo(ServiceResponseCode.OK);
    }

}
