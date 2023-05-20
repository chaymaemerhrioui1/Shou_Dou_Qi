package com.console;

import com.game.classes.Echiquier;
import com.game.classes.Piece;
import com.game.classes.Position;
import com.game.tools.MoveException;
import com.game.tools.Point;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class ConsoleClass {
    static Position P ;
    private static int direction;

    public static void RandomContreRandom(Echiquier echiquier) {
        while (true) {
            echiquier.randomTurn();
            echiquier.showEchiquier();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public static void HumanContreHuman( Echiquier echiquier) {
        while (true) {
            System.out.println("saisir votre Commande , joueur  : "+echiquier.getTurn());
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            String[] trait = command.split(",");

            // echiquier.switchPlayer();
            Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
            if (choosenPiece != null && trait.length == 2) {
                System.out.println("Vous avez choisi : " + choosenPiece);
                System.out.println("Pour faire ce mouvement " + trait[1]);

                if ("h".equals(trait[1])) {
                    direction = 1;
                } else if ("b".equals(trait[1])) {
                    direction = 2;
                } else if ("d".equals(trait[1])) {
                    direction = 3;
                } else if ("g".equals(trait[1])) {
                    direction = 4;
                }
                try {

                    choosenPiece.movePiece(direction, 0);
                } catch (MoveException | NullPointerException e) {
                    System.out.println("Ne peut pas bouger");
                }

            } else {
                System.out.println("On ne peut pas trouvé cette piece ou votre commande est incorrecte");
            }

            echiquier.showEchiquier();
        }
    }

    public static void HumanContreMachine(Echiquier echiquier) {
        while (true) {
            if (echiquier.getTurn() == -1) {
                System.out.println("Saisir votre commande player " + echiquier.getTurn());
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();

                String[] trait = command.split(":");

                Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                if (choosenPiece != null && trait.length == 2) {
                    String move = trait[1];
                    int direction = 0;
                    if ("h".equals(move)) {
                        direction = 1;
                    } else if ("b".equals(move)) {
                        direction = 2;
                    } else if ("d".equals(move)) {
                        direction = 3;
                    } else if ("g".equals(move)) {
                        direction = 4;
                    }

                    try {
                        Point depart = choosenPiece.getPosition();
                        choosenPiece.movePiece(direction, 0);
                        Point arrivee = choosenPiece.getPosition();

                        // Enregistrer le déplacement dans la base de données
                        echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);
                    } catch (MoveException | NullPointerException e) {
                        System.out.println("Impossible de déplacer la pièce.");
                    }
                } else {
                    System.out.println("Impossible de trouver la pièce ou commande incorrecte.");
                }
            } else {
                // La machine joue
                Piece[] pieces = echiquier.getPieceByTurn().toArray(new Piece[0]);
                Piece choosenPiece = pieces[(int) (Math.random() * pieces.length)];
                int direction = (int) (Math.random() * 4) + 1;

                try {
                    Point depart = choosenPiece.getPosition();
                    choosenPiece.movePiece(direction, 0);
                    Point arrivee = choosenPiece.getPosition();

                    // Enregistrer le déplacement dans la base de données
                    echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);
                } catch (MoveException | NullPointerException e) {
                    // Si le déplacement est impossible, on en choisit un autre aléatoirement
                    while (true) {
                        choosenPiece = pieces[(int) (Math.random() * pieces.length)];
                        direction = (int) (Math.random() * 4) + 1;
                        try {
                            Point depart = choosenPiece.getPosition();
                            choosenPiece.movePiece(direction, 0);
                            Point arrivee = choosenPiece.getPosition();

                            // Enregistrer le déplacement dans la base de données
                            echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);

                            break;
                        } catch (MoveException | NullPointerException ex) {
                            // Si le déplacement est encore impossible, on continue la boucle
                        }
                    }
                }
            }
            echiquier.showEchiquier();
        }
    }
    public static void Sauvegarderpartie(Echiquier echiquier) {

        try {
            // Établir une connexion à la base de données
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeu xou dou qi", "root", "");

            // Préparer la requête SQL pour insérer le déplacement dans la base de données
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM deplacements ");
            Piece piece;
            piece = null;
            preparedStatement.setString(1, piece.pieceIcon().toString());
            Component depart = null;
            preparedStatement.setInt(2, depart.getX());
            preparedStatement.setInt(3, depart.getY());
            Component arrivee;
            arrivee = null;
            preparedStatement.setInt(4, arrivee.getX());
            preparedStatement.setInt(5, arrivee.getY());

            // Exécuter la requête SQL
            preparedStatement.executeQuery();
            Echiquier e =new Echiquier();
            e.showEchiquier();


            // Fermer la connexion à la base de données
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        Echiquier echiquier = Echiquier.getInstance();
        //Piece piece=Piece.getInstanceof();

        System.out.println("Saisir votre choix : ");
        System.out.println("1-Human VS Human");
        System.out.println("2-Human VS Machine");
        System.out.println("3-Random VS Random");
        System.out.println("4-Reprendre une partie");
        System.out.println("5-exit");
        int choose=0;
        Scanner sc=new Scanner(System.in);
        choose=sc.nextInt();
        echiquier.showEchiquier();
        System.out.println("Le jeu va commencera dans 5 secondes......\n");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch (choose) {
            case 1:
                HumanContreHuman(echiquier);
                break;
            case 2:
                HumanContreMachine(echiquier);
                break;
            case 3:
                RandomContreRandom(echiquier);
                break;
            case 4:
                Sauvegarderpartie(echiquier);
                break;
            case 5:
                System.exit(0);
                break;

            default:
                break;
        }

        }
    }
