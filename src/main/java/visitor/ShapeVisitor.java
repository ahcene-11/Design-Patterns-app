package visitor;

import model.Circle;
import model.Ellipse;
import model.Group;
import model.Line;
import model.Rectangle;

/**
 * Interface du patron Visiteur.
 * Permet d'ajouter de nouvelles opérations
 * sans avoir à modifier le code des formes géométriques.
 */
public interface ShapeVisitor {
    void visitLine(Line line);
    void visitRectangle(Rectangle rectangle);
    void visitCircle(Circle circle);
    void visitEllipse(Ellipse ellipse);
    void visitGroup(Group group);
}