package builder;

import model.Drawing;
import model.Group;
import model.Shape;
import java.util.Stack;

/**
 * builder concret responsable d'assembler l'arbre Composite en mémoire.
 * Il utilise une pile (Stack) pour gérer l'imbrication des groupes.
 */
public class CompositeDrawingBuilder implements DrawingBuilder {

    private Drawing currentDrawing;
    private Stack<Group> groupStack;

    public CompositeDrawingBuilder() {
        this.groupStack = new Stack<>();
        this.reset();
    }

    @Override
    public void reset() {
        this.currentDrawing = new Drawing();
        this.groupStack.clear();
    }

    @Override
    public void beginGroup(String name) {
        Group newGroup = new Group(name);
        buildShape(newGroup);      // On attache ce nouveau groupe là où on se trouve
        groupStack.push(newGroup); // On "entre" dans ce groupe pour les prochaines formes
    }

    @Override
    public void endGroup() {
        if (!groupStack.isEmpty()) {
            groupStack.pop(); // On "sort" du groupe courant
        }
    }

    @Override
    public void buildShape(Shape shape) {
        if (groupStack.isEmpty()) {
            // Pas de groupe en cours, on ajoute à la racine du dessin
            currentDrawing.addShape(shape);
        } else {
            // On ajoute la forme au groupe situé au sommet de la pile
            groupStack.peek().add(shape);
        }
    }

    @Override
    public Drawing getResult() {
        return currentDrawing;
    }
}