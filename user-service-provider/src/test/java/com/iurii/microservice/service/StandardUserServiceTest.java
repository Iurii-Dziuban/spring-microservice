package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.UserRepository;
import com.iurii.microservice.persistance.entity.User;
import com.iurii.microservice.service.converters.UserResourceConverter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StandardUserServiceTest {

    private static final String ID = "4";
    public static final LocalDate BIRTH_DATE = LocalDate.of(1990, 4, 16);
    public static final String NAME = "iurii";

    private UserRepository repository;
    private StandardUserService service;
    private User user;
    private UserResourceConverter converter;
    private UserResource userResource;

    @Before
    public void setUp() throws Exception {
        converter = mock(UserResourceConverter.class);
        repository = mock(UserRepository.class);
        service = new StandardUserService(repository, converter);
        user = User.builder().id(ID)
                .name(NAME).birthDate(BIRTH_DATE).build();

        userResource = UserResource.builder().name(NAME).birthDate(BIRTH_DATE.toString()).build();
    }

    @Test
    public void findRestriction() {
        given(repository.findOne(ID)).willReturn(user);

        given(converter.convert(user)).willReturn(userResource);

        UserResource result = service.getRestriction(ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getBirthDate(), is(notNullValue()));
        assertThat(result.getName(), is(NAME));

        verify(repository).findOne(ID);
        verify(converter).convert(user);
    }

    @Test
    public void notFindRestriction() {
        UserResource result = service.getRestriction(ID);

        assertThat(result, is(nullValue()));
        verify(repository).findOne(ID);
    }

    @Test
    public void createRestriction() {
        given(repository.save((User) any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode, is(ServiceResponseCode.OK));
        verify(repository).save((User) any());
    }

    @Test
    public void createRestrictionBcSigExists() {
        given(repository.exists(ID)).willReturn(true);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode, is(ServiceResponseCode.OK));
        verify(repository).save((User)any());
    }

    @Test
    public void updateRestrictionSuccessfully() {
        given(repository.findOne(ID)).willReturn(user);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode, is(ServiceResponseCode.OK));
        verify(repository).save((User) any());
        verify(repository).findOne(ID);
    }

    @Test
    public void updateRestrictionNotFoundFail() {
        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode, is(ServiceResponseCode.NOT_FOUND));
        verify(repository).findOne(ID);
    }

    @Test
    public void deleteRestrictionNotFound() {
        doThrow(EmptyResultDataAccessException.class).when(repository).delete(ID);

        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode, is(ServiceResponseCode.NOT_FOUND));
        verify(repository).delete(ID);
    }

    @Test
    public void deleteRestrictionSuccessfully() {
        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode, is(ServiceResponseCode.OK));
        verify(repository).delete(ID);
    }

}