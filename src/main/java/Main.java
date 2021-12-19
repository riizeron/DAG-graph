import models.Origin;
import models.Point;
import models.Space;

public class Main {
    public static void main(String[] args) {


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
        System.out.println(sp.getBounds());
//
//        Set<Point> ch = new HashSet<>();
//        Point p3 = new Point(3, 4);
//        Point p4 = new Point(4, 3);
//        ch.add(p3);
//        ch.add(p4);
//        o2.setChildren(ch);
//        System.out.println(o0.getBounds());
//
//        o0.setPosition(new Coord2D(5,1));
//        System.out.println(o0.getBounds());
//        Space o0 = new Space();
//        Origin o3 = new Origin(1,1);
//        Origin o1 = new Origin(9,1);
//        Origin o2 = new Origin(3,5);
//        o0.add(o3);
//        o3.add(o1);
//        o1.add(o2);
//        o2.add(o3);






    }
}
