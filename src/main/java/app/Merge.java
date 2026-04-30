package app;

import facade.DrawingFacade;
/**
 * Programme utilitaire : Fusionne deux fichiers de dessin vectoriel (.vec) en un seul.
 */
public class Merge {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java app.Merge <fichier1.vec> <fichier2.vec> <fusion.vec>");
            return;
        }

        DrawingFacade facade = new DrawingFacade();
        try {
            facade.mergeDrawings(args[0], args[1], args[2]);
            System.out.println("Fusion réussie !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la fusion : " + e.getMessage());
        }
    }
}