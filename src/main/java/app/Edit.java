package app;

import facade.DrawingFacade;
import factory.CommandRegistry;
import model.Drawing;
import model.Shape;
import visitor.ListVisitor;

import java.util.Scanner;

/**
 * Programme principal (Éditeur vectoriel).
 * Gère l'interpréteur de commandes interactif.
 */
public class Edit {

    public static void main(String[] args) {
        // 1. Initialisation de nos outils (Patrons)
        CommandRegistry registry = new CommandRegistry();
        DrawingFacade facade = new DrawingFacade();
        ListVisitor listVisitor = new ListVisitor();

        // 2. Initialisation du modèle de données (Composite + Observable)
        Drawing currentDrawing = new Drawing();

        // (Optionnel pour l'instant : Ajout de la vue graphique - Patron Observer)
        // GraphicWindow view = new GraphicWindow();
        // currentDrawing.addObserver(view);
        view.DrawingView window = new view.DrawingView(currentDrawing);
        currentDrawing.addObserver(window);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Éditeur Vectoriel Démarré ===");
        System.out.println("Tapez vos commandes (ex: line 10 5 42 23 blue). Tapez 'quit' pour quitter.");

        // 3. Boucle principale de l'interpréteur
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            // Découpage pour lire le premier mot (la commande)
            String[] parts = input.split("\\s+");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "quit":
                        running = false;
                        break;

                    case "new":
                        currentDrawing.clear(); // Vide la liste (et notifie l'Observer !)
                        System.out.println("Nouveau dessin initialisé.");
                        break;

                    case "list":
                        listVisitor.reset(); // On remet le compteur à 1
                        for (Shape s : currentDrawing.getShapes()) {
                            s.accept(listVisitor); // Magie du Visiteur
                        }
                        break;

                    case "save":
                        if (parts.length < 2) throw new IllegalArgumentException("Nom de fichier manquant.");
                        facade.saveDrawing(currentDrawing, parts[1]); // Magie de la Façade
                        System.out.println("Dessin sauvegardé dans " + parts[1]);
                        break;

                    case "load":
                        if (parts.length < 2) throw new IllegalArgumentException("Nom de fichier manquant.");
                        currentDrawing = facade.loadDrawing(parts[1]); // Magie de la Façade et du Builder

                        // On met à jour la référence dans la fenêtre
                        window.setDrawing(currentDrawing);

                        // On réabonne la fenêtre au nouveau dessin pour les futures commandes
                        currentDrawing.addObserver(window);
                        System.out.println("Dessin chargé depuis " + parts[1]);
                        break;

                    case "grp":
                    case "ugrp":
                        // La logique de groupe utilise les index de la liste.
                        // Je place un bouchon (stub) ici comme autorisé par le sujet.
                        System.out.println("[Bouchon] Commande de groupe à implémenter...");
                        break;

                    default:
                        // Si ce n'est pas une commande système, on demande à la Fabrique !
                        if (registry.supports(command)) {
                            // La Fabrique instancie la forme géométrique
                            Shape newShape = registry.executeCommand(input);

                            // On l'ajoute au dessin (CECI DÉCLENCHE L'OBSERVATEUR POUR LA FENÊTRE !)
                            currentDrawing.addShape(newShape);
                        } else {
                            System.out.println("Erreur : Commande inconnue '" + command + "'.");
                        }
                        break;
                }
            } catch (Exception e) {
                // Intercepte les erreurs (syntaxe, fichier introuvable, etc.) sans crasher le programme
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Au revoir ");
    }
}