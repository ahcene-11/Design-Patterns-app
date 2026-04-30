package model;

import visitor.ShapeVisitor;

/**
 * Represente une ligne définie par deux points et une couleur.
 */
public class Line implements Shape {
    private int x1, y1, x2, y2;
    private Color color; // Plus besoin de "model."

    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visitLine(this);
    }
}