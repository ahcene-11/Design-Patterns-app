package model;

import java.util.*;

/**
 * Représente le dessin global de l'application. C'est la classe centrale du modèle, elle contient toutes les formes (y compris les groupes) et gère les opérations de base sur le dessin (ajout/suppression de formes, regroupement/dégroupement).
 * C'est aussi la classe qui implémente le pattern Observer pour notifier les vues lorsque le dessin change. Les vues s'abonnent à cette classe pour être informées des modifications et se mettre à jour en conséquence.
 */
public class Drawing {

    private List<Shape> shapes;
    private List<DrawingObserver> observers;

    public Drawing() {
        this.shapes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // --- Gestion du dessin ---

    public void addShape(Shape shape) {
        shapes.add(shape);
        notifyObservers();
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


    /**
     * Regroupe plusieurs formes existantes dans un nouveau groupe.
     * @param indices Liste des indices (0-based) des formes à regrouper.
     * @param newGroup Le groupe dans lequel les formes seront placées.
     */
    public void groupShapes(List<Integer> indices, Group newGroup) {
        if (indices.isEmpty()) return;

        // On trie les indices par ordre décroissant pour ne pas fausser
        // les index lors de la suppression dans la liste
        indices.sort(Collections.reverseOrder());
        List<Shape> shapesToGroup = new ArrayList<>();

        // On extrait les formes
        for (int index : indices) {
            if (index >= 0 && index < shapes.size()) {
                shapesToGroup.add(shapes.remove(index));
            }
        }

        // On les ajoute au groupe dans l'ordre d'origine (d'où le reverse)
        Collections.reverse(shapesToGroup);
        for (Shape s : shapesToGroup) {
            newGroup.add(s);
        }

        // On ajoute le groupe au dessin et on notifie la vue
        shapes.add(newGroup);
        notifyObservers();
    }

    /**
     * Dissout un groupe et remet ses enfants à la racine du dessin.
     * @param index Index du groupe à dissoudre.
     */
    public void ungroupShapes(int index) {
        if (index >= 0 && index < shapes.size()) {
            Shape s = shapes.get(index);
            if (s instanceof Group) {
                Group g = (Group) shapes.remove(index);
                // On remet tous les enfants à la racine
                shapes.addAll(g.getChildren());
                notifyObservers();
            } else {
                throw new IllegalArgumentException("L'élément à l'index " + (index+1) + " n'est pas un groupe.");
            }
        }
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