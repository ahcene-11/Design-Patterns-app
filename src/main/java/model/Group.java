package model;

import java.util.ArrayList;
import java.util.List;

public class Group implements Shape {
    private String name;
    private List<Shape> children = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public void add(Shape shape) {
        children.add(shape);
    }

    public void remove(Shape shape) {
        children.remove(shape);
    }

    public List<Shape> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        // Un groupe n'a pas de couleur propre dans le sujet
        return null;
    }

}