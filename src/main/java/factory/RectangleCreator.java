package factory;

import model.Color;
import model.Rectangle;
import model.Shape;

import java.util.Map;

/**
 * Créateur concret pour la forme Rectangle.
 */
public class RectangleCreator implements ShapeCreator {
    @Override
    public Shape create(Map<String, String> props) {
        return new Rectangle(
                Integer.parseInt(props.get("x1")),
                Integer.parseInt(props.get("y1")),
                Integer.parseInt(props.get("x2")),
                Integer.parseInt(props.get("y2")),
                Color.valueOf(props.get("color").toUpperCase())
        );
    }
}