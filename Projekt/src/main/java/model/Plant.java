package model;

public class Plant implements WorldElement {
    private final Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public String toString() {
        return "*";
    }
}
