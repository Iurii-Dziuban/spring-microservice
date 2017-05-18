package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.UserRepository;
import com.iurii.microservice.persistance.entity.User;
import com.iurii.microservice.service.converters.UserResourceConverter;
import com.iurii.microservice.service.response.ServiceResponseCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

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
        converter = BDDMockito.mock(UserResourceConverter.class);
        repository = BDDMockito.mock(UserRepository.class);
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

        assertThat(result).isNotNull();
        assertThat(result.getBirthDate()).isNotNull();
        assertThat(result.getName()).isEqualTo(NAME);

        then(repository).should().findOne(ID);
        then(converter).should().convert(user);
    }

    @Test
    public void notFindRestriction() {
        UserResource result = service.getRestriction(ID);

        assertThat(result).isNull();
        then(repository).should().findOne(ID);
    }

    @Test
    public void createRestriction() {
        given(repository.save((User) any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User) any());
    }

    @Test
    public void createRestrictionBcSigExists() {
        given(repository.exists(ID)).willReturn(true);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User)any());
    }

    @Test
    public void updateRestrictionSuccessfully() {
        given(repository.findOne(ID)).willReturn(user);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User) any());
        then(repository).should().findOne(ID);
    }

    @Test
    public void updateRestrictionNotFoundFail() {
        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.NOT_FOUND);
        then(repository).should().findOne(ID);
    }

    @Test
    public void deleteRestrictionNotFound() {
        willThrow(EmptyResultDataAccessException.class).given(repository).delete(ID);

        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.NOT_FOUND);
        then(repository).should().delete(ID);
    }

    @Test
    public void deleteRestrictionSuccessfully() {
        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().delete(ID);
    }

}