package view;

import model.Drawing;
import model.DrawingObserver;
import model.Shape;
import visitor.GraphicsVisitor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * La vue graphique de l'application (Patron Observer).
 */
public class DrawingView extends JFrame implements DrawingObserver {

    private Drawing drawing;
    private DrawingPanel panel;

    public DrawingView(Drawing drawing) {
        this.drawing = drawing;

        // Configuration de la fenêtre
        this.setTitle("Éditeur Vectoriel - Vue temps réel");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centre la fenêtre à l'écran

        // Création du panneau de dessin blanc
        this.panel = new DrawingPanel();
        this.panel.setBackground(Color.WHITE);
        this.add(panel);

        // On affiche la fenêtre
        this.setVisible(true);
    }

    /**
     * Méthode déclenchée par le modèle (Drawing) dès qu'une forme est ajoutée.
     */
    @Override
    public void onDrawingChanged() {
        // On demande au panneau de se redessiner
        panel.repaint();
    }


    public void setDrawing(Drawing newDrawing) {
        this.drawing = newDrawing;
        // On force un rafraîchissement immédiat
        this.onDrawingChanged();
    }

    /**
     * Panneau interne qui s'occupe du rendu visuel.
     */
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Efface l'écran (fond blanc)

            // On active l'antialiasing pour avoir de beaux traits lisses
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);


            // On instancie notre visiteur de dessin, et on visite tout le modèle !
            GraphicsVisitor renderer = new GraphicsVisitor(g2d);
            for (Shape shape : drawing.getShapes()) {
                shape.accept(renderer);
            }
        }
    }
}