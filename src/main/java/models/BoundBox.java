package models;

/**
 * Идеа предложила переделать класс в рекорд, ну я и повелся.
 * Почему бы и нет.
 * К тому же и equals и hashCode переопределяются в таком случае автоматически.
 * И toString тоже.
 * И конструктор писать не нужно.
 */
public record BoundBox(Coord2D low, Coord2D high) {

    /**
     * Метод объединения двух BoundBox-ов
     * Возвращает минимальный BoundBox,
     * вмещающий в себя и текущий и добавляемый.
     * Вообще хотелось бы переопределить оператор '+' или '|'.
     * Но джава пока таким возможностями, увы, не обладает.
     *
     * @param bounds Присоединяемый BoundBox
     * @return Новый BoundBox
     */
    public BoundBox unite(BoundBox bounds) {
        return new BoundBox(new Coord2D(Math.min(bounds.low.x(), low.x()), Math.min(bounds.low.y(), low.y())),
                new Coord2D(Math.max(bounds.high.x(), high.x()), Math.max(bounds.high.y(), high.y())));
    }

    /**
     * Метод сдвига ребенка относительно родителя.
     * Необходим для вычисления BoundBox в координатах другого ориджина.
     *
     * @param position Позиция родителя.
     * @return new BoundBox
     */
    public BoundBox shift(Coord2D position) {
        return new BoundBox(low.plus(position), high.plus(position));
    }
}
