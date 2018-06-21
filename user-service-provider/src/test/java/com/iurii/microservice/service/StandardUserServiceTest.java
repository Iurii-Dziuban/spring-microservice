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
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;

public class StandardUserServiceTest {

    private static final String ID = "4";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 4, 16);
    private static final String NAME = "iurii";
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now();
    private static final long MONEY = 100L;

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
    public void findUser() {
        given(repository.findOneAndLock(ID)).willReturn(user);

        given(converter.convert(user)).willReturn(userResource);

        UserResource result = service.getUser(ID);

        assertThat(result).isNotNull();
        assertThat(result.getBirthDate()).isNotNull();
        assertThat(result.getName()).isEqualTo(NAME);

        then(repository).should().findOneAndLock(ID);
        then(converter).should().convert(user);
    }

    @Test
    public void notFindUser() {
        UserResource result = service.getUser(ID);

        assertThat(result).isNull();
        then(repository).should().findOneAndLock(ID);
    }

    @Test
    public void createUser() {
        given(repository.save((User) any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE, UPDATED_TIME, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User) any());
    }

    @Test
    public void createUserExists() {
        given(repository.existsById(ID)).willReturn(true);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.createUser(ID, NAME, BIRTH_DATE, UPDATED_TIME, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User)any());
    }

    @Test
    public void updateUserSuccessfully() {
        given(repository.findOneAndLock(ID)).willReturn(user);
        given(repository.save((User)any())).willReturn(null);

        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE, UPDATED_TIME, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save((User) any());
        then(repository).should().findOneAndLock(ID);
    }

    @Test
    public void updateUserNotFoundFail() {
        ServiceResponseCode responseCode = service.updateUser(ID, NAME, BIRTH_DATE, UPDATED_TIME, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.NOT_FOUND);
        then(repository).should().findOneAndLock(ID);
    }

    @Test
    public void deleteUserNotFound() {
        willThrow(EmptyResultDataAccessException.class).given(repository).deleteById(ID);

        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.NOT_FOUND);
        then(repository).should().deleteById(ID);
    }

    @Test
    public void deleteUserSuccessfully() {
        ServiceResponseCode responseCode = service.deleteUser(ID);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().deleteById(ID);
    }


    @Test
    public void updateMoneySuccessfully() {
        given(repository.findOneAndLock(ID)).willReturn(user);
        given(repository.save(any())).willReturn(null);

        ServiceResponseCode responseCode = service.updateAddAmount(ID, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.OK);
        then(repository).should().save(any());
        then(repository).should().findOneAndLock(ID);
    }

    @Test
    public void updateMoneyUserNotFoundFail() {
        ServiceResponseCode responseCode = service.updateAddAmount(ID, MONEY);

        assertThat(responseCode).isEqualTo(ServiceResponseCode.NOT_FOUND);
        then(repository).should().findOneAndLock(ID);
    }

}