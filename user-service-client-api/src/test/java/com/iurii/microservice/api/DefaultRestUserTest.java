package com.iurii.microservice.api;

import com.iurii.microservice.api.resources.user.Messages;
import com.iurii.microservice.api.resources.user.UserResource;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by iurii.dziuban on 10/05/2017.
 */
@Ignore
public class DefaultRestUserTest {
    private DefaultRestUserService userService =
            new DefaultRestUserService("http://localhost:9000", "user", "user");

    @Test
    public void test() {
        ResponseEntity<?> entity = userService.get("ID");

        HttpStatus statusCode = entity.getStatusCode();

        Object body = entity.getBody();
        if (entity.getBody() instanceof UserResource) {
            UserResource userResource = (UserResource) body;
        }

        if (entity.getBody() instanceof  Messages) {
            Messages messages = (Messages) body;
        }
    }
}
