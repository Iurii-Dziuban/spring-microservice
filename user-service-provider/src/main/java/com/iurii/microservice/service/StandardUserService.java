package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.UserRepository;
import com.iurii.microservice.persistance.entity.User;
import com.iurii.microservice.service.converters.UserResourceConverter;
import com.iurii.microservice.service.response.ServiceResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation = REQUIRED, isolation = READ_COMMITTED, rollbackFor = Exception.class)
public class StandardUserService implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardUserService.class);

    private final UserRepository userRepository;

    private final UserResourceConverter converter;

    @Autowired
    public StandardUserService(UserRepository userRepository, UserResourceConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public ServiceResponseCode createUser(String id, String name, LocalDate birthDate, ZonedDateTime createdTime, long money) {
        boolean existsUser = userRepository.exists(id);
        User newUser = User.builder()
                .isNew(!existsUser)
                .id(id)
                .name(name)
                .birthDate(birthDate)
                .updatedTime(createdTime)
                .money(money)
                .build();

        userRepository.save(newUser);
        LOGGER.info("User with id {} is created", id);
        return ServiceResponseCode.OK;
    }

    @Override
    public ServiceResponseCode updateUser(String id, String name, LocalDate birthDate, ZonedDateTime updatedTime, long money) {

        User oldUser = userRepository.findOne(id);
        if (oldUser == null) {
            return ServiceResponseCode.NOT_FOUND;
        }

        User updatedUser = User.builder()
                .isNew(false)
                .id(id)
                .name(name)
                .birthDate(birthDate)
                .updatedTime(updatedTime)
                .money(money)
                .build();

        userRepository.save(updatedUser);
        return ServiceResponseCode.OK;
    }

    @Override
    public ServiceResponseCode updateAddAmount(String id, long amount) {

        User oldUser = userRepository.findOneAndLock(id);
        if (oldUser == null) {
            return ServiceResponseCode.NOT_FOUND;
        }

        User updatedUser = User.builder()
                .isNew(false)
                .id(oldUser.getId())
                .name(oldUser.getName())
                .birthDate(oldUser.getBirthDate())
                .money(oldUser.getMoney() + amount)
                .updatedTime(ZonedDateTime.now())
                .build();

        userRepository.save(updatedUser);
        return ServiceResponseCode.OK;
    }

    @Override
    public ServiceResponseCode deleteUser(String id) {
        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return ServiceResponseCode.NOT_FOUND;
        }
        return ServiceResponseCode.OK;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResource getUser(String id) {
        User user = userRepository.findOne(id);
        return converter.convert(user);
    }
}