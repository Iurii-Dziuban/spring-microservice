package com.iurii.microservice.web;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.model.CreateOrUpdateUserRequest;
import com.iurii.microservice.model.Mode;
import com.iurii.microservice.service.UserService;
import com.iurii.microservice.service.response.ServiceResponseCode;
import com.iurii.microservice.web.common.CreateOrUpdateUserBuilder;
import com.iurii.microservice.web.link.UserLinkCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.BDDMockito.when;

/**
 * Created by iurii.dziuban on 22/05/2017.
 */
public class UserControllerTest {

    private static final String IURII = "iurii";
    private static final String BIRTH_DATE = "1990-04-16";
    private static final String UPDATED_TIME = "2015-12-24T18:21:05Z";
    private static final String MONEY = "1234";
    public static final String ID = "id";

    private UserController controller;

    private UserService userService = BDDMockito.mock(UserService.class);
    private CreateOrUpdateUserBuilder builder = BDDMockito.mock(CreateOrUpdateUserBuilder.class);
    private UserLinkCreator linkCreator = BDDMockito.mock(UserLinkCreator.class);

    @Before
    public void setUp() {
        userService = BDDMockito.mock(UserService.class);
        builder = BDDMockito.mock(CreateOrUpdateUserBuilder.class);
        linkCreator = BDDMockito.mock(UserLinkCreator.class);
        controller = new UserController(userService, builder, linkCreator);
    }

    @Test
    public void testInitBinder() {
        WebDataBinder binder = BDDMockito.mock(WebDataBinder.class);

        controller.initBinder(binder);

        then(binder).should().registerCustomEditor(any(), any());
    }

    @Test
    public void testCreateOrUpdateSetMode() {
        UserResource userResource = UserResource.builder().birthDate(BIRTH_DATE)
                .money(MONEY).updatedTime(UPDATED_TIME).name(IURII).build();
        CreateOrUpdateUserRequest createOrUpdateUserRequest = BDDMockito.mock(CreateOrUpdateUserRequest.class);
        when(builder.getCreateOrUpdateUserRequest(ID, userResource)).thenReturn(createOrUpdateUserRequest);
        LocalDate birthDate = LocalDate.now();
        when(createOrUpdateUserRequest.getBirthDate()).thenReturn(birthDate);
        long money = 1234L;
        when(createOrUpdateUserRequest.getMoney()).thenReturn(money);
        ZonedDateTime updatedTime = ZonedDateTime.now();
        when(createOrUpdateUserRequest.getUpdatedTime()).thenReturn(updatedTime);
        when(createOrUpdateUserRequest.getUserName()).thenReturn(IURII);
        when(createOrUpdateUserRequest.getUserId()).thenReturn(ID);
        when(userService.createUser(ID, IURII, birthDate, updatedTime, money)).thenReturn(ServiceResponseCode.OK);

        ResponseEntity<Void> responseEntity = controller.createOrUpdate(ID, Mode.SET, userResource);

        then(userService).should().createUser(ID, IURII, birthDate, updatedTime, money);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateOrUpdateUpdateMode() {
        UserResource userResource = UserResource.builder().birthDate(BIRTH_DATE)
                .money(MONEY).updatedTime(UPDATED_TIME).name(IURII).build();
        CreateOrUpdateUserRequest createOrUpdateUserRequest = BDDMockito.mock(CreateOrUpdateUserRequest.class);
        when(builder.getCreateOrUpdateUserRequest(ID, userResource)).thenReturn(createOrUpdateUserRequest);
        LocalDate birthDate = LocalDate.now();
        when(createOrUpdateUserRequest.getBirthDate()).thenReturn(birthDate);
        long money = 1234L;
        when(createOrUpdateUserRequest.getMoney()).thenReturn(money);
        ZonedDateTime updatedTime = ZonedDateTime.now();
        when(createOrUpdateUserRequest.getUpdatedTime()).thenReturn(updatedTime);
        when(createOrUpdateUserRequest.getUserName()).thenReturn(IURII);
        when(createOrUpdateUserRequest.getUserId()).thenReturn(ID);
        when(userService.updateUser(ID, IURII, birthDate, updatedTime, money)).thenReturn(ServiceResponseCode.OK);

        ResponseEntity<Void> responseEntity = controller.createOrUpdate(ID, Mode.UPDATE, userResource);

        then(userService).should().updateUser(ID, IURII, birthDate, updatedTime, money);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateOrUpdateUpdateAmountMode() {
        UserResource userResource = UserResource.builder().birthDate(BIRTH_DATE)
                .money(MONEY).updatedTime(UPDATED_TIME).name(IURII).build();
        CreateOrUpdateUserRequest createOrUpdateUserRequest = BDDMockito.mock(CreateOrUpdateUserRequest.class);
        when(builder.getCreateOrUpdateUserRequest(ID, userResource)).thenReturn(createOrUpdateUserRequest);
        LocalDate birthDate = LocalDate.now();
        when(createOrUpdateUserRequest.getBirthDate()).thenReturn(birthDate);
        long money = 1234L;
        when(createOrUpdateUserRequest.getMoney()).thenReturn(money);
        ZonedDateTime updatedTime = ZonedDateTime.now();
        when(createOrUpdateUserRequest.getUpdatedTime()).thenReturn(updatedTime);
        when(createOrUpdateUserRequest.getUserName()).thenReturn(IURII);
        when(createOrUpdateUserRequest.getUserId()).thenReturn(ID);
        when(userService.updateAddAmount(ID, money)).thenReturn(ServiceResponseCode.OK);

        ResponseEntity<Void> responseEntity = controller.createOrUpdate(ID, Mode.UPDATE_AMOUNT, userResource);

        then(userService).should().updateAddAmount(ID, money);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateOrUpdateUpdateAmountModeDefault() {
        ResponseEntity<Void> responseEntity = controller.createOrUpdate(ID, null, null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDelete() {
        when(userService.deleteUser(ID)).thenReturn(ServiceResponseCode.OK);

        ResponseEntity<Void> responseEntity = controller.delete(ID);

        then(userService).should().deleteUser(ID);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGet() {
        UserResource userResource = UserResource.builder().birthDate(BIRTH_DATE)
                .money(MONEY).updatedTime(UPDATED_TIME).name(IURII).build();
        when(userService.getUser(ID)).thenReturn(userResource);
        when(linkCreator.getLink(ID)).thenReturn(BDDMockito.mock(Link.class));

        ResponseEntity<UserResource> responseEntity = controller.get(ID);

        then(userService).should().getUser(ID);
        then(linkCreator).should().getLink(ID);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetIsNull() {
        when(userService.getUser(ID)).thenReturn(null);

        ResponseEntity<UserResource> responseEntity = controller.get(ID);

        then(userService).should().getUser(ID);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
