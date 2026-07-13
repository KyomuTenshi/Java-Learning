package scr.oop;

public class SmsSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Отправка SMS: " + message);
    }
}