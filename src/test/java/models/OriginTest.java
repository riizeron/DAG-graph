package models;

import exceptions.DAGConstraintException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OriginTest {
    static Space sp;
    static Set<Point> set = new HashSet<>();
    static Origin o1, o2, o3, o4, o5, o6, o7;
    static Point p1, p2, p3, p4, p5, p6;

    @BeforeAll
    static void SetUp() {
        sp = new Space();
        o1 = new Origin(1, 2);
        o2 = new Origin(2, 3);
        o3 = new Origin(3, 4);
        o4 = new Origin(5, 6);
        o5 = new Origin(1,10);
        p1 = new Point(1, 5);
        p2 = new Point(-1, -4);
        p3 = new Point(1.3, 6);
        p4 = new Point(-0.13, 7);
        p5 = new Point(0, 0);
        p6 = new Point(10, 10);
    }

    @Test
    void addMethodShouldThrowsDAGExceptionIfCyclesCanOccuse() {
        o1.add(o2);
        o2.add(o3);
        o3.add(o4);
        assertThrows(DAGConstraintException.class, () -> {
            o4.add(o2);
        });
    }

    @Test
    void getChildrenShouldGetACopyOfChildren() {
        set.add(o2);
        set.add(o3);
        set.add(o4);
        o1.setChildren(set);
        assertFalse(set== o1.getChildren());
    }

    @Test
    void setChildrenShouldSetACopyOfChildrenSet() {
        set.add(o2);
        set.add(o3);
        set.add(o4);
        o1.setChildren(set);
        set.clear();
        assertNotEquals(set.size(), o1.getChildren().size());
    }

    @Test
    void getBoundsShouldReturnNullIfThereNoPointsInThisGraph() {
        sp.add(o1);
        o1.add(o2);
        o2.add(o3);
        o2.add(o4);
        o1.add(o5);
        o2.add(o6);
        sp.add(o7);
        assertNull(sp.getBounds());
    }

    @Test
    void checkCycleAfterSetShouldReturnTrueIfThereAreCycles() {
        sp.add(o1);
        o2.add(o1);
        set.add(o5);
        set.add(o6);
        set.add(o2);
        o1.add(p3);
        o1.add(o4);
        o4.add(o3);
        assertTrue(o3.checkCycleAfterSet(set));
    }

    @Test
    void checkCycleAfterAddShouldReturnTrueIfCycleIsOccured() {
        sp.add(o1);
        o1.add(o2);
        o2.add(o3);
        o2.add(o5);

        assertTrue(o5.checkCycleAfterAdd(o1));

    }
}