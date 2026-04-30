package factory;

import model.Color;
import model.Line;
import model.Shape;

import java.util.Map;

/**
 * Créateur concret pour la forme Line.
 */
public class LineCreator implements ShapeCreator {
    @Override
    public Shape create(Map<String, String> props) {
        return new Line(
                Integer.parseInt(props.get("x1")),
                Integer.parseInt(props.get("y1")),
                Integer.parseInt(props.get("x2")),
                Integer.parseInt(props.get("y2")),
                Color.valueOf(props.get("color").toUpperCase())
        );
    }
}