package visitor;

import model.Circle;
import model.Ellipse;
import model.Group;
import model.Line;
import model.Rectangle;
import model.Shape;

/**
 * Visiteur concret : Affiche la liste des formes numérotées dans la console.
 */
public class ListVisitor implements ShapeVisitor {

    private int counter = 1;

    // empêche le compteur de s'emballer si on réutilise le même visiteur
    public void reset() {
        this.counter = 1;
    }

    @Override
    public void visitLine(Line line) {
        System.out.printf("%d line %d %d %d %d %s%n",
                counter++, line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                line.getColor().toString().toLowerCase());
    }

    @Override
    public void visitRectangle(Rectangle rectangle) {
        System.out.printf("%d rect %d %d %d %d %s%n",
                counter++, rectangle.getX1(), rectangle.getY1(), rectangle.getX2(), rectangle.getY2(),
                rectangle.getColor().toString().toLowerCase());
    }

    @Override
    public void visitCircle(Circle circle) {
        System.out.printf("%d circ %d %d %d %s%n",
                counter++, circle.getX(), circle.getY(), circle.getRadius(),
                circle.getColor().toString().toLowerCase());
    }

    @Override
    public void visitEllipse(Ellipse ellipse) {
        System.out.printf("%d elli %d %d %d %d %s%n",
                counter++, ellipse.getX(), ellipse.getY(), ellipse.getRx(), ellipse.getRy(),
                ellipse.getColor().toString().toLowerCase());
    }

    @Override
    public void visitGroup(Group group) {
        System.out.printf("%d group [%s]%n", counter++, group.getName());

        // Selon le sujet, la commande "list" montre l'état du niveau courant.
        // Si on veut afficher le contenu des groupes de manière imbriquée, décommente ceci :
        /*
        for (Shape child : group.getChildren()) {
            child.accept(this);
        }
        */
    }
}