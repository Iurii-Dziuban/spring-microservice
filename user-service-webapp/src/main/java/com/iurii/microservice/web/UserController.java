package com.iurii.microservice.web;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import com.iurii.microservice.service.response.ServiceResponseCode;
import com.iurii.microservice.service.UserService;
import com.iurii.microservice.web.common.CreateOrUpdateUserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("UserController")
@Validated
@RequestMapping(value = "/userService/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    private final CreateOrUpdateUserBuilder builder;

    @Autowired
    public UserController(UserService userService, CreateOrUpdateUserBuilder builder) {
        this.userService = userService;
        this.builder = builder;
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<Void> createOrUpdate(
            @PathVariable("userId") final String userId, @RequestParam("mode")
    @Pattern(regexp = "^update|set|updateAmount$") final String mode, @Valid @RequestBody UserResource userResource) {

        CreateOrUpdateUserRequest userRequest = builder.getCreateOrUpdateUserRequest(userId, userResource);

        ServiceResponseCode serviceResponseCode = null;

        if ("set".equals(mode)) {
            serviceResponseCode = userService.createUser(userRequest.getUserId(), userRequest.getUserName(),
                    userRequest.getBirthDate(), userRequest.getUpdatedTime(), userRequest.getMoney());
        }

        if ("update".equals(mode)) {
            serviceResponseCode = userService.updateUser(userRequest.getUserId(), userRequest.getUserName(),
                    userRequest.getBirthDate(), userRequest.getUpdatedTime(), userRequest.getMoney());
        }

        if ("updateAmount".equals(mode)) {
            serviceResponseCode = userService.updateAddAmount(userRequest.getUserId(), userRequest.getMoney());
        }

        return new ResponseEntity<>(HttpStatus.valueOf(serviceResponseCode.getCode()));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") final String userId) {
        ServiceResponseCode serviceResponseCode = userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.valueOf(serviceResponseCode.getCode()));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> get(@PathVariable("userId") final String userId) {
        UserResource userResource = userService.getUser(userId);
        if (userResource == null) {
            return ResponseEntity.notFound().build();
        }
        userResource.add(linkTo(methodOn(UserController.class).get(userId)).withSelfRel());
        return ResponseEntity.ok(userResource);
    }
}
