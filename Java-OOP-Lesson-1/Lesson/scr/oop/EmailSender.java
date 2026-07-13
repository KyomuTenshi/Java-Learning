package scr.oop;

public class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Отправка Email: " + message);
    }
}