package com.smurov.web.repository;


import com.smurov.web.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findUserById(Long id);
}
