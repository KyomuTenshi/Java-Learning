package scr.oop;

public class Main {
    public static void main(String[] args) {
        // Инкапсуляция
        Account account = new Account();
        account.deposit(1000);
        System.out.println("Баланс: " + account.getBalance());

        // Наследование и полиморфизм через переопределение метода
        Employee employee = new Employee();
        employee.name = "Иван";
        employee.baseSalary = 50000;

        Manager manager = new Manager();
        manager.name = "Ольга";
        manager.baseSalary = 70000;
        // bonus зададим через сеттер в реальном проекте, здесь для краткости пропустим

        System.out.println(employee.calculateSalary());
        System.out.println(manager.calculateSalary());

        // Полиморфизм через интерфейс
        NotificationService emailService = new NotificationService(new EmailSender());
        emailService.notifyUser("Ваш заказ подтверждён");

        NotificationService smsService = new NotificationService(new SmsSender());
        smsService.notifyUser("Код подтверждения: 4821");
    }
}