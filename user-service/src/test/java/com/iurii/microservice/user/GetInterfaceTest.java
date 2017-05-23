package com.iurii.microservice.user;

import com.iurii.microservice.AbstractModuleIntegrationTest;
import com.iurii.microservice.util.HttpClientProvider;
import com.iurii.microservice.util.JSONUtil;
import com.iurii.microservice.util.UrlBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GetInterfaceTest extends AbstractModuleIntegrationTest {

    private static final HttpClient HTTP_CLIENT = HttpClientProvider.buildHttpClientWithCredentials();

    @Test
    public void testGetUserData() throws IOException, JSONException {
        String url = UrlBuilder.buildUrl(USER_RESOURCE, ID);
        HttpGet request = new HttpGet(url);

        HttpResponse response = HTTP_CLIENT.execute(request);
        String responseEntity = EntityUtils.toString(response.getEntity());

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(JSONUtil.getResponseMoney(responseEntity)).isEqualTo(MONEY);
        assertThat(JSONUtil.getResponseLink(responseEntity)).contains(ID);
    }

    @Test
    public void testWhenUserNotFound() throws IOException {
        String url = UrlBuilder.buildUrl(USER_RESOURCE, DUMMY_ID);
        HttpGet request = new HttpGet(url);

        HttpResponse response = HTTP_CLIENT.execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(404);
    }

}
