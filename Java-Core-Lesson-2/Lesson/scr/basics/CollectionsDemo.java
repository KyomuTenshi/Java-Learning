package scr.basics;

import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        // List
        List<String> tasks = new ArrayList<>();
        tasks.add("Написать отчет");
        tasks.add("Провести встречу");
        System.out.println("Список задач: " + tasks);

        // Set
        Set<String> categories = new HashSet<>();
        categories.add("Работа");
        categories.add("Личное");
        categories.add("Работа"); // дубликат проигнорируется
        System.out.println("Уникальные категории: " + categories);

        // Map
        Map<Long, String> statusById = new HashMap<>();
        statusById.put(1L, "NEW");
        statusById.put(2L, "IN_PROGRESS");
        for (Map.Entry<Long, String> entry : statusById.entrySet()) {
            System.out.println("Задача: " + entry.getKey() + " ->" + entry.getValue());
        }
    }
    
}
