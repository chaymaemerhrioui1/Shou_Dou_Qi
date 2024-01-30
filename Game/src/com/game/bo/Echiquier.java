package com.game.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

//import com.game.tools.Point;

import java.util.logging.Logger;

public class Echiquier {
    private static final Logger logger = Logger.getLogger(Echiquier.class.getName());

    private static Echiquier echiquier;
    //crée une liste vide appelée "player1" qui contiendra les pièces du joueur 1.
    private List<Piece> player1 = new ArrayList<Piece>();
    private List<Piece> player2 = new ArrayList<Piece>();
    private int turn = 1;// -1-->player 1 , 1--->player 2
    //crée une liste vide appelée "listPiegePlayer1" qui contiendra les emplacements des pièges du joueur 1
    private List<Point> listPiegePlayer1 = new ArrayList<Point>();
    private List<Point> listPiegePlayer2 = new ArrayList<Point>();
    //crée une liste vide appelée "listWaterPlace" qui contiendra les emplacements des points d'eau sur le plateau.
    private List<Point> listWaterPlace = new ArrayList<Point>();
    private Connection con;
    private Statement st;

    // il faut instancier les postion ici dans le constructeur

    public Echiquier() {
        try {
            // apeel au Driver
            Class.forName("com.mysql.jdbc.Driver");
            // con pour se connecter a ma base de donnee(jeu xou dou qi contenat les tables necessaires
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeu xou dou qi", "root", "");
            // creer statement
            st = con.createStatement();
            // si vous etes connectes on affiche message de bienvenue
            System.out.println("*********************************Bienvenue <_>,votre connection est bien connecte !! ******************************************");
        } catch (Exception ex) {
            logger.severe("********** Notre exception s'est produite  ********** : " + ex.getMessage());
            ex.printStackTrace();
            // sinon en declanche un erreur
            //System.out.println("Error" +ex);
        }
        //crée une nouvelle pièce "lionB" en utilisant la classe "Lion", en lui attribuant power de 7,
        // color -1, une position de départ de (0,6), special mouvement 2 et une
        // référence à l'échiquier sur lequel la pièce est placée.
        Piece lionB = new Lion(7, -1, new Point(0, 6), 2, this);
        Piece tigreB = new Tigre(6, -1, new Point(0, 0), 2, this);
        Piece panthereB = new Panthere(5, -1, new Point(2, 4), 0, this);
        Piece loupB = new Loup(4, -1, new Point(2, 2), 0, this);
        Piece chienB = new Chien(3, -1, new Point(1, 5), 0, this);
        Piece chatB = new chat(2, -1, new Point(1, 1), 0, this);
        Piece ratB = new Rat(1, -1, new Point(2, 6), 1, this);
        Piece elephantB = new Elephant(8, -1, new Point(2, 0), 0, this);
        Point piege1B = new Point(0, 2);
        Point piege2B = new Point(1, 3);
        Point piege3B = new Point(0, 4);

        //on ajoute les pièces du joueur 1 à la liste "player1"

        player1.add(elephantB);
        player1.add(chienB);
        player1.add(panthereB);
        player1.add(tigreB);
        player1.add(ratB);
        player1.add(chatB);
        player1.add(loupB);
        player1.add(lionB);

        //créent  les emplacements des pièges du joueur 1.

        listPiegePlayer1.add(piege1B);
        listPiegePlayer1.add(piege3B);
        listPiegePlayer1.add(piege2B);

        Piece lionN = new Lion(7, 1, new Point(8, 0), 2, this);
        Piece tigreN = new Tigre(6, 1, new Point(8, 6), 2, this);
        Piece panthereN = new Panthere(5, 1, new Point(6, 2), 0, this);
        Piece loupN = new Loup(4, 1, new Point(6, 4), 0, this);
        Piece chienN = new Chien(3, 1, new Point(7, 1), 0, this);
        Piece chatN = new chat(2, 1, new Point(7, 5), 0, this);
        Piece ratN = new Rat(1, 1, new Point(6, 0), 1, this);
        Piece elephantN = new Elephant(8, 1, new Point(6, 6), 0, this);
        Point piege1N = new Point(8, 4);
        Point piege2N = new Point(7, 3);
        Point piege3N = new Point(8, 2);

        player2.add(elephantN);
        player2.add(chienN);
        player2.add(panthereN);
        player2.add(tigreN);
        player2.add(ratN);
        player2.add(chatN);
        player2.add(loupN);
        player2.add(lionN);

        listPiegePlayer2.add(piege1N);
        listPiegePlayer2.add(piege3N);
        listPiegePlayer2.add(piege2N);


        //créent les emplacements des points d'eau et les ajoutent à la liste "listWaterPlace".
        //(la riviere)
        Point water1 = new Point(3, 1);
        Point water2 = new Point(3, 2);
        Point water3 = new Point(4, 1);
        Point water4 = new Point(4, 2);
        Point water5 = new Point(5, 1);
        Point water6 = new Point(5, 2);
        Point water7 = new Point(3, 4);
        Point water8 = new Point(3, 5);
        Point water9 = new Point(4, 4);
        Point water10 = new Point(4, 5);
        Point water11 = new Point(5, 4);
        Point water12 = new Point(5, 5);

        //créent les emplacements des points d'eau et les ajoutent à la liste "listWaterPlace".
        listWaterPlace.add(water12);
        listWaterPlace.add(water11);
        listWaterPlace.add(water10);
        listWaterPlace.add(water9);
        listWaterPlace.add(water8);
        listWaterPlace.add(water7);
        listWaterPlace.add(water6);
        listWaterPlace.add(water5);
        listWaterPlace.add(water4);
        listWaterPlace.add(water3);
        listWaterPlace.add(water2);
        listWaterPlace.add(water1);

    }

    //qui retourne l'instance unique de l'échiquier. Cette méthode suit également le modèle de conception "Singleton" en s'assurant qu'une seule instance de la
    // classe "Echiquier" peut être créée
    public static Echiquier getInstance() {
        if (Echiquier.echiquier == null)
            echiquier = new Echiquier();
        return echiquier;
    }

    public List<Piece> getPlayer1() {
        return player1;
    }

    public void setPlayer1(List<Piece> player1) {
        this.player1 = player1;
    }

    public List<Piece> getPlayer2() {
        return player2;
    }

    public void setPlayer2(List<Piece> player2) {
        this.player2 = player2;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<Point> getListPiegePlayer1() {
        return listPiegePlayer1;
    }

    public void setListPiegePlayer1(List<Point> listPiegePlayer1) {
        this.listPiegePlayer1 = listPiegePlayer1;
    }

    public List<Point> getListPiegePlayer2() {
        return listPiegePlayer2;
    }

    public void setListPiegePlayer2(List<Point> listPiegePlayer2) {
        this.listPiegePlayer2 = listPiegePlayer2;
    }

    public List<Point> getListWaterPlace() {
        return listWaterPlace;
    }

    public void setListWaterPlace(List<Point> listWaterPlace) {
        this.listWaterPlace = listWaterPlace;
    }

    //Cette méthode est utilisée pour changer le tour des joueurs. Elle bascule simplement entre les valeurs 1 et -1 pour identifier
    // le joueur en cours. Si le joueur actuel a perdu, la méthode affiche, un message indiquant qu'il a perdu et quitte le jeu.
    public void switchPlayer() {
        this.turn = -this.turn+3;
        if (isLose()) {
            this.showEchiquier();
            System.err.println("le joueur  " + this.turn + "  perdu");
            System.exit(0);
        }
    }
    //Cette méthode vérifie si un point est situé à l'intérieur du plateau de jeu. Elle prend en entrée un objet Point qui représente les coordonnées d'un point et retourne true si le point est situé à l'intérieur du plateau, et false sinon
    public boolean isPointInEchiquier(Point position) {
        /*
          respecter les dimesntion du echiquier sinon declancher une exception dans
          piece
         */
        return (position.getX() >= 0 && position.getX() <= 8) && (position.getY() >= 0 && position.getY() <= 6);
    }

    //Cette méthode vérifie si un point est situé sur une case d'eau. Elle prend en entrée un objet Point et retourne true si le point est situé sur une case d'eau, et false sinon.
    public boolean isPointWater(Point position) {
        // point savoir si une du water test suur la liste
        return listWaterPlace.contains(position);
    }

    //Cette méthode vérifie si le joueur en cours a perdu. Elle vérifie si le joueur a encore des pièces et si le trône est toujours en sécurité. Si le joueur a perdu, la méthode retourne true, sinon elle retourne false
    public boolean isLose() {
        /*
          pour chaque tour de switch user on va teste list ds pices ou la trone still
          existe
         */

        if (turn == 1) {
            if (this.player1.size() == 0 || !this.isTroneGood()) {
                return true;
            } else {
                return false;
            }}

        else {
            if (this.player2.size() == 0 || !this.isTroneGood()) {
                return true;
            } else {
                return false;
            }}

    }

    //Cette méthode vérifie si le trône est en sécurité. Si le trône est occupé par une pièce ennemie ou s'il est situé sur une case d'eau, la méthode retourne false, sinon elle retourne true.
    public boolean isTroneGood() {
        /*
          on va teste ou on va mettre un point si adversaire attient l'autre va perdu
          on va tester le point du trone is Vide pour chaque joueur

         */
        if (turn == 1) {
            return isPointVide(new Point(0, 3));
        } else {
            return isPointVide(new Point(8, 3));
        }

    }

    //Cette méthode vérifie si un point donné est vide, c'est-à-dire s'il n'y a pas de pièce sur ce point.
    // Elle prend en entrée un objet Point et retourne true si le point est vide, et false sinon.
    public boolean isPointVide(Point pointTest) {
        /*
          Point si le point est vide apres l'iteration du listPiece et sa
                      position */
        boolean isVide = true;
        for (Piece point : player1) {
            if (point.getPosition().equals(pointTest)) {
                isVide = false;
                break;
            }
        }

        for (Piece piece : player2) {
            if (piece.getPosition().equals(pointTest)) {
                isVide = false;
                break;
            }
        }

        return isVide;
    }

    //Cette méthode vérifie si un point donné contient une pièce appartenant au joueur en cours.
    // Elle prend en entrée un objet Point et retourne true si le point contient une pièce appartenant au joueur en cours, et false sinon
    public boolean isThereMyPiece(Point pointTest) {
        /*
         Point si le point est vide apres l'iteration du listPiece et sa
                      position
         */
        boolean isVide = true;
        if (this.turn == 1) {
            for (Piece point : player1) {
                if (point.getPosition().equals(pointTest)) {
                    isVide = false;
                    break;
                }
            }
        } else {
            for (Piece piece : player2) {
                if (piece.getPosition().equals(pointTest)) {
                    isVide = false;
                    break;
                }
            }
        }
        return isVide;
    }



    //Cette méthode supprime une pièce du plateau de jeu. Elle prend en entrée un objet Piece à supprimer
    public void removePiece(Piece pieceRmv) {
        /*
          pour elemener dans unn deplacement du l'adversaire donc on va jouer sur le
          role
         */
        this.player1.remove(pieceRmv);
        this.player2.remove(pieceRmv);

    }

    //Cette méthode affiche le plateau de jeu sur la console
    public String showEchiquier() {
        StringBuilder stringBuilder = new StringBuilder();

        /*
          pour afficher la console
          String tes=""; tes=tes+" "; System.out.println(tes.substring(0,8));
         */

        for (int j = 8; j >= 0; j--) {
            for (int i = 0; i < 7; i++) {
                String string="";
                if ((j >= 3 && j <= 5) && (i == 1 || i == 2 || i == 4 || i == 5)) {
                    //string="\u001B[34mW\u001B[0m";
                    string="\u001B[34m" +"W" + "\u001B[0m";

                }

                if ((j == 8 && i == 4) || (j == 7 && i == 3) || (j == 8 && i == 2)) {
                    // Coloration en rose bébé pour "S2"
                    string = "\u001B[95mS2\u001B[0m";
                }

                if ((j == 0 && i == 2) || (j == 1 && i == 3) || (j == 0 && i == 4)) {
                    // Coloration en rose bébé pour "S1"
                    string = "\u001B[95mS1\u001B[0m";
                }
                if (j == 0 && i == 3) {
                    // Coloration en marron pour "T1"
                    string = "\u001B[33mT1\u001B[0m";
                }

                if (j == 8 && i == 3) {
                    // Coloration en marron pour "T2"
                    string = "\u001B[33mT2\u001B[0m";
                }

                Piece p = this.getPieceAt(new Point(j, i));
                if (p != null) {
                    //System.out.print(p.pieceIcon());
                    string += String.format("%-12s", p.pieceIcon());
                }else {
                    string += "           ";
                }

                System.out.print(string + "┇");
            }
            System.out.println();
            //System.out.println("⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯⋯");
            System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");
        }
        System.out.println("******* Joueur 1 : "+this.player1.size()+" ******* Joueur 2 : "+this.player2.size()+" *******");
        return stringBuilder.toString();
    }

    //permet de retourner la pièce qui est sur une case donnée
    public Piece getPieceAt(Point pointTest) {
        /*
          point pour facilter traitement de deplacement
         */

        for (Piece it : player1) {
            if (it.getPosition().equals(pointTest)) {
                return it;
            }
        }

        for (Piece it : player2) {
            if (it.getPosition().equals(pointTest)) {
                return it;
            }
        }

        return null;
    }

    // retourne la liste des pièces du joueur dont c'est le tour.
    public List<Piece> getPieceByTurn() {
        if (this.turn == 1) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

    // retourne la pièce correspondant à une certaine étiquette
    public Piece getPieceByLabel(String label) {
        Piece choosenPiece = null;
        List<Piece> listPiece = this.getPieceByTurn();
        for (Piece piece : listPiece) {
            if (piece.pieceIcon().equals(label)) {
                return piece;
            }
        }

        return choosenPiece;
    }

    public void enregistrerDeplacement(Piece piece, Point depart, Point arrivee) {
        try {
            // Établir une connexion à la base de données
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeu xou dou qi", "root", "");

            // Préparer la requête SQL pour insérer le déplacement dans la base de données
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO deplacements (piece, depart_x, depart_y, arrivee_x, arrivee_y) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, piece.pieceIcon().toString());
            preparedStatement.setInt(2, depart.getX());
            preparedStatement.setInt(3, depart.getY());
            preparedStatement.setInt(4, arrivee.getX());
            preparedStatement.setInt(5, arrivee.getY());

            // Exécuter la requête SQL
            preparedStatement.executeUpdate();


            // Fermer la connexion à la base de données
            connection.close();
        } catch (ClassNotFoundException e) {
            logger.severe("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.severe("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();

        }
    }

    public static void Sauvegarderpartie(Piece piece, Point depart, Point arrivee) {

        try {
            // Établir une connexion à la base de données
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeu xou dou qi", "root", "");

            // Préparer la requête SQL pour insérer le déplacement dans la base de données
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM deplacements ");
            preparedStatement.setString(1, piece.pieceIcon().toString());
            preparedStatement.setInt(2, depart.getX());
            preparedStatement.setInt(3, depart.getY());
            preparedStatement.setInt(4, arrivee.getX());
            preparedStatement.setInt(5, arrivee.getY());

            // Exécuter la requête SQL
            preparedStatement.executeQuery();
            // Fermer la connexion à la base de données
            connection.close();
        } catch (ClassNotFoundException e) {
            logger.severe("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.severe("Une exception s'est produite : " + e.getMessage());
            e.printStackTrace();
        }
    }




}