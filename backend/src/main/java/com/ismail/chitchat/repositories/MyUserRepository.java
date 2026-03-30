package com.ismail.chitchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ismail.chitchat.entities.MyUser;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByUsername(String username);

    Optional<MyUser> findByPhone(String phone);

    Optional<MyUser> findByEmail(String email);
}
