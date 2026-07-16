package com.example.task_api.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // говорит Spring: "это бин, который обрабатывает HTTP-запросы,
                // а возвращаемые значения методов сериализуются напрямую в тело ответа (обычно в JSON)"
public class GreetingController {
    private final GreetingService greetingService;

    // Конструкторное внедрение зависимости — Spring сам найдёт бин GreetingService
    // (созданный благодаря @Service выше) и передаст его сюда
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello") // обрабатывает GET-запросы на /hello
    public String hello(@RequestParam(defaultValue = "мир") String name) {
        return greetingService.greet(name);
    }
}
