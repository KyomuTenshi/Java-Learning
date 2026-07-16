package com.example.task_api.greeting;

import org.springframework.stereotype.Service;

@Service // говорит Spring: "это бин, управляй мной"
public class GreetingService {
    public String greet(String name) {
        return "Привет, " + name + "! Это Spring Boot говорит с тобой.";
    }
}