package builder;

import model.Drawing;
import model.Shape;

/**
 * Interface définissant l'assemblage d'un dessin complexe.
 * (Rôle : Builder)
 */
public interface DrawingBuilder {

    // Réinitialise le builder pour un nouveau dessin
    void reset();

    // Gestion de la hiérarchie (l'arbre Composite)
    void beginGroup(String name);
    void endGroup();

    // Ajoute une forme au niveau courant
    void buildShape(Shape shape);

    // Retourne le dessin final assemblé
    Drawing getResult();
}