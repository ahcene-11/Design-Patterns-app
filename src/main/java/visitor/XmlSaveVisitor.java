package visitor;

import model.Circle;
import model.Ellipse;
import model.Group;
import model.Line;
import model.Rectangle;
import model.Shape;

/**
 * Visiteur concret : Génère le format XML du dessin.
 */
public class XmlSaveVisitor implements ShapeVisitor {

    private StringBuilder xmlBuilder;
    private int indentLevel; // Pour faire un XML propre et indenté

    public XmlSaveVisitor() {
        this.xmlBuilder = new StringBuilder();
        this.indentLevel = 0;

        // Entête XML standard
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<drawing>\n");
    }

    /**
     * Retourne le texte XML final. À appeler une fois la visite terminée.
     */
    public String getResult() {
        return xmlBuilder.toString() + "</drawing>\n";
    }

    private String getIndent() {
        return "  ".repeat(indentLevel + 1); // Indentation de base + niveau
    }

    @Override
    public void visitLine(Line line) {
        xmlBuilder.append(String.format(
                "%s<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" color=\"%s\"/>\n",
                getIndent(), line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                line.getColor().toString().toLowerCase()
        ));
    }

    @Override
    public void visitRectangle(Rectangle rectangle) {
        xmlBuilder.append(String.format(
                "%s<rect x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" color=\"%s\"/>\n",
                getIndent(), rectangle.getX1(), rectangle.getY1(), rectangle.getX2(), rectangle.getY2(),
                rectangle.getColor().toString().toLowerCase()
        ));
    }

    @Override
    public void visitCircle(Circle circle) {
        xmlBuilder.append(String.format(
                "%s<circ x=\"%d\" y=\"%d\" radius=\"%d\" color=\"%s\"/>\n",
                getIndent(), circle.getX(), circle.getY(), circle.getRadius(),
                circle.getColor().toString().toLowerCase()
        ));
    }

    @Override
    public void visitEllipse(Ellipse ellipse) {
        xmlBuilder.append(String.format(
                "%s<elli x=\"%d\" y=\"%d\" rx=\"%d\" ry=\"%d\" color=\"%s\"/>\n",
                getIndent(), ellipse.getX(), ellipse.getY(), ellipse.getRx(), ellipse.getRy(),
                ellipse.getColor().toString().toLowerCase()
        ));
    }

    @Override
    public void visitGroup(Group group) {
        xmlBuilder.append(String.format("%s<group name=\"%s\">\n", getIndent(), group.getName()));

        // On "descend" dans le groupe
        indentLevel++;
        for (Shape child : group.getChildren()) {
            child.accept(this);
        }
        indentLevel--;

        xmlBuilder.append(String.format("%s</group>\n", getIndent()));
    }
}