package com.example.task_manegement_web_application.interfaces;

import com.example.task_manegement_web_application.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll(Sort sort);

    @Query("SELECT t FROM Task t WHERE t.userEntity.email = :username")
    List<Task> findByUsername(@Param("username") String username);




//    Long findById(Long id);

}
