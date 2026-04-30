package factory;

import model.Shape;
import java.util.HashMap;
import java.util.Map;

/**
 * Registre qui associe une commande textuelle ou XML à la bonne fabrique (Factory Method).
 * Il agit comme un point d'entrée unique pour la création de toutes les formes.
 */
public class CommandRegistry {

    // Le dictionnaire qui lie le texte au Créateur
    private Map<String, ShapeCreator> creators;

    public CommandRegistry() {
        creators = new HashMap<>();

        // On enregistre toutes nos fabriques au démarrage
        creators.put("group", new GroupCreator());
        creators.put("line", new LineCreator());
        creators.put("rect", new RectangleCreator());
        creators.put("circ", new CircleCreator());
        creators.put("elli", new EllipseCreator());
    }

    /**
     * Point d'entrée pour l'interpréteur de commandes du terminal.
     * Transforme la commande texte en dictionnaire (Map), puis délègue la création.
     *  @param commandLine La commande brute (ex: "line 10 5 42 23 blue")
     * @return L'objet Shape instancié
     */
    public Shape executeCommand(String commandLine) {
        String[] args = commandLine.trim().split("\\s+");
        if (args.length == 0 || args[0].isEmpty()) {
            throw new IllegalArgumentException("Commande vide.");
        }

        String type = args[0].toLowerCase();
        Map<String, String> props = new HashMap<>();

        try {
            // Traduction des arguments selon le type de forme demandé
            switch (type) {
                case "line":
                case "rect":
                    props.put("x1", args[1]);
                    props.put("y1", args[2]);
                    props.put("x2", args[3]);
                    props.put("y2", args[4]);
                    props.put("color", args[5]);
                    break;
                case "circ":
                    props.put("x", args[1]);
                    props.put("y", args[2]);
                    props.put("radius", args[3]);
                    props.put("color", args[4]);
                    break;
                case "elli":
                    props.put("x", args[1]);
                    props.put("y", args[2]);
                    props.put("rx", args[3]);
                    props.put("ry", args[4]);
                    props.put("color", args[5]);
                    break;
                default:
                    throw new IllegalArgumentException("Commande inconnue : " + type);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Arguments manquants pour la commande : " + type);
        }

        // On appelle la méthode de création générique avec le type et les propriétés extraites
        return createShape(type, props);
    }

    /**
     * Point d'entrée "pur", idéal pour le Directeur XML.
     * Crée une forme directement à partir d'un type et d'un dictionnaire de propriétés.
     * @param type Le type de forme (ex: "line")
     * @param props Le dictionnaire des propriétés
     * @return L'objet Shape instancié
     */
    public Shape createShape(String type, Map<String, String> props) {
        ShapeCreator creator = creators.get(type.toLowerCase());

        if (creator == null) {
            throw new IllegalArgumentException("Type de forme non supporté : " + type);
        }

        // On délègue la création à la bonne fabrique concrète
        return creator.create(props);
    }

    /**
     * Vérifie si le registre possède une fabrique pour ce type de forme.
     * @param type Le type à vérifier (ex: "line", "triangle")
     * @return true si le type est supporté, false sinon.
     */
    public boolean supports(String type) {
        return creators.containsKey(type.toLowerCase());
    }
}