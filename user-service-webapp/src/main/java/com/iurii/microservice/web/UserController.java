package com.iurii.microservice.web;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import com.iurii.microservice.model.Mode;
import com.iurii.microservice.service.UserService;
import com.iurii.microservice.service.binders.ModeBinder;
import com.iurii.microservice.service.response.ServiceResponseCode;
import com.iurii.microservice.web.common.CreateOrUpdateUserBuilder;
import com.iurii.microservice.web.link.UserLinkCreator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("UserController")
@Validated
@RequestMapping(value = "/userService/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "users", description = "the users API")
public class UserController {

    private final UserService userService;

    private final CreateOrUpdateUserBuilder builder;

    private final UserLinkCreator linkCreator;

    @Autowired
    public UserController(UserService userService, CreateOrUpdateUserBuilder builder, UserLinkCreator linkCreator) {
        this.userService = userService;
        this.builder = builder;
        this.linkCreator = linkCreator;
    }

    @ApiOperation(value = "modify the available amount for the BC",
            notes = "we have to separate the two modes \"update\" and \"set\" and \"updateAmount\". " +
                    "In case of \"update\" an user record must exist already for the id. " +
                    "This will be adjusted then. In case of \"set\" an existing record will be overwritten, " +
                    "otherwise a new one will be created."
                    + "In case of \"updateAmount\" an user record must exist already for the id and amount is added ",
            response = Void.class,
            authorizations = {@Authorization(value = "basicAuth")}, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "bc amount was adjusted", response = Void.class),
            @ApiResponse(code = 400, message = "something wrong with the input parameters", response = Void.class),
            @ApiResponse(code = 404, message = "no bc credit restriction found for this BC in case of mode \"add\"",
                    response = Void.class),
            @ApiResponse(code = 500, message = "System Error", response = Void.class) })
    @PostMapping(value = "/{userId}")
    public ResponseEntity<Void> createOrUpdate(
            @PathVariable("userId") final String userId, @RequestParam("mode")
    final Mode mode, @Valid @RequestBody UserResource userResource) {

        CreateOrUpdateUserRequest userRequest = builder.getCreateOrUpdateUserRequest(userId, userResource);

        ServiceResponseCode serviceResponseCode = null;

        switch (mode) {
            case SET: serviceResponseCode = userService.createUser(userRequest.getUserId(), userRequest.getUserName(),
                      userRequest.getBirthDate(), userRequest.getUpdatedTime(), userRequest.getMoney());
                      break;
            case UPDATE: serviceResponseCode = userService.updateUser(userRequest.getUserId(), userRequest.getUserName(),
                         userRequest.getBirthDate(), userRequest.getUpdatedTime(), userRequest.getMoney());
                         break;
            case UPDATE_AMOUNT:
            default: serviceResponseCode = userService.updateAddAmount(userRequest.getUserId(), userRequest.getMoney());
        }

        return new ResponseEntity<>(HttpStatus.valueOf(serviceResponseCode.getCode()));
    }

    @ApiOperation(value = "delete user record for the id",
            notes = "this is needed to delete user",
            response = Void.class, authorizations = {@Authorization(value = "basicAuth")}, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user is deleted", response = Void.class),
            @ApiResponse(code = 400, message = "something wrong with the input parameters", response = Void.class),
            @ApiResponse(code = 404, message = "no user found for this id", response = Void.class),
            @ApiResponse(code = 500, message = "System Error", response = Void.class) })
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") final String userId) {
        ServiceResponseCode serviceResponseCode = userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.valueOf(serviceResponseCode.getCode()));
    }

    @ApiOperation(value = "gets the available user for id", notes = "",
            response = UserResource.class, authorizations = {@Authorization(value = "basicAuth")}, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If there is an user defined for the id then return the data",
                    response = UserResource.class),
            @ApiResponse(code = 400, message = "something wrong with the input parameters", response = UserResource.class),
            @ApiResponse(code = 404, message = "no credit restriction found for this BC", response = UserResource.class),
            @ApiResponse(code = 500, message = "System Error", response = UserResource.class) })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> get(@PathVariable("userId") final String userId) {
        UserResource userResource = userService.getUser(userId);
        if (userResource == null) {
            return ResponseEntity.notFound().build();
        }
        userResource.add(linkCreator.getLink(userId));
        return ResponseEntity.ok(userResource);
    }

    @InitBinder
    void initBinder(final WebDataBinder binder){
        binder.registerCustomEditor(Mode.class, new ModeBinder());
    }
}
