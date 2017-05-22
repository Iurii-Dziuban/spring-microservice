package com.iurii.microservice.persistance;

public interface UserRepositoryLockRead<T> {
    T findOneAndLock(String id);
}
