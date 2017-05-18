package com.iurii.microservice.web.interfaces;

import com.iurii.microservice.api.resources.user.UserResource;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Void> createOrUpdate(final String userId, final String mode, UserResource userResource);

    ResponseEntity<Void> delete(final String userId);

    ResponseEntity<UserResource> get(final String userId);
}
