package facade;

import builder.CompositeDrawingBuilder;
import builder.DrawingBuilder;
import factory.CommandRegistry;
import io.XmlDrawingDirector;
import model.Drawing;
import visitor.GraphicsVisitor;
import visitor.ShapeVisitor;
import visitor.XmlSaveVisitor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        File file = new File(filePath);

        if (!file.exists()) {
            throw new Exception("Le fichier est introuvable : " + file.getAbsolutePath());
        }

        DrawingBuilder builder = new CompositeDrawingBuilder();
        XmlDrawingDirector director = new XmlDrawingDirector(builder, registry);

        director.construct(filePath);
        return builder.getResult();
    }

    /**
     * Sauvegarde un dessin dans un fichier XML en utilisant le Visiteur.
     */
    public void saveDrawing(Drawing drawing, String destPath) throws IOException {
        ShapeVisitor visitor = new XmlSaveVisitor();
        File outputFile = new File(destPath);

        // --- cree le dossier parent s'il n'existe pas ---
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
        // On visite toutes les formes du dessin
        for (model.Shape shape : drawing.getShapes()) {
            shape.accept(visitor);
        }

        // On écrit le résultat du visiteur dans le fichier
        try (FileWriter writer = new FileWriter(destPath)) {
            writer.write(((XmlSaveVisitor) visitor).getResult());
        }
    }

    /**
     * Réalise le travail complet du programme "merge" :
     * Charge deux fichiers, les fusionne dans un nouveau dessin, et sauvegarde.
     */
    public void mergeDrawings(String fileA, String fileB, String destFile) throws Exception {

        Drawing drawingA = loadDrawing(fileA);
        Drawing drawingB = loadDrawing(fileB);

        Drawing mergedDrawing = new Drawing();

        // On transfère les formes de A et B dans le nouveau dessin
        for (model.Shape s : drawingA.getShapes()) {
            mergedDrawing.addShape(s);
        }
        for (model.Shape s : drawingB.getShapes()) {
            mergedDrawing.addShape(s);
        }

        saveDrawing(mergedDrawing, destFile);
    }

    /**
     * Exporte un dessin vectoriel vers une image matricielle (PNG).
     * Réutilise le GraphicsVisitor initialement prévu pour l'interface graphique.
     */
    public void exportToBitmap(Drawing drawing, String destPath) throws IOException {
        // Définition de la taille de l'image (par défaut 800x600 ici)
        int width = 800;
        int height = 600;

        //  Création de l'image en mémoire
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Préparation de la toile (Fond blanc et lissage des traits)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // On dessine sur l'image exactement comme sur l'écran
        ShapeVisitor renderer = new GraphicsVisitor(g2d);
        for (model.Shape shape : drawing.getShapes()) {
            shape.accept(renderer);
        }

        // Libération des ressources graphiques
        g2d.dispose();

        //  Sauvegarde de l'image sur le disque dur au format PNG
        File outputFile = new File(destPath);
        // --- cree le dossier parent s'il n'existe pas ---
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
        ImageIO.write(image, "PNG", outputFile);
    }
}