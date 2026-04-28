package model;

/**
 * Interface for elements that want to be notified of drawing changes.
 */
public interface DrawingObserver {
    /**
     * Called whenever the drawing is modified (shape added, removed, cleared).
     */
    void onDrawingChanged();
}