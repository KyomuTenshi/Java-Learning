package scr.basics;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskRegistry {
    // Внутренний класс для хранения данных задачи
    private static class Task {
        private final String title;
        private final LocalDateTime createdAt;

        public Task(String title) {
            this.title = title;
            this.createdAt = LocalDateTime.now(); // Устанавливается автоматически
        }

        public String getTitle() {
            return title;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    // Использование ConcurrentHashMap для потокобезопасности
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();

    // Добавление задачи
    public void addTask(Long id, String title) {
        tasks.put(id, new Task(title));
    }

    // Получение задачи с выбросом исключения, если id не найден
    public String getTask(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException("Задача с ID " + id + " не найдена.");
        }
        return task.getTitle();
    }

    // Получение списка всех названий задач
    public List<String> getAllTasks() {
        List<String> titles = new ArrayList<>();
        for (Task task : tasks.values()) {
            titles.add(task.getTitle());
        }
        return titles;
    }

    // Метод, который выводит, сколько минут прошло с момента создания конкретной задачи
    public long getMinutesSinceCreation(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
             throw new TaskNotFoundException("Задача с ID " + id + " не найдена.");
        }
        return ChronoUnit.MINUTES.between(task.getCreatedAt(), LocalDateTime.now());
    }  
}
