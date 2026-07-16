package com.example.taskapi.calc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final HistoryService historyService;

    // Внедрение зависимости через конструктор. 
    // Начиная со Spring 4.3, аннотацию @Autowired над конструктором ставить не обязательно, если он один.
    public CalculatorController(CalculatorService calculatorService, HistoryService historyService) {
        this.calculatorService = calculatorService;
        this.historyService = historyService;
    }

    @GetMapping("/calc/add")
    public String add(@RequestParam("a") int a, @RequestParam("b") int b) {
        int result = calculatorService.add(a,b);
        historyService.addRecord(a + " + " + b + " = " + result);
        return String.valueOf(result);
    }

    @GetMapping("/calc/sub")
    public String subtract(@RequestParam("a") int a, @RequestParam("b") int b) {
        int result = calculatorService.subtract(a,b);
        historyService.addRecord(a + " - " + b + " = " + result);
        return String.valueOf(result);
    }

    @GetMapping("/calc/mult")
    public String multiply(@RequestParam("a") int a, @RequestParam("b") int b) {
        int result = calculatorService.multiply(a,b);
        historyService.addRecord(a + " * " + b + " = " + result);
        return String.valueOf(result);
    }

    @GetMapping("/calc/div")
    public String divide(@RequestParam("a") int a, @RequestParam("b") int b) {
        // Простая проверка на деление на ноль
        if (b == 0) {
            return "Ошибка: деление на ноль";
        }
        int result = calculatorService.divide(a,b);
        historyService.addRecord(a + " / " + b + " = " + result);
        return String.valueOf(result);
    }

    @GetMapping("/calc/history")
    public List<String> getHistory() {
        // Spring Boot автоматически сериализует List<String> в JSON-массив строк
        return historyService.getHistory();
    }
}
