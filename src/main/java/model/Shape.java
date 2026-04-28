package model;


import visitor.ShapeVisitor;

/**
 * Interface définissant tout élément graphique (feuille ou composite).
 */
public interface Shape {
    /**
     * Retourne la couleur de la forme.
     */
    public Color getColor();


    void accept(ShapeVisitor visitor);
}