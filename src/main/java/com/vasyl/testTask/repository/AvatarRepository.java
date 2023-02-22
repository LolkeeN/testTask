package com.vasyl.testTask.repository;

import com.vasyl.testTask.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
}
