package com.example.taskapi.repository;

import com.example.taskapi.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByCompleted(boolean completed);
}