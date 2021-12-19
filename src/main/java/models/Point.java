package models;

public class Point {
    private Coord2D position;
    protected BoundBox bounds;

    public Point(double x, double y) {
        setPosition(new Coord2D(x, y));
    }

    public Coord2D getPosition() {
        return position;
    }

    public void setPosition(Coord2D newCoord) {
        position = newCoord;
    }

    public BoundBox getBounds() {
        bounds = new BoundBox(position, position);
        return bounds;
    }
}
