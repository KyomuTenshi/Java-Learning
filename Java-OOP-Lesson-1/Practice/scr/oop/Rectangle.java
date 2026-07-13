package scr.oop;

public class Rectangle extends Shape implements Drawable {
    private double width;
    private double height;

    public Rectangle(double wight, double height) {
        this.width = wight;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("+------+");
        System.out.println("|      |");
        System.out.println("+------+");
        System.out.println("(Это прямоугольник " + width + "x" + height + ")");
    }
}
