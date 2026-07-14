package scr.basics;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadingDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger safeCounter = new AtomicInteger(0);

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                safeCounter.incrementAndGet();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join(); // ждем зарешения потока 1
        thread2.join(); // ждем зарешения потока 2

        System.out.println("Итоговое значение (должно быть 2000): " + safeCounter.get());
    }
}
