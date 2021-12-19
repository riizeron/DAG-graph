package models;

/**
 * Идеа предложила переделать класс в рекорд, ну я и повелся.
 * Почему бы и нет.
 * К тому же и equals и hashCode переопределяются в таком случае автоматически.
 * И toString тоже.
 * И конструктор писать не нужно.
 */
public record Coord2D(double x, double y) {

    /**
     * Метод сложения двух объектов Coord2D.
     * Опять же хотелось бы переопределить операнд '+', но увы.
     * Складывает покоординатно.
     *
     * @param coord Второе слагаемое
     * @return Новый объект с Coord2D, с координатами суммы.
     */
    public Coord2D plus(Coord2D coord) {
        return new Coord2D(x + coord.x, y + coord.y);
    }
}
