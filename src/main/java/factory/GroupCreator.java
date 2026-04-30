package factory;

import model.Group;
import model.Shape;
import java.util.Map;

/**
 * Créateur concret pour la forme Group.
 */
public class GroupCreator implements ShapeCreator {
    @Override
    public Shape create(Map<String, String> props) {
        return new Group(props.get("name"));
    }
}