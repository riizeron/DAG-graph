package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundBoxTest {

    @Test
    void uniteShouldReturnCorrectBoundBox() {
        Origin sp = new Space();
        Origin o1 = new Origin(5, 1);
        sp.add(o1);
        Origin o2 = new Origin(3, 4);
        o1.add(o2);
        Origin o3 = new Origin(2, 2);
        sp.add(o3);
        Point p5 = new Point(1, 1);
        o3.add(p5);
        Point p1 = new Point(0, 0);
        o2.add(p1);
        Point p2 = new Point(2, 2);
        o2.add(p2);
        Point p6 = new Point(4,-5);
        sp.add(p6);
        BoundBox bound = new BoundBox(new Coord2D(3,-5),new Coord2D(10,7));
        assertEquals(sp.getBounds(), bound);
    }

    @Test
    void shiftShouldMovePositionOfBoundBoxReferToParent() {
        Origin sp = new Space();
        Origin o1 = new Origin(5, 1);
        sp.add(o1);
        Origin o2 = new Origin(3, 4);
        o1.add(o2);
        Origin o3 = new Origin(2, 2);
        o1.add(o3);
        Point p5 = new Point(1, 1);
        o3.add(p5);
        Point p1 = new Point(0, 0);
        o2.add(p1);
        Point p2 = new Point(2, 2);
        o2.add(p2);
        Point p6 = new Point(4,-5);
        o1.add(p6);
        BoundBox bound = new BoundBox(new Coord2D(3,-5),new Coord2D(10,7));
        assertEquals(sp.getBounds(),o1.getBounds().shift(o1.getPosition()));
    }
}