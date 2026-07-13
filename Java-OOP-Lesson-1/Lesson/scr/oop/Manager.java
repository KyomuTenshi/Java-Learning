package scr.oop;

// Наследование с помощью ключевого слова extends
public class Manager extends Employee {
    
    // Переопределение метода родительского класса
    @Override
    public double calculateSalary() {
        // Менеджер получает базовую зарплату + фиксированный бонус (например, 20%)
        return baseSalary + (baseSalary * 0.20);
    }
}