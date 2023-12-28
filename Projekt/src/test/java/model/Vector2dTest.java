package model;

import model.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    public void testEquals() {
        Vector2d v1 = new Vector2d(1, 3);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(1, 3);

        assertNotEquals(v1, v2);
        assertEquals(v1, v3);
        assertNotEquals(v2, v3);
        assertEquals(v3, v1);
    }

    @Test
    public void testAdd() {
        Vector2d vector = new Vector2d(1, 2);
        Vector2d adding = new Vector2d(2, 3);

        assertEquals(new Vector2d(3, 5), vector.add(adding));
        assertEquals(new Vector2d(5, 8), vector.add(adding.add(adding)));
        assertEquals(new Vector2d(4, 7), vector.add(adding.add(vector)));
        assertEquals(new Vector2d(3, 5), adding.add(vector));
        assertNotEquals(new Vector2d(3, 7), adding.add(vector));
    }

    @Test
    public void testSubstract() {
        Vector2d vector = new Vector2d(1, 2);
        Vector2d substracting = new Vector2d(2, 3);

        assertEquals(new Vector2d(-1, -1), vector.substract(substracting));
        assertEquals(new Vector2d(0, 1), vector.substract(substracting.substract(vector)));
        assertEquals(new Vector2d(1, 1), substracting.substract(vector));
        assertNotEquals(new Vector2d(-1, -1), substracting.substract(vector));
    }

    @Test
    public void testPrecedes() {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d v3 = new Vector2d(1, 2);

        assertTrue(v1.precedes(v2));
        assertFalse(v2.precedes(v1));
        assertTrue(v1.precedes(v3));
        assertTrue(v3.precedes(v1));
    }

    @Test
    public void testFollows() {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d v3 = new Vector2d(1, 2);

        assertFalse(v1.follows(v2));
        assertTrue(v2.follows(v1));
        assertTrue(v1.follows(v3));
        assertTrue(v3.follows(v1));
    }

    @Test
    public void testLowerLeft() {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d v3 = new Vector2d(1, 2);
        Vector2d v4 = new Vector2d(-1, 4);

        assertEquals(new Vector2d(1, 2), v1.lowerLeft(v2));
        assertEquals(new Vector2d(1, 2), v2.lowerLeft(v1));
        assertEquals(new Vector2d(1, 2), v1.lowerLeft(v3));
        assertEquals(new Vector2d(-1, 3), v4.lowerLeft(v2));
    }

    @Test
    public void testUpperRight() {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d v3 = new Vector2d(1, 2);
        Vector2d v4 = new Vector2d(-1, 4);

        assertEquals(new Vector2d(2, 3), v1.upperRight(v2));
        assertEquals(new Vector2d(2, 3), v2.upperRight(v1));
        assertEquals(new Vector2d(1, 2), v1.upperRight(v3));
        assertEquals(new Vector2d(2, 4), v4.upperRight(v2));
    }
}
