package factory;

import model.Color;
import model.Ellipse;
import model.Shape;
import java.util.Map;

/**
 * Créateur concret pour la forme Ellipse.
 */
public class EllipseCreator implements ShapeCreator {
    @Override
    public Shape create(Map<String, String> props) {
        return new Ellipse(
                Integer.parseInt(props.get("x")),
                Integer.parseInt(props.get("y")),
                Integer.parseInt(props.get("rx")),
                Integer.parseInt(props.get("ry")),
                Color.valueOf(props.get("color").toUpperCase())
        );
    }
}