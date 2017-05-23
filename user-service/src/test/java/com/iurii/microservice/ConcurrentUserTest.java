package com.iurii.microservice;

import com.iurii.microservice.model.Mode;
import com.iurii.microservice.user.SetInterfaceTest;
import com.iurii.microservice.util.JSONUtil;
import com.iurii.microservice.util.RequestBody;
import com.iurii.microservice.util.RequestProvider;
import com.iurii.microservice.util.UrlBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Concurrency test with N treads trying to update credit amount with THREAD_AMOUNT
 *
 * If initial value is zero in the end -> amount should be N * THREAD_AMOUNT!
 *
 * Created by iurii.dziuban on 10/05/2017.
 */
public class ConcurrentUserTest extends AbstractModuleIntegrationTest {

    private static final int INITIAL_MONEY_AMOUNT = 0;
    private static final int NUMBER_OF_THREADS = 10;
    private static final int ONE_THREAD_AMOUNT = 10;
    private static final int AMOUNT = NUMBER_OF_THREADS * ONE_THREAD_AMOUNT;


    @Test
    public void concurrentTest() throws Exception {

        final CountDownLatch startLatch = new CountDownLatch(NUMBER_OF_THREADS);
        final CountDownLatch endLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        createOrUpdate(ID, SetInterfaceTest.SET_MODE);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            exec.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                    concurrentMethod();
                    endLatch.countDown();
                    endLatch.await();
                } catch (Exception e) {
                    Assertions.fail(e.getMessage());
                }
            });
        }

        exec.shutdown();
        exec.awaitTermination(50, TimeUnit.SECONDS);

        assertThat(getMoney()).isEqualTo(AMOUNT);
    }

    private void concurrentMethod() throws Exception{
        String requestBody = new RequestBody().setMoney(String.valueOf(ONE_THREAD_AMOUNT))
                .setName(IURII).birthDate(BIRTH_DATE.toString()).setUpdatedTime(UPDATED_TIME.toString()).toString();
        String requestUrl = UrlBuilder.buildUserUrl(USER_RESOURCE, ID, Mode.UPDATE_AMOUNT.toString());
        HttpPost request = RequestProvider.getPostRequest(requestUrl, requestBody);

        SetInterfaceTest.HTTP_CLIENT.execute(request);
    }

    private void createOrUpdate(String id, String mode) throws Exception {
        String requestUrl = UrlBuilder.buildUserUrl(USER_RESOURCE, id, mode);
        String requestBody = new RequestBody().setMoney(String.valueOf(INITIAL_MONEY_AMOUNT))
                .setName(IURII).birthDate(BIRTH_DATE.toString()).setUpdatedTime(UPDATED_TIME.toString()).toString();
        HttpPost request = RequestProvider.getPostRequest(requestUrl, requestBody);

        SetInterfaceTest.HTTP_CLIENT.execute(request);
    }

    private long getMoney() throws Exception {
        String url = UrlBuilder.buildUrl(USER_RESOURCE, ID);
        HttpGet request = new HttpGet(url);

        HttpResponse response = SetInterfaceTest.HTTP_CLIENT.execute(request);
        return JSONUtil.getResponseMoney(EntityUtils.toString(response.getEntity()));
    }
}
