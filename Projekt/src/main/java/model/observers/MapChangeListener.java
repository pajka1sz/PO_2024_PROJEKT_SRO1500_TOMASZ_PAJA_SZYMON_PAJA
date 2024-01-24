package model.observers;

import model.map.WorldMap;

@FunctionalInterface
public interface MapChangeListener {
    /**
     * Function which informs observer about the change on the map.
     * @param worldMap defines changed map.
     * @param message defines message to be given to observer.
     */
    void mapChanged(WorldMap worldMap, String message);
}