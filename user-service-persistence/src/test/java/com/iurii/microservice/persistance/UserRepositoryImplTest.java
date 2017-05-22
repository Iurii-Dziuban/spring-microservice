package com.iurii.microservice.persistance;

import com.iurii.microservice.persistance.entity.User;
import org.junit.Test;
import org.mockito.BDDMockito;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import static org.mockito.BDDMockito.then;

public class UserRepositoryImplTest {

    private static final String ID = "id";

    @Test
    public void testLock() {
        EntityManager entityManager = BDDMockito.mock(EntityManager.class);
        UserRepositoryImpl lockRepository = new UserRepositoryImpl(entityManager);

        lockRepository.findOneAndLock(ID);

        then(entityManager).should().find(User.class, ID, LockModeType.PESSIMISTIC_WRITE);
    }
}
