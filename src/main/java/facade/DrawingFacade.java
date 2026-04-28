package facade;

import builder.CompositeDrawingBuilder;
import builder.DrawingBuilder;
import factory.CommandRegistry;
import io.XmlDrawingDirector;
import model.Drawing;
import visitor.XmlSaveVisitor;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Patron Façade : Masque la complexité du sous-système (création, parsing, visite)
 * et expose des méthodes simples pour les programmes utilitaires.
 */
public class DrawingFacade {

    private CommandRegistry registry;

    public DrawingFacade() {
        this.registry = new CommandRegistry();
    }

    /**
     * Charge un dessin depuis un fichier XML en utilisant le Directeur et le Monteur.
     */
    public Drawing loadDrawing(String filePath) throws Exception {
        DrawingBuilder builder = new CompositeDrawingBuilder();
        XmlDrawingDirector director = new XmlDrawingDirector(builder, registry);

        director.construct(filePath);
        return builder.getResult();
    }

    /**
     * Sauvegarde un dessin dans un fichier XML en utilisant le Visiteur.
     */
    public void saveDrawing(Drawing drawing, String destPath) throws IOException {
        XmlSaveVisitor visitor = new XmlSaveVisitor();

        // On visite toutes les formes du dessin
        for (model.Shape shape : drawing.getShapes()) {
            shape.accept(visitor);
        }

        // On écrit le résultat du visiteur dans le fichier
        try (FileWriter writer = new FileWriter(destPath)) {
            writer.write(visitor.getResult());
        }
    }

    /**
     * Réalise le travail complet du programme "merge" :
     * Charge deux fichiers, les fusionne dans un nouveau dessin, et sauvegarde.
     */
    public void mergeDrawings(String fileA, String fileB, String destFile) throws Exception {
        // 1. Chargement (la complexité est cachée dans la méthode loadDrawing)
        Drawing drawingA = loadDrawing(fileA);
        Drawing drawingB = loadDrawing(fileB);

        // 2. Création du dessin fusionné
        Drawing mergedDrawing = new Drawing();

        // On transfère les formes de A et B dans le nouveau dessin
        for (model.Shape s : drawingA.getShapes()) {
            mergedDrawing.addShape(s);
        }
        for (model.Shape s : drawingB.getShapes()) {
            mergedDrawing.addShape(s);
        }

        // 3. Sauvegarde
        saveDrawing(mergedDrawing, destFile);
    }
}