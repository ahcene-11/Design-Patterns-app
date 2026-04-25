package model;

public class Circle implements Shape {
    private int x, y, radius;
    private Color color;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Color getColor() { return color; }


    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getRadius() { return radius; }
}