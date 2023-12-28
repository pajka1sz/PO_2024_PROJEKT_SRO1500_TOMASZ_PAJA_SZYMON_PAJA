package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    public void testRotate() {
        MapDirection mp1 = MapDirection.EAST;
        MapDirection mp2 = MapDirection.SOUTH_EAST;
        MapDirection mp3 = MapDirection.NORTH;

        assertEquals(MapDirection.SOUTH, mp1.rotate(2));
        assertEquals(MapDirection.NORTH_WEST, mp2.rotate(4));
        assertEquals(MapDirection.NORTH_WEST, mp3.rotate(7));
        assertNotEquals(MapDirection.NORTH_EAST, mp3.rotate(0));
    }

    @Test
    public void testToUnitVector() {
        MapDirection mp1 = MapDirection.EAST;
        MapDirection mp2 = MapDirection.SOUTH_EAST;
        MapDirection mp3 = MapDirection.NORTH;

        assertEquals(new Vector2d(1, 0), mp1.toUnitVector());
        assertEquals(new Vector2d(1, -1), mp2.toUnitVector());
        assertEquals(new Vector2d(0, 1), mp3.toUnitVector());
        assertNotEquals(new Vector2d(1, 0), mp3.toUnitVector());
    }
}
