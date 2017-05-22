package com.iurii.microservice.util;

import com.iurii.microservice.util.properties.ITProperties;

public final class UrlBuilder {

    private static String baseUrl = ITProperties.getInstance().getProperty("base.url");

    private UrlBuilder() {}

    public static String buildUrl(String resource, String resourceId) {
        StringBuilder url = new StringBuilder(baseUrl);
        return url.append(resource).append('/').append(resourceId).toString();
    }

    public static String buildUserUrl(String resource, String userBase, String mode) {
        return buildUrl(resource, userBase).concat("?mode=").concat(mode);
    }
}
