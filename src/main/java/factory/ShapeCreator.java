package factory;

import model.Shape;

import java.util.Map;

/**
 * Interface du patron Factory Method (Creator).
 *
 * Chaque implémentation concrète est responsable de la création
 * d'un type spécifique de forme (Line, Rectangle, Circle, etc.).
 *
 * Ce mécanisme permet de déléguer la logique d’instanciation
 * et de réduire le couplage avec les classes concrètes.
 */
public interface ShapeCreator {

    /**
     * Crée une instance de Shape à partir d’un ensemble de propriétés.
     *
     * Les propriétés représentent les paramètres nécessaires à la création
     * de la forme (coordonnées, rayon, couleur, etc.), généralement issues
     * du parsing d’une commande ou d’un fichier.
     * @param properties dictionnaire clé -> valeur contenant les paramètres de la forme
     * @return instance concrète de Shape
     */
    Shape create(Map<String, String> properties);
}