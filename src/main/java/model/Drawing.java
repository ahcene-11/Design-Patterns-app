package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The main drawing workspace. It acts as the Observable Subject.
 */
public class Drawing {
    // Les données du dessin (la racine du Composite)
    private List<Shape> shapes;

    // La liste des observateurs (la vue)
    private List<DrawingObserver> observers;

    public Drawing() {
        this.shapes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // --- Gestion du dessin ---

    public void addShape(Shape shape) {
        shapes.add(shape);
        notifyObservers(); // On prévient que le dessin a changé
    }

    public void removeShape(Shape shape) {
        if (shapes.remove(shape)) {
            notifyObservers();
        }
    }

    public void clear() {
        shapes.clear();
        notifyObservers();
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    // --- Gestion du pattern Observer ---

    public void addObserver(DrawingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DrawingObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (DrawingObserver observer : observers) {
            observer.onDrawingChanged();
        }
    }
}