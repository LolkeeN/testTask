package com.vasyl.testTask.repository;

import com.vasyl.testTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u from User u WHERE u.id = :userId")
    User findById(@Param("userId") int id);

    @Query("SELECT u from User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select case when count(u) > 0 then true else false end from User u where u.id = :userId")
    boolean userExists(@Param("userId") Integer userId);
}
