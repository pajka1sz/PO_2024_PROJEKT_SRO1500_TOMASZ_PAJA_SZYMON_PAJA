package model.util;

import model.Vector2d;

public interface MoveValidator {
    /**
     * Function checks whether it is possible to move to specified position.
     *
     * @param position defines the position to be checked.
     * @return true if it is possible to move to specified position and false otherwise.
     */
    boolean canMoveTo(Vector2d position);
}
