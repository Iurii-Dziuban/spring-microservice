package com.iurii.microservice.user;

import com.iurii.microservice.AbstractModuleIntegrationTest;
import com.iurii.microservice.util.HttpClientProvider;
import com.iurii.microservice.util.RequestBody;
import com.iurii.microservice.util.RequestProvider;
import com.iurii.microservice.util.UrlBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class SetInterfaceTest extends AbstractModuleIntegrationTest {

    public static final String SET_MODE = "set";
    public static final String USER_URL = UrlBuilder.buildUserUrl(USER_RESOURCE, ID, SET_MODE);
    public static final HttpClient HTTP_CLIENT = HttpClientProvider.buildHttpClientWithCredentials();

    @Test
    public void testWhenUserReturnsNotFound() throws JSONException, IOException {
        String requestBody = new RequestBody().setMoney(String.valueOf(MONEY))
                .setName(DUMMY_ID).birthDate(BIRTH_DATE).setUpdatedTime(UPDATED_TIME).toString();
        HttpPost request = RequestProvider.getPostRequest(USER_URL, requestBody);

        HttpResponse response = HTTP_CLIENT.execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(404);
    }

    @Test
    public void testWhenUserNotExistNewIsAdded() throws JSONException, IOException {
        String id = "ID" + generateTimeStamp();
        String requestUrl = UrlBuilder.buildUserUrl(USER_RESOURCE, id, SET_MODE);
        String requestBody = new RequestBody().setMoney(String.valueOf(MONEY))
                .setName(IURII).birthDate(BIRTH_DATE).setUpdatedTime(UPDATED_TIME).toString();
        HttpPost request = RequestProvider.getPostRequest(requestUrl, requestBody);

        HttpResponse response = HTTP_CLIENT.execute(request);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        assertMoneyEqualsTo(connection, MONEY, id);
        assertNameEqualsTo(connection, IURII, id);
        assertBirthDateEqualsTo(connection, BIRTH_DATE, id);
        assertUpdatedTimeEqualsTo(connection, UPDATED_TIME, id);
    }

    private static String generateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
