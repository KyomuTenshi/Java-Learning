package com.example.taskapi.calc;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class HistoryService {
    // Используем потокобезопасный список
    private final List<String> history = new CopyOnWriteArrayList<>();

    // Метод для добавления записи в историю
    public void addRecord(String record) {
        history.add(record);
    }

    // Метод для получения всей истории
    public List<String> getHistory() {
        return history;
    }
}
