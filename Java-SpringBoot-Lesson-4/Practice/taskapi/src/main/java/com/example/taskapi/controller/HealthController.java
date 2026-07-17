package com.example.taskapi.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    // Spring сам создаст JdbcTemplate как бин (он идёт вместе со starter-data-jpa)
    // и внедрит его сюда через конструктор — это Dependency Injection,
    // с которым ты уже знакомился в занятии 4.
    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health/db")
    public String checkDb() {
        String version = jdbcTemplate.queryForObject("SELECT version()", String.class);
        return "DB connection OK: " + version;
    }

    // 1. Эндпоинт для получения имени текущей БД
    @GetMapping("/health/db/name")
    public String getDbName() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }

    // 2. Эндпоинт для проверки чтения из таблицы ping
    @GetMapping("/health/ping")
    public String pingDb() {
        return jdbcTemplate.queryForObject("SELECT message FROM ping LIMIT 1", String.class);
    }
}