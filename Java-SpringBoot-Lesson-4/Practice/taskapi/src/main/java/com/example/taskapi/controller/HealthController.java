package com.example.taskapi.controller;

import com.example.taskapi.entity.LogEntity;
import com.example.taskapi.entity.TaskEntity;
import com.example.taskapi.repository.TaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final TaskRepository taskRepository;

    public HealthController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/health/jpa")
    public String checkJpa() {
        TaskEntity task = new TaskEntity("Проверка JPA");
        task.addLog(new LogEntity("Задача создана через /health/jpa"));

        TaskEntity saved = taskRepository.save(task); // cascade сохранит и лог

        return "Сохранена задача id=" + saved.getId()
                + ", логов: " + saved.getLogs().size();
    }
}