package com.game.classes;

public class ImpossibleDeplacementException extends Exception {
    private static final long serialVersionUID = 1L;

    public ImpossibleDeplacementException(String s) {
        super(s);
    }
    public ImpossibleDeplacementException() {};
}
