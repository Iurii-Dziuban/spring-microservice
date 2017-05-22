package com.iurii.microservice.api.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class RestTemplateBuilder {

    private static final Integer DEFAULT_CONNECTION_TIMEOUT = 60000;
    private static final Integer DEFAULT_READ_TIMEOUT = 60000;

    // Using SimpleClientHttpRequestFactory
    // but maybe in future we should switch to HttpComponentsClientHttpRequestFactory
    // it is more flexible and uses thread pools
    public RestTemplate build() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(dateFormat);

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

        return template;
    }
}
