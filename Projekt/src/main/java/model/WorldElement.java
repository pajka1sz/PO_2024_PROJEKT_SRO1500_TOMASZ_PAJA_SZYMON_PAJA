package model;

public interface WorldElement {
    /**
     * Method returning position of a world element.
     *
     * @return position of an element.
     */
    Vector2d getPosition();

    /**
     * Returns String representation of a world element.
     *
     * @return String representation of an element.
     */
    String toString();
}
