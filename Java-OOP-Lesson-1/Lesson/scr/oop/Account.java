package scr.oop;

public class Account {
    // Скрытое поле данных
    private double balance;

    // Метод для пополнения баланса
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Геттер для безопасного получения значения
    public double getBalance() {
        return balance;
    }
}