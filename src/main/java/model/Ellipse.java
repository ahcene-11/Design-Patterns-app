package model;


import visitor.ShapeVisitor;

public class Ellipse implements Shape {
    private int x, y, rx, ry;
    private Color color;

    public Ellipse(int x, int y, int rx, int ry, Color color) {
        this.x = x;
        this.y = y;
        this.rx = rx;
        this.ry = ry;
        this.color = color;
    }

    @Override
    public Color getColor() { return color; }


    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getRx() { return rx; }
    public int getRy() { return ry; }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visitEllipse(this);
    }
}