package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Coord2DTest {

    @Test
    void plusTwoCoordinatesShouldWorkRight() {
        Coord2D c1 = new Coord2D(1,2);
        Coord2D c2 = new Coord2D(15,-34);
        assertEquals(c1.plus(c2),new Coord2D(16,-32));
    }
}