package scr.oop;

public class NotificationService {
    private final NotificationSender sender;

    // Конструктор принимает интерфейс, а не конкретный класс
    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String message) {
        // Делегирование выполнения конкретной реализации
        sender.send(message);
    }
}