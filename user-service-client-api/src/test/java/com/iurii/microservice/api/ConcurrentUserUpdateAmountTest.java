package com.iurii.microservice.api;

import com.iurii.microservice.api.resources.user.UserResource;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 *
 * Concurrency test with N treads trying to update amount with THREAD_AMOUNT
 *
 * If initial value is 0 -> N * THREAD_AMOUNT in the end!
 *
 * Created by iurii.dziuban on 10/05/2017.
 *
 * Can be used to check concurrency issues. Uses client API - existing service. That is why ignored by default.
 */
@Ignore
public class ConcurrentUserUpdateAmountTest {

    private static final int NUMBER_OF_THREADS = 10;
    private static final int ONE_THREAD_AMOUNT = 10;
    private static final int AMOUNT = NUMBER_OF_THREADS * ONE_THREAD_AMOUNT;
    private static final String ID = "id";
    public static final String IURII = "Iurii";
    public static final String BIRTH_DATE = "1990-04-16";
    public static final String MONEY = "0";
    public static final String UPDATED_TIME = "2015-12-24T18:21:05Z";

    private DefaultRestUserService userService =
            new DefaultRestUserService("http://localhost:9000", "user", "user");

    private AtomicInteger index = new AtomicInteger(0);

    @Test
    public void concurrentTest() throws InterruptedException {

        final CountDownLatch startLatch = new CountDownLatch(NUMBER_OF_THREADS);
        final CountDownLatch endLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService exec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        UserResource userResource = UserResource.builder().name(IURII)
                .birthDate(BIRTH_DATE).money(MONEY).updatedTime(UPDATED_TIME).build();
        userService.createOrUpdate(ID, "set", userResource);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            exec.execute(() -> {
                    startLatch.countDown();
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        fail(e.getMessage());
                    }
                    concurrentMethod();
                    endLatch.countDown();
                    try {
                        endLatch.await();
                    } catch (InterruptedException e) {
                        fail(e.getMessage());
                    }
            });
        }

        exec.shutdown();
        exec.awaitTermination(50, TimeUnit.SECONDS);

        ResponseEntity<?> entity = userService.get(ID);

        Object body = entity.getBody();
        if (entity.getBody() instanceof UserResource) {
            UserResource userResourceInBody = (UserResource) body;
            assertThat(userResourceInBody.getMoney()).isEqualTo(String.valueOf(AMOUNT));
        } else {
            fail(entity.getBody().toString());
        }
    }

    private void concurrentMethod() {
        UserResource userResource = UserResource.builder()
                .name(IURII).money(String.valueOf(ONE_THREAD_AMOUNT))
                .updatedTime(UPDATED_TIME).birthDate(BIRTH_DATE).build();


        userService.createOrUpdate(ID, "updateAmount", userResource);
    }
}
