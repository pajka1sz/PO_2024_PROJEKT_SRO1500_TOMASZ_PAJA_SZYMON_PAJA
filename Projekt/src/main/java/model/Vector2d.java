package model;

import java.util.Objects;

public class Vector2d {
    private int x;
    private int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return this.x;}

    public int getY() {return this.y;}

    public Vector2d add(Vector2d vector) {
        return new Vector2d(this.x + vector.getX(), this.y + vector.getY());
    }

    public Vector2d substract(Vector2d vector) {
        return new Vector2d(this.x - vector.getX(), this.y - vector.getY());
    }

    public boolean precedes(Vector2d vector) {
        return this.x <= vector.getX() && this.y <= vector.getY();
    }

    public boolean follows(Vector2d vector) {
        return this.x >= vector.getX() && this.y >= vector.getY();
    }

    public Vector2d lowerLeft(Vector2d vector) {
        return new Vector2d(Math.min(this.x, vector.getX()), Math.min(this.y, vector.getY()));
    }

    public Vector2d upperRight(Vector2d vector) {
        return new Vector2d(Math.max(this.x, vector.getX()), Math.max(this.y, vector.getY()));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vector2d))
            return false;
        return this.x == ((Vector2d) o).getX() && this.y == ((Vector2d) o).getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
