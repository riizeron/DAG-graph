package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void getPositionShouldReturnCorrectPosition() {
        Origin o1 = new Origin(1,3);
        Coord2D c = new Coord2D(1,3);
        assertEquals(o1.getPosition(),c);
    }

    @Test
    void setPositionShouldSetpositionCorrectly() {
        Origin o1 = new Origin(1,3);
        Coord2D d = new Coord2D(5,-12);
        o1.setPosition(d);
        assertEquals(o1.getPosition(),d);
    }

    @Test
    void getBoundsOnPointShouldReturnBoundBoxBasedOnPointCoordinates() {
        Point o1 = new Point(1,3);
        Coord2D dd = o1.getPosition();
        BoundBox bound = new BoundBox(dd,dd);
        assertEquals(bound, o1.getBounds());
    }
}