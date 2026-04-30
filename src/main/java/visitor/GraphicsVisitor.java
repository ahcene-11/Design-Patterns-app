package visitor;

import model.Circle;
import model.Ellipse;
import model.Group;
import model.Line;
import model.Rectangle;
import model.Shape;

import java.awt.Graphics2D;

/**
 * Visiteur concret : Dessine les formes sur un contexte graphique Java 2D.
 * Il fait le pont entre notre modèle vectoriel et l'affichage écran.
 */
public class GraphicsVisitor implements ShapeVisitor {

    private Graphics2D g2d;

    public GraphicsVisitor(Graphics2D g2d) {
        this.g2d = g2d;
    }

    /**
     * Convertit notre énumération de couleur en couleur système Java AWT.
     */
    private java.awt.Color getAwtColor(model.Color color) {
        switch (color) {
            case RED: return java.awt.Color.RED;
            case GREEN: return java.awt.Color.GREEN;
            case BLUE: return java.awt.Color.BLUE;
            default: return java.awt.Color.BLACK;
        }
    }

    @Override
    public void visitLine(Line line) {
        g2d.setColor(getAwtColor(line.getColor()));
        g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    @Override
    public void visitRectangle(Rectangle rectangle) {
        g2d.setColor(getAwtColor(rectangle.getColor()));
        // AWT drawRect attend (x, y, largeur, hauteur). donc il faut calculer :
        int width = Math.abs(rectangle.getX2() - rectangle.getX1());
        int height = Math.abs(rectangle.getY2() - rectangle.getY1());
        int x = Math.min(rectangle.getX1(), rectangle.getX2());
        int y = Math.min(rectangle.getY1(), rectangle.getY2());
        g2d.drawRect(x, y, width, height);
    }

    @Override
    public void visitCircle(Circle circle) {
        g2d.setColor(getAwtColor(circle.getColor()));
        // AWT drawOval trace l'ovale inscrit dans un rectangle.
        // Il faut décaler par le rayon pour que le (x,y) soit bien le centre.
        int d = circle.getRadius() * 2;
        g2d.drawOval(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(), d, d);
    }

    @Override
    public void visitEllipse(Ellipse ellipse) {
        g2d.setColor(getAwtColor(ellipse.getColor()));
        g2d.drawOval(ellipse.getX() - ellipse.getRx(), ellipse.getY() - ellipse.getRy(),
                ellipse.getRx() * 2, ellipse.getRy() * 2);
    }

    @Override
    public void visitGroup(Group group) {
        // Pour un groupe, on dessine simplement tous ses enfants
        for (Shape child : group.getChildren()) {
            child.accept(this);
        }
    }
}