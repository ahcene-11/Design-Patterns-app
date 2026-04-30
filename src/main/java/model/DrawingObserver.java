package model;

/**
 * Interface pour les observateurs du dessin. Permet de notifier les vues lorsque le dessin est modifié.
 */
public interface DrawingObserver {
    /**
     * Méthode appelée lorsque le dessin a été modifié. Les vues doivent se mettre à jour en conséquence.
     */
    void onDrawingChanged();
}