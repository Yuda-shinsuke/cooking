package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUserNameAndUserPass(
        String userName,
        String userPass
    );
    Optional<User> findByUserName(String userName);
    User getUserByUserName(String userName);
}

