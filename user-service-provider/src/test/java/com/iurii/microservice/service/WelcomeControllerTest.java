package com.iurii.microservice.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class WelcomeControllerTest {

    @Test
    public void test(){
        Assertions.assertThat(new WelcomeController().welcome(null)).isEqualTo("welcome");
    }

}