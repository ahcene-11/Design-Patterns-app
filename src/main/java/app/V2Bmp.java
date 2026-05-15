package app;

import facade.DrawingFacade;
import factory.CommandRegistry;
import model.Drawing;

/**
 * Programme utilitaire : Convertit un fichier de dessin vectoriel (.vec)
 * en image matricielle (.png).
 */
public class V2Bmp {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java V2Bmp <source.vec> <destination.png>");
            return;
        }

        String sourceFile = args[0];
        String destFile = args[1];

        CommandRegistry registry = new CommandRegistry();
        DrawingFacade facade = new DrawingFacade(registry);

        try {
            System.out.println("Chargement du dessin vectoriel...");
            Drawing drawing = facade.loadDrawing(sourceFile);

            System.out.println("Génération de l'image matricielle...");
            facade.exportToBitmap(drawing, destFile);

            System.out.println("Succès ! L'image a été sauvegardée dans : " + destFile);

        } catch (Exception e) {
            System.err.println("Erreur lors de la conversion : " + e.getMessage());
        }
    }
}