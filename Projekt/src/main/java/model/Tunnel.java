package model;

public class Tunnel implements WorldElement {
    private final Vector2d opening;

    public Tunnel(Vector2d position) {
        this.opening = position;
    }

    @Override
    public Vector2d getPosition() {
        return opening;
    }

    public String toString() {
        return "O";
    }
}
