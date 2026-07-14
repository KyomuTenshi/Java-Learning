package scr.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TaskRegistry registry = new TaskRegistry();
        int numberOfThreads = 10;
        int tasksPerThread = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        System.out.println("Запуск потоков для добавления задач...");

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                for (int j = 0; j <tasksPerThread; j++) {
                    long id = (long) threadId * tasksPerThread + j;
                    registry.addTask(id, "Task-" + id);
                }
            });
        }

        // Завершаем работу пула потоков и ждем выполнения всех задач
        executorService.shutdown();
        if (executorService.awaitTermination(60, TimeUnit.SECONDS)) {
            System.out.println("Все задачи успешно добавлены. Всего задач: " + registry.getAllTasks().size());
        } else {
            System.out.println("Потоки не успели завершить работу за отведенное время.");
        }

        // Проверяем, все ли задачи добавились корректно
        int expectedTotalTasks = numberOfThreads * tasksPerThread;
        int actualTotalTasks = registry.getAllTasks().size();

        System.out.println("Ожидалось задач: " + expectedTotalTasks);
        System.out.println("Фактически добавлено: " + actualTotalTasks);

        if (expectedTotalTasks == actualTotalTasks) {
            System.out.println("Успех: Все задачи добавлены без потерь и Race Conditions!");
        } else {
            System.out.println("Ошибка: Данные потеряны!");
        }

        // Тест исключения
        try {
            registry.getTask(999999L);
        } catch (TaskNotFoundException e) {
            System.out.println("Перехвачено ожидаемое исключение: " + e.getMessage());
        }
    }
}
