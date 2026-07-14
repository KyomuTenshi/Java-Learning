package scr.basics;

public class ExceptionsDemo {
    public static void main(String[] args) {
        try {
            processTask(null);
        } catch (TaskNotFoundException e) {
            System.out.println("Обработана ошибка: " + e.getMessage());
        }
    }

    static void processTask(String taskId) throws TaskNotFoundException {
        if (taskId == null) {
            throw new TaskNotFoundException(0L);
        }
        System.out.println("Обработка задачи: " + taskId);
    }
}
