package game3.classes;
import game3.tools.MoveException;
import game3.tools.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Piece {

    // constantes qui définit les déplacements
    public static final int ADVANCE = 1;
    public static final int BACK = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;

    // ajout de classe de represnter une classe
    // Directions est un tableau qui contient les mouvements possibles d'une piece
    public static final int[] DIRECTIONS = { ADVANCE, BACK, RIGHT, LEFT };

    private Echiquier echiquier;

    /**
     * default power c'est la force de chaque animal qui peut etre zero par default
     */
    private int defaultPower;
    private int power;
    private int color;
    private Point position;
    private int specialMove;

    public Piece() {

    }

    public Piece(int power, int color, Point position, int specialMove, Echiquier ech) {
        this.power = power;
        this.color = color;
        this.position = position;
        this.specialMove = specialMove;
        this.defaultPower = power;
        this.echiquier = ech;
    }

    public Echiquier getEchiquier() {
        return echiquier;
    }

    public void setEchiquier(Echiquier echiquier) {
        this.echiquier = echiquier;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getDefaultPower() {
        return defaultPower;
    }

    public void setDefaultPower(int defaultPower) {
        this.defaultPower = defaultPower;
    }

    /*
     * on teste si il respecte les deplacement
     */
    public int getSpecialMove() {
        return this.specialMove;
    }

    /**
     *cela concerne les mouvements spéciales du rat , tigre et lion
     *
     */
    public void setSpecialMove(int specialMove) {
        this.specialMove = specialMove;
    }

    public String toString() {
        return "Piece : " + this.pieceIcon() + " [ defaultPower=" + defaultPower + ", power=" + power + ", color="
                + color + ", position=" + position + ", specialMove=" + specialMove + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + color;
        result = prime * result + defaultPower;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + specialMove;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Piece other = (Piece) obj;
        if (color != other.color)
            return false;
        if (defaultPower != other.defaultPower)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (specialMove != other.specialMove)
            return false;
        return true;
    }



     // permet de savoir la position de la piece

    public Point whereAmI() {
        return this.position;
    }

    public boolean amAlive() {
        /*
         * permet de vérifier si
         *la pièce est toujours en vie sur le plateau de jeu
         * a méthode vérifie si la liste de pièces "Player1" de l'instance "echiquier" contient la pièce.
         * Sinon, la méthode vérifie si la liste de pièces "Player2" de l'instance "echiquier" contient la pièce.
         * Si la pièce est trouvée dans l'une de ces listes, la méthode renvoie "true",
         * sinon elle renvoie "false".
         */
        if (this.echiquier.getTurn() == -1) {
            return this.echiquier.getPlayer1().contains(this);
        } else {
            return this.echiquier.getPlayer2().contains(this);
        }

    }

    public List<Point> getPossilesMoves() {
        /*
         * est une méthode de la classe "Piece" qui retourne une liste de tous les déplacements possibles pour la pièce en question sur le plateau de jeu.
         */

        List<Point> moves = new ArrayList<Point>();

        for (int it : DIRECTIONS) {

            try {
                moves.add(getPositionIfCanMove(it, 1));
            } catch (MoveException e) {

            }

        }

        /*
         * pour ne pas etre dans le trone d'un animal
         */
        if (this.echiquier.getTurn() == -1) {
            moves.remove(new Point(0, 3)); //Le trône de l'adversaire est situé en (0, 3) pour le joueur 1
        } else {
            moves.remove(new Point(8, 3));//Le trône de l'adversaire est situé en (8, 3) pour le joueur 2
            //Cela garantit également que la liste "moves" ne contient que les déplacements possibles de la pièce, en excluant les déplacements interdits sur le trône de l'adversaire.
        }

        return moves;


    }

    public void amInPiege() {
        /**
         * permet de vérifier si la pièce est dans un piège après son déplacement. Si elle est dans le piège,
         * la méthode met la puissance de la pièce à zéro.
         */

        if (echiquier.getTurn() == -1) {
            /**
             * on vérifie  si la postion apres le déplacemetn est dans la zone piege pour rendre
             * la puissance de la piéce zero par default
             */
            if (echiquier.getListPiegePlayer2().contains(this.position)) {
                this.power = 0;
            } else {
                this.power = this.defaultPower;
            }
        } else {
            if (echiquier.getListPiegePlayer1().contains(this.position)) {
                this.power = 0;
            } else {
                this.power = this.defaultPower;
            }

        }

    }

    public Point getPositionIfCanMove(int direction, int nbrCase) throws MoveException {
        /**
         *est une méthode qui renvoie la position d'une pièce
         *  si elle peut se déplacer dans une certaine direction d'un certain nombre de cases.
         */

        Point newPosition = null;

        if (direction == ADVANCE) {
            if (this.color == Color.NOIR) {
                newPosition = new Point(this.position.getX() - 1, this.position.getY());
            } else {
                newPosition = new Point(this.position.getX() + 1, this.position.getY());
            }
        } else if (direction == BACK) {
            if (this.color == Color.NOIR) {
                newPosition = new Point(this.position.getX() + 1, this.position.getY());
            } else {
                newPosition = new Point(this.position.getX() - 1, this.position.getY());
            }
        }

        else if (direction == LEFT) {
            if (this.color == Color.NOIR) {
                newPosition = new Point(this.position.getX(), this.position.getY() + 1);
            } else {
                newPosition = new Point(this.position.getX(), this.position.getY() - 1);
            }
        }

        else if (direction == RIGHT) {
            if (this.color == Color.NOIR) {
                newPosition = new Point(this.position.getX(), this.position.getY() - 1);
            } else {
                newPosition = new Point(this.position.getX(), this.position.getY() + 1);
            }
        }

        /**
         * Si la nouvelle position est sur l'eau, la méthode vérifie si la pièce peut effectuer
         * un mouvement spécial (si elle est un Rat ou un Lion ou tigre), dans le cas contraire elle déclenche une exception
         */

        if (echiquier.isPointWater(newPosition)) {
            /**
             * On doit vérifier que la pièce déplacée est la souris.
             * Seuls le lion et le tigre peuvent sauter la rivière.
             * Sinon, si la pièce n'est ni la souris, ni le lion, ni le tigre, une erreur sera déclenchée.
             */
            // Si c'est un Rat
            if (this.getSpecialMove() == 1) {
                // le rat peut traverser la riviere
                System.out.println("Je suis une " + this.pieceIcon() + ", je veux traverser l'eau à " + newPosition);
                if (this.getSpecialMove() == 2) {
                    // Si c'est le tigre ou le lion et que la souris est dans la rivière donc ils peuvent pas sauter
                    if (("T".equals(this.pieceIcon()) || "L".equals(this.pieceIcon())) && echiquier.isPointWater(this.position)) {
                        System.out.println("Le tigre ou le lion ne peut plus sauter la rivière");
                        throw new MoveException();
                    }
                    else {
                        //les coordonnées du saut de chaqu'un du tigre et le lion
                        int nbrX = newPosition.getX() - this.position.getX();
                        int nbrY = newPosition.getY() - this.position.getY();
                        System.out.println("From " + this.position + " to " + newPosition);
                        System.out.println("Nbr X: " + nbrX + ", Nbr Y: " + nbrY);

                        newPosition.setX(newPosition.getX() + nbrX * 3);
                        newPosition.setY(newPosition.getY() + nbrY * 2);
                        System.out.println("Je suis une " + this.pieceIcon() + ", je veux traverser l'eau à " + newPosition);
                    }

                }
            }
            else {
                System.out.println("Vous n'avez pas le droit de traverser la rivière");
                throw new MoveException();
            }
        }

        // Ce code vérifie si la nouvelle position est en dehors de
        // l'échiquier en appelant la méthode isPointInEchiquier de l'objet echiquier

        if (!echiquier.isPointInEchiquier(newPosition)) {
            /**
             * sinon iv declancher une exeption
             */
            System.out.println("position hors echiquier");
            throw new MoveException();
        }


        /**
         * vérifie si la case de destination est vide
         * en appelant la méthode isThereMyPiece
         * Si la case n'est pas vide, cela signifie qu'il y a déjà une pièce à cet endroit
         * et le code lance une exception MoveException.
         */
        if (!echiquier.isThereMyPiece(newPosition)) {
            throw new MoveException();
        }

        /*
         * param une postion
         */
        Piece pieceAt = echiquier.getPieceAt(newPosition);

        /**
         * la derniere conditon, un elephent ne peut pas manger un rat
         */
        if ((pieceAt != null && ((pieceAt.getColor() == this.color) || (pieceAt.getColor() != this.color
                && ( ((pieceAt.getPower() > this.getDefaultPower()))
                || (this.getDefaultPower() == 8 && pieceAt.getPower() == 1) )  )  )      ) ) {
            /**
             * declancher une erreur les piege sont traiter apres le deplacement
             */
            if((pieceAt.getPower() == 8 && this.getDefaultPower() == 1)) {
                System.out.println("piece at " + pieceAt.getPower() + "  my piece " + this.getDefaultPower());
            }
            else {
                System.out.println("votre piece est de puissance faible par rapport à l'autre piece ");
                throw new MoveException();
            }
        }

        return newPosition;
    }

    public Point getBestMove() {
        /**
         * parmi les possibles moves on va bassser sur un Dataset pour determiner Best
         * Move
         */
        return null;
    }

    public void movePiece(int direction, int nbrCase) throws MoveException {
        /**
         * apres le choix de piece on va le deplacer et synchro avec echiquier pour la
         * mise ajour
         */


        Point newPostion = getPositionIfCanMove(direction, nbrCase);

        Piece pi = echiquier.getPieceAt(newPostion);

        if (pi != null) {
            echiquier.removePiece(pi);
        }

        this.position = newPostion;

        /*
         * point on teste si le nouveau deplacement si oui set power to zero
         *              sinon default power;
         */
        System.out.println("Player" + echiquier.getTurn() + " Piece " + this.pieceIcon() + " moved From"
                + this.getPosition() + " to " + newPostion);

        this.amInPiege();

        echiquier.switchPlayer();
    }

    /**
     * pour respecter le mouvement  et special movement  pour chaque animaux && respecte la
     * place du sanctaire
     */
    public abstract void isPossibleMove();

    public void randomMove() {  //permet à une pièce de se déplacer de manière aléatoire parmi les cases où elle peut se déplacer.

        List<Point> possibleMoves = this.getPossilesMoves();
        if (this.getPossilesMoves().size() != 0) {
            Random rand = new Random();
            int randomPiece = rand.nextInt(possibleMoves.size());

            /*
             * point comme parametre
             */

            Point newPosition = possibleMoves.get(randomPiece);
            Piece pi = echiquier.getPieceAt(newPosition);

            if (pi != null) {
                echiquier.removePiece(pi);
            }

            System.out.println("Player" + echiquier.getTurn() + " Piece " + this.pieceIcon() + " moved From"
                    + this.getPosition() + " to " + newPosition);
            this.position = newPosition;

            this.amInPiege();

            /**
             * changer de joueur
             */
            // echiquier.isTroneGood();
            echiquier.switchPlayer();
        }
    }

    public String pieceIcon() {
        String namClass = this.getClass().getSimpleName().substring(0, 3);
        if (this.color == Color.BLANC) {
            return namClass + " B";
        } else {
            return namClass + " N";
        }
    }

}
