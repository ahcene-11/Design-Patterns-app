package io; //  package dédié aux entrées/sorties

import builder.DrawingBuilder;
import factory.CommandRegistry;
import model.Shape;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Directeur qui lit un fichier XML et orchestre la création (via la Fabrique)
 * et l'assemblage (via le Builder).
 */
public class XmlDrawingDirector {

    private DrawingBuilder builder;
    private CommandRegistry registry;

    public XmlDrawingDirector(DrawingBuilder builder, CommandRegistry registry) {
        this.builder = builder;
        this.registry = registry;
    }

    /**
     * Lance la lecture du fichier XML et la construction du dessin.
     */
    public void construct(String filePath) throws Exception {
        File inputFile = new File(filePath);

        // --- validation XSD ---
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // On charge ton fichier XSD
            Schema schema = factory.newSchema(new File("drawing.xsd"));
            Validator validator = schema.newValidator();
            // On vérifie le fichier VEC contre les règles du XSD
            validator.validate(new StreamSource(inputFile));
            System.out.println("Validation XSD réussie, le fichier est conforme.");
        } catch (Exception e) {
            throw new Exception("Le fichier XML est invalide (non conforme au XSD) : " + e.getMessage());
        }
        builder.reset();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        parseNode(doc.getDocumentElement());
    }
    /**
     * Méthode récursive pour parcourir l'arbre XML.
     */
    private void parseNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String nodeName = element.getNodeName().toLowerCase();

            if (nodeName.equals("group")) {
                builder.beginGroup(element.getAttribute("name"));
            }
            //Si c'est une forme connue, on la crée via la Fabrique
            else if (registry.supports(nodeName)) {
                Map<String, String> props = extractAttributes(element);
                // La factory crée la forme
                Shape shape = registry.createShape(nodeName, props);
                // Le Builder l'ajoute au bon endroit
                builder.buildShape(shape);
            }

            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                parseNode(children.item(i));
            }

            if (nodeName.equals("group")) {
                builder.endGroup();
            }
        }
    }

    /**
     * Extrait tous les attributs XML d'un noeud et les place dans une map
     */
    private Map<String, String> extractAttributes(Element element) {
        Map<String, String> props = new HashMap<>();
        NamedNodeMap attributes = element.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            props.put(attr.getNodeName(), attr.getNodeValue());
        }
        return props;
    }
}