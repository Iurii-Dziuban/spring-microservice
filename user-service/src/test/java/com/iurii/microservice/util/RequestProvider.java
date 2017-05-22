package com.iurii.microservice.util;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;

public final class RequestProvider {

    private static final BasicHeader CONTENT_TYPE_JSON = new BasicHeader("content-type", "application/json");

    private RequestProvider() {}

    public static HttpPost getPostRequest(String url, String requestBody) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(url);
        request.addHeader(CONTENT_TYPE_JSON);
        request.setEntity(new StringEntity(requestBody));
        return request;
    }
}
