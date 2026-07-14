package scr.basics;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(Long taskId) {
        super("Задача с ID" + taskId + " не найдена.");
    }
}
