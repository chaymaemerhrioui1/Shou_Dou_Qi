package com.game.bo;


import java.io.Serializable;

public class Position implements Serializable {

    protected Long id;
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Position [id=" + id + ", x=" + x + ", y=" + y + "]";
    }

}

