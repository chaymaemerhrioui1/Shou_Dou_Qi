package com.game.classes;

import com.game.tools.Point;

public class Chat extends Piece {

    public Chat() {}
    //constructeur initialisant
    public Chat(int power, int color, Point position, int specialMove,Echiquier ech) {
        super(power, color, position, specialMove,ech);
    }
    @Override
    public void isPossibleMove() {
        // TODO Auto-generated method stub
    }
}
