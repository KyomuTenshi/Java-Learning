package com.example.taskapi.calc;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        // Логику проверки на ноль мы вынесем в контроллер, 
        // чтобы вернуть пользователю понятный текст вместо падения.
        return a / b;
    }
}
