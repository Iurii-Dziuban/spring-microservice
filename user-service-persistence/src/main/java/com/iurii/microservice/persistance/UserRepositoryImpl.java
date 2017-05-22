package com.iurii.microservice.persistance;

import com.iurii.microservice.persistance.entity.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryLockRead<User> {

    private final EntityManager entityManager;

    @Override
    public User findOneAndLock(String id){
        return entityManager.find(User.class, id, LockModeType.PESSIMISTIC_WRITE);
    }
}
