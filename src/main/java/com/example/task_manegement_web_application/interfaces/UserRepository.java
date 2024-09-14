package com.example.task_manegement_web_application.interfaces;

import com.example.task_manegement_web_application.entity.Task;
import com.example.task_manegement_web_application.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findAll(Sort sort);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :username")
    UserEntity findByUsername(@Param("username") String username);
}
