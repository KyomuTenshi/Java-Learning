package scr.oop;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Circle(5));
        shapes.add(new Rectangle(4, 6));
        shapes.add(new Circle(2.5));

        for (Shape shape : shapes) {
            System.out.println("Площадь фигуры: " + shape.calculateArea());
        }

       System.out.println("\n--- Отрисовка фигур ---");
       for (Shape shape : shapes) {
        if (shape instanceof Drawable) {
            ((Drawable) shape).draw();
            System.out.println();
        }
       }
    }
}
