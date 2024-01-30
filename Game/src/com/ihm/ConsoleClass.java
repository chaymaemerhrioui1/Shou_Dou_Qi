package com.ihm;

import com.game.bo.Echiquier;
import com.game.bo.Piece;
import com.network.ClientProg;
import com.network.ServerApp;
import com.game.exception.MoveException;
import com.game.bo.Point;

import javax.swing.text.Position;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;



public class ConsoleClass {
    private static int direction;

    public static void jouerreseau(Echiquier echiquier) {

        Scanner scan = new Scanner(System.in);
        System.out.println("saisir le mode du jeu : ");
        System.out.println("1)  join server");
        System.out.println("2)  create server");
        int response = scan.nextInt();
        if (response == 1) {
            serveurHuman(echiquier);
        }
        else if(response == 2){
            ClientHumain(echiquier);


        }
        else{
            System.out.println("choix invalide");
        }
    }


    public static void HumanContreHuman( Echiquier echiquier) {
        while (true) {
            System.out.println("saisir votre Commande , joueur  : "+echiquier.getTurn());
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            String[] trait = command.split(":");

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

            System.out.println(echiquier.showEchiquier());
        }
    }

    public static void HumanContreMachine(Echiquier echiquier) {
        while (true) {
            if (echiquier.getTurn() == 1) {
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
                Piece choosenPiece = null;
                int direction = 0;
                for (Piece piece : pieces) {
                    if (piece.canCapture()) {
                        choosenPiece = piece;
                        direction = piece.getBestCaptureDirection();
                        break;
                    }
                }

                // Si aucune capture possible, déplacement aléatoire
                if (choosenPiece == null) {
                    for (Piece piece : pieces) {
                        if (piece.canMove()) {
                            choosenPiece = piece;
                            direction = piece.getRandomMoveDirection();
                            break;
                        }
                    }
                }

                if (choosenPiece != null) {
                    try {
                        Point depart = choosenPiece.getPosition();
                        choosenPiece.movePiece(direction, 0);
                        Point arrivee = choosenPiece.getPosition();

                        // Enregistrer le déplacement dans la base de données
                        echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);
                    } catch (MoveException | NullPointerException e) {
                        // Si le déplacement est impossible, vous pouvez ajouter un autre comportement ici
                        System.out.println("Impossible de déplacer la pièce.");
                    }
                }}
            echiquier.showEchiquier();
        }
    }

    private static void ClientHumain(Echiquier echiquier) {

        Scanner sc = new Scanner(System.in);
        System.out.println("please entrer the network IP Adresse of the server number (for localhost the ip adresse is: 127.0.0.1)");
        String ipAdresse =  sc.next().trim();
        System.out.println("please entrer the network port number user by the server (Example: 5000)");
        int port  = sc.nextInt();

        ClientProg client = ClientProg.getConnnection(ipAdresse, port);
        Object rep= null;
        while (true) {

            if(echiquier.getTurn()==1) {
                System.out.println("saisir votre Commande player" + echiquier.getTurn());
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();

                String[] trait = command.split(":");


                Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                if (choosenPiece != null && trait.length == 2) {
                    System.out.println("you choose : " + choosenPiece);
                    System.out.println("to make this move " + trait[1]);

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
                        rep = client.send(command);
                    } catch (MoveException | NullPointerException e) {
                        System.out.println("Cant Move");
                        //break;
                    }

                } else {
                    System.out.println("Cant Find Piece Or command not correct");
                }

                System.out.println(echiquier.showEchiquier());

                System.out.println(trait[0]+trait[1]);

            }
            else{
                String[] trait = ((String) rep).split(":");

                Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                if (choosenPiece != null && trait.length == 2) {
                    System.out.println("you choose : " + choosenPiece);
                    System.out.println("to make this move " + trait[1]);

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
                        System.out.println("Cant Move");
                        //break;
                    }

                } else {
                    System.out.println("Cant Find Piece Or command not correct");
                }

                System.out.println(echiquier.showEchiquier());

            }

        }


    }

    private static void serveurHuman(Echiquier echiquier) {

        boolean noProblem;
        Scanner sc = new Scanner(System.in);

        Scanner input = new Scanner(System.in);
        System.out.println("please entrer the network port number (Example: 5000)");
        int port  = sc.nextInt();
        ServerApp server=null;
        try {
            server = new ServerApp(port);
        } catch (Exception e) {
            System.out.println("error occured when connecting, plz try later -_-");
            e.printStackTrace();

        }
        while(true){

            noProblem = true;

            if (noProblem) {

                if (echiquier.getTurn() == 1) {
                    try {

                        String receivedData;

                        // reads message from client
                        receivedData = (String) server.getIn().readObject();

                        //if it wants to close connection
                        if (receivedData == "end_connection") {

                            System.out.println("Closing connection");

                            // close connection
                            server.getIn().close();
                            server.getOut().close();
                            server.getSocket().close();
                        }


                        else {


                            //print the message received
                            System.out.println("Message received :" + receivedData);


                            String move = (String) receivedData;

                            String[] trait = move.split(":");

                            Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                            if (choosenPiece != null && trait.length == 2) {
                                System.out.println("you choose : " + choosenPiece);
                                System.out.println("to make this move " + trait[1]);

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
                                    System.out.println("Cant Move");
                                    //break;
                                }

                            } else {
                                System.out.println("Cant Find Piece Or command not correct");
                            }

                            System.out.println(echiquier.showEchiquier());
                        }
                    }catch( Exception e ){

                        e.printStackTrace();
                        break;
                    }

                }else{


                    System.out.println("saisir votre Commande player" + echiquier.getTurn());
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine();

                    String[] trait = command.split(":");


                    Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                    if (choosenPiece != null && trait.length == 2) {
                        System.out.println("you choose : " + choosenPiece);
                        System.out.println("to make this move " + trait[1]);

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
                            server.getOut().writeObject(command);
                        } catch (MoveException | NullPointerException e) {
                            System.out.println("Cant Move");
                            //break;

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        System.out.println("Cant Find Piece Or command not correct");
                    }

                    System.out.println(echiquier.showEchiquier());

                    System.out.println(trait[0]+trait[1]);


                }


            }


        }



    }



    /*public static void Sauvegarderpartie(Echiquier echiquier) {
        try {
            // Établir une connexion à la base de données
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeu_xou_dou_qi", "root", "");

            // Préparer la requête SQL pour insérer les mouvements dans la base de données
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO deplacements (piece, depart_x, depart_y, arrivee_x, arrivee_y) VALUES (?, ?, ?, ?, ?)");

            // Récupérer les mouvements actuels de l'échiquier
            //List<String> mouvements = echiquier.getMouvements();

            // Parcourir les mouvements et les enregistrer dans la base de données
            for (String mouvement : mouvements) {
                String[] trait = mouvement.split(":");
                String pieceLabel = trait[0];
                int direction = Integer.parseInt(trait[1]);

                Piece choosenPiece = echiquier.getPieceByLabel(pieceLabel);
                if (choosenPiece != null) {
                    Point depart = choosenPiece.getPosition();
                    Point arrivee = choosenPiece.movePiece(direction, 0);

                    // Enregistrer le déplacement dans la base de données
                    preparedStatement.setString(1, pieceLabel);
                    preparedStatement.setInt(2, depart.getX());
                    preparedStatement.setInt(3, depart.getY());
                    preparedStatement.setInt(4, arrivee.getX());
                    preparedStatement.setInt(5, arrivee.getY());
                    preparedStatement.executeUpdate();
                }
            }

            // Fermer la connexion à la base de données
            connection.close();

            System.out.println("Partie sauvegardée avec succès.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MoveException e) {
            throw new RuntimeException(e);
        }
    }
*/



    public static void main(String[] args) {

        Echiquier echiquier = Echiquier.getInstance();
        //Piece piece=Piece.getInstanceof();

        System.out.println("Saisir votre choix : ");
        System.out.println("1-Human VS Human");
        System.out.println("2-Human VS Machine");
        System.out.println("3-jouer reseau");
        System.out.println("4-Reprendre une partie");
        System.out.println("5-exit");
        int choose=0;
        Scanner sc=new Scanner(System.in);
        choose=sc.nextInt();
        System.out.println(echiquier.showEchiquier());
        System.out.println("Le jeu va commencera dans 2 secondes......\n");

        try {
            Thread.sleep(2000);
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
                jouerreseau(echiquier);
                break;
            case 4:
                //Sauvegarderpartie(echiquier);
                break;
            case 5:
                System.exit(0);
                break;

            default:
                break;
        }

    }
}
