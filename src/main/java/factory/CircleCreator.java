package factory;

import model.Circle;
import model.Color;
import model.Shape;
import java.util.Map;

/**
 * Créateur concret pour la forme Cercle.
 */
public class CircleCreator implements ShapeCreator {
    @Override
    public Shape create(Map<String, String> props) {
        return new Circle(
                Integer.parseInt(props.get("x")),
                Integer.parseInt(props.get("y")),
                Integer.parseInt(props.get("radius")),
                Color.valueOf(props.get("color").toUpperCase())
        );
    }
}