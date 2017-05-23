package com.iurii.microservice.service.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by iurii.dziuban on 23/05/2017.
 */
public class NotFoundExceptionTest {

    public static final String MESSAGE = "hello";

    @Test
    public void test() {
        NotFoundException exception = new NotFoundException(MESSAGE);

        Assertions.assertThat(exception.getMessage()).isEqualTo(MESSAGE);
    }
}
