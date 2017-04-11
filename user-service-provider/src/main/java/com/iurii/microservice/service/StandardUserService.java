package com.iurii.microservice.service;

import com.iurii.microservice.api.resources.user.UserResource;
import com.iurii.microservice.persistance.UserRepository;
import com.iurii.microservice.persistance.entity.User;
import com.iurii.microservice.service.converters.UserResourceConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

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
    public ServiceResponseCode createUser(String id, String name, LocalDate birthDate) {
        boolean existsBcSig = userRepository.exists(id);
        User newUser = User.builder()
                .isNew(!existsBcSig)
                .id(id)
                .name(name)
                .birthDate(birthDate)
                .build();

        userRepository.save(newUser);
        return ServiceResponseCode.OK;
    }

    @Override
    public ServiceResponseCode updateUser(String id, String name, LocalDate birthDate) {

        User oldRestriction = userRepository.findOne(id);
        if (oldRestriction == null) {
            return ServiceResponseCode.NOT_FOUND;
        }

        User updatedUser = User.builder()
                .isNew(false)
                .id(id)
                .name(name)
                .birthDate(birthDate)
                .build();

        userRepository.save(updatedUser);
        return ServiceResponseCode.OK;
    }

    @Override
    public ServiceResponseCode deleteUser(String bcSig) {
        try {
            userRepository.delete(bcSig);
        } catch (EmptyResultDataAccessException e) {
            return ServiceResponseCode.NOT_FOUND;
        }
        return ServiceResponseCode.OK;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResource getRestriction(String id) {
        User user = userRepository.findOne(id);
        return converter.convert(user);
    }
}