package scr.oop;

public class Circle extends Shape implements Drawable {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("  *** ");
        System.out.println(" * * ");
        System.out.println(" * * ");
        System.out.println("  *** ");
        System.out.println("(Это круг радиусом " + radius + ")");
    }
}
