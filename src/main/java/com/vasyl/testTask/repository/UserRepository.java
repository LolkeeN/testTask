package com.vasyl.testTask.repository;

import com.vasyl.testTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u from User u WHERE u.id=:#{#userId}")
    User findById(@Param("userId") int id);
}
