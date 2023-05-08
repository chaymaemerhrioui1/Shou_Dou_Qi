package com.game.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.tools.Point;

public class Echiquier {
    private static Echiquier echiquier;
    private List<Piece> player1 = new ArrayList<Piece>();
    private List<Piece> player2 = new ArrayList<Piece>();
    private int turn = -1;// -1-->player 1 , 1--->player 2
    private List<Point> listPiegePlayer1 = new ArrayList<Point>();
    private List<Point> listPiegePlayer2 = new ArrayList<Point>();
    private List<Point> listWaterPlace = new ArrayList<Point>();

    /**
     * TODO ajuter les places du trone
     */

    /**
     * il faut instancier les postion ici dans le constructeur
     */
    private Echiquier() {
        Piece lionB = new Lion(7, -1, new Point(0, 6), 2, this);
        Piece tigreB = new Tigre(6, -1, new Point(0, 0), 2, this);
        Piece panthereB = new Panthere(5, -1, new Point(2, 4), 0, this);
        Piece loupB = new Loup(4, -1, new Point(2, 2), 0, this);
        Piece chienB = new Chien(3, -1, new Point(1, 5), 0, this);
        Piece chatB = new Chat(2, -1, new Point(1, 1), 0, this);
        Piece ratB = new Rat(1, -1, new Point(2, 6), 1, this);
        Piece elephantB = new Elephant(8, -1, new Point(2, 0), 0, this);
        Point piege1B = new Point(0, 2);
        Point piege2B = new Point(1, 3);
        Point piege3B = new Point(0, 4);
        // TODO ajoute emplacement du trone point(0,3);

        player1.add(elephantB);
        player1.add(chienB);
        player1.add(panthereB);
        player1.add(tigreB);
        player1.add(ratB);
        player1.add(chatB);
        player1.add(loupB);
        player1.add(lionB);

        listPiegePlayer1.add(piege1B);
        listPiegePlayer1.add(piege3B);
        listPiegePlayer1.add(piege2B);

        Piece lionN = new Lion(7, 1, new Point(8, 0), 2, this);
        Piece tigreN = new Tigre(6, 1, new Point(8, 6), 2, this);
        Piece panthereN = new Panthere(5, 1, new Point(6, 2), 0, this);
        Piece loupN = new Loup(4, 1, new Point(6, 4), 0, this);
        Piece chienN = new Chien(3, 1, new Point(7, 1), 0, this);
        Piece chatN = new Chat(2, 1, new Point(7, 5), 0, this);
        Piece ratN = new Rat(1, 1, new Point(6, 0), 1, this);
        Piece elephantN = new Elephant(8, 1, new Point(6, 6), 0, this);
        Point piege1N = new Point(8, 4);
        Point piege2N = new Point(7, 3);
        Point piege3N = new Point(8, 2);
        // TODO ajoute emplacement du trone point(0,3);

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

    public void switchPlayer() {
        this.turn = -this.turn;
        if (isLose()) {
            this.showEchiquier();
            System.err.println("the player " + this.turn + " lose");
            System.exit(0);
        }
    }

    public boolean isPointInEchiquier(Point position) {
        /**
         * respecter les dimesntion du echiquier sinon declancher une exception dans
         * piece
         *
         * @param un Point
         */

        return (position.getX() >= 0 && position.getX() <= 8) && (position.getY() >= 0 && position.getY() <= 6);
    }

    public boolean isPointWater(Point position) {
        /**
         * @param point savoir si une du water test suur la liste
         */

        return listWaterPlace.contains(position);
    }

    public boolean isPiege(Point posiPoint) {
        /**
         * @param un point test sur la list pour rendre power zero
         */
        if (turn == -1) {
            return listPiegePlayer2.contains(posiPoint);
        } else {
            return listPiegePlayer1.contains(posiPoint);
        }
    }

    public boolean isLose() {
        /**
         * pour chaque tour de switch user on va teste list ds pices ou la trone still
         * existe
         */

        if (turn == -1) {
            if (this.player1.size() == 0 || !this.isTroneGood()) {
                return true;
            } else {
                return false;
            }

        }

        else {
            if (this.player2.size() == 0 || !this.isTroneGood()) {
                return true;
            } else {
                return false;
            }
        }

    }

    public boolean isTroneGood() {
        /**
         * on va teste ou on va mettre un point si adversaire attient l'autre va perdu
         * on va tester le point du trone is Vide pour chaque joueur
         *
         */
        if (turn == -1) {
            /**
             * TODO met la point du trone pour chaque user
             */
            return isPointVide(new Point(0, 3));
        } else {
            return isPointVide(new Point(8, 3));
        }

    }

    public boolean isPointVide(Point pointTest) {
        /**
         * @param Point si le point est vide apres l'iteration du listPiece et sa
         *              position
         */
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

    public boolean isThereMyPiece(Point pointTest) {
        /**
         * @param Point si le point est vide apres l'iteration du listPiece et sa
         *              position
         */
        boolean isVide = true;
        if (this.turn == -1) {
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

    public Piece choosePieceAt(Point point) {
        /**
         * pour chosir une piece pour faire les traimtment et sedeplacer sinon on
         * declanche une exception s'i existe pas
         */

        Piece piece = null;
        if (turn == -1) {
            for (Piece it : player1) {
                if (it.getPosition().equals(point)) {
                    piece = it;
                }

            }
        }

        else {
            for (Piece it : player2) {
                if (it.getPosition().equals(point)) {
                    piece = it;
                }

            }
        }

        return piece;

    }

    public void removePiece(Piece pieceRmv) {
        /**
         * pour elemener dans unn deplacement du l'adversaire donc on va jouer sur le
         * role
         */
        /*
         * if(this.turn==-1) { this.player2.remove(pieceRmv); } else {
         * this.player1.remove(pieceRmv); }
         */
        this.player1.remove(pieceRmv);
        this.player2.remove(pieceRmv);

    }

    public void updatePosition() {
        /***
         * pour faire la mise ajour de tout les pieces TODO pour afficher l'echquier et
         * met la mise a jour
         */
    }

    public void showEchiquier() {
        /**
         * pour afficher la console TODO met la console d'affichage
         */

        /**
         *
         * String tes=""; tes=tes+" "; System.out.println(tes.substring(0,8));
         */

        for (int j = 8; j >= 0; j--) {
            for (int i = 0; i < 7; i++) {
                String string=" ";
                if ((j >= 3 && j <= 5) && (i == 1 || i == 2 || i == 4 || i == 5)) {
                    string="W ";
                }

                if ((j == 8 && i == 4) || (j == 7 && i == 3) || (j == 8 && i == 2)) {

                    string="PN ";
                }

                if ((j == 0 && i == 2) || (j == 1 && i == 3) || (j == 0 && i == 4)) {

                    string="PB ";
                }

                if (j == 0 && i == 3) {

                    string="TB ";
                }

                if (j == 8 && i == 3) {

                    string="TN ";
                }

                Piece p = this.getPieceAt(new Point(j, i));
                if (p != null) {
                    //System.out.print(p.pieceIcon());
                    string=string+" "+p.pieceIcon();
                }

                string=string+"                          ";
                System.out.print(string.substring(0, 12)+" ||");


            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            System.out.println();
            // System.out.println("---------------------------------------------------------");
        }
        System.out.println("*******piece Restant*******player -1 : "+this.player1.size()+" ************************player 1:"+this.player2.size()+"********************************************************");
    }

    public Piece getPieceAt(Point pointTest) {
        /**
         * @param point pour facilter traitement de deplacement
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

    public List<Piece> getPieceByTurn() {
        if (this.turn == -1) {
            return this.player1;
        } else {
            return this.player2;
        }
    }

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

    public void randomTurn() {
        if (this.getTurn() == -1) {
            int pieceI = new Random().nextInt(this.player1.size());
            Piece randPiece = this.player1.get(pieceI);
            randPiece.randomMove();
        } else {
            int pieceI = new Random().nextInt(this.player2.size());
            Piece randPiece = this.player2.get(pieceI);
            randPiece.randomMove();
        }
    }

}