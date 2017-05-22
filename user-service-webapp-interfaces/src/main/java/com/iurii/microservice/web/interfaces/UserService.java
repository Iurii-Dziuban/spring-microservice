package com.iurii.microservice.web.interfaces;

import com.iurii.microservice.api.resources.user.UserResource;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createOrUpdate(final String userId, final String mode, UserResource userResource);

    ResponseEntity<?> delete(final String userId);

    ResponseEntity<?> get(final String userId);
}
