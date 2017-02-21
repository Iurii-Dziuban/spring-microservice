package com.iurii.microservice.persistance;

import com.iurii.microservice.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
