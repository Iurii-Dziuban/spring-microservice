package com.iurii.microservice.util.properties;

import java.io.IOException;
import java.util.Properties;

public final class ITProperties {

    private final static String IT_PROPERTIES = "it.properties";
    private static final ITProperties INSTANCE = new ITProperties();

    private Properties properties;

    private ITProperties() {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(IT_PROPERTIES));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ITProperties getInstance() {
        return INSTANCE;
    }

    public String getProperty(final String key) {
        return String.valueOf(properties.get(key));
    }
}
