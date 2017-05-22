package com.iurii.microservice.util;

import com.iurii.microservice.util.properties.ITProperties;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public final class HttpClientProvider {

    private static String username = ITProperties.getInstance().getProperty("username");
    private static String password = ITProperties.getInstance().getProperty("password");
    private static CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    private static UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);

    private HttpClientProvider() {}

    public static HttpClient buildHttpClientWithCredentials() {
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
    }

}
