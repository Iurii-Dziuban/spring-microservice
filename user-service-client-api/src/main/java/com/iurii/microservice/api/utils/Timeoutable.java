package com.iurii.microservice.api.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by iurii.dziuban on 27/04/2017.
 */
public abstract class Timeoutable {

    public void setConnectionTimeout(int connectionTimeout) {
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) getRestTemplate().getRequestFactory();
        factory.setConnectTimeout(connectionTimeout);
    }

    public void setReadTimeout(int readTimeout) {
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) getRestTemplate().getRequestFactory();
        factory.setReadTimeout(readTimeout);
    }

    protected abstract RestTemplate getRestTemplate();

}
