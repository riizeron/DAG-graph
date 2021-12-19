package models;

import exceptions.DAGConstraintException;

import java.util.HashSet;
import java.util.Set;

public class Origin extends Point {
    private Set<Point> children;

    /**
     * Конструктор класса Origin.
     * Осуществляется за счет обращения к конструктору базового класса - Point,
     * в котором ициниализируется поле position, по переданным параметрам x и y.
     *
     * @param x Координата по оси абсцисс.
     * @param y Координата по оси ординат.
     */
    public Origin(double x, double y) {
        super(x, y);
        children = new HashSet<>();
    }

    /**
     * Метод добавления ребенка в сет детей текущего объекта.
     * Далее идет проверка на образование циклов.
     * В случае их обнаружения выбрасываем исключение DAGConstraintException.
     * Если объект усепешно добавился, то обозначаем флаг isChildrenSetChanged равным true.
     *
     * @param point Новый ребенок.
     */
    public void add(Point point) {
        if (checkCycleAfterAdd(point)) {
            throw new DAGConstraintException();
        }
        children.add(point);
    }

    /**
     * Возвращает сет копию сета детей текущего родителя.
     * Испоьзовать копию нееобходимо для сохранения инкапсуляции,
     * невозможности повлиять на объекты извне.
     * Да да, я знаю, что его не было в тз.
     * Ну написал и написал ну что уж, удобно это.
     * Мог бы удалить, но почему бы и не оставить.
     *
     * @return Копию сета детей.
     */
    public Set<Point> getChildren() {
        return new HashSet<>(children);
    }

    /**
     * Сеттер сета детей.
     * Присваивает копию передаваемого в метод массива.
     * Копия необходима для сохранения инкапсуляции,
     * невозможности повлиять на объекты извне.
     * Происходит вызова метода проверки на циклы сразу после добавления.
     * Если они образовались, выбрасывает исключение.
     * При успешном добавлении сета, устанавливает флаг isChildrenSetChanged равным True.
     *
     * @param newValue Новый сет детей.
     */
    public void setChildren(Set<Point> newValue) {
        if (checkCycleAfterSet(newValue)) {
            throw new DAGConstraintException();
        }
        children = new HashSet<>(newValue);
    }

    /**
     * Рекурсивный метод вычисления BoundBox.
     * Без инициализации поля bounds.
     * Ну или с инициализацией, но она ни для чего не нужна.
     * Ну я вот не знал надо оно или нет и до сих пор не знаю.
     * Ну раз оно прописано в тз, то не уберу же я его.
     * И решил, ну что уж полю пустовать, путь инициализируется.
     * Поэтому при переопределении children не придется обнулять bounds.
     * Тем не менее их можно инициализировать в методе, но благодаря вспомогательно переменной
     * tmpBounds, их значение при вычислении учитываться не будет
     * Однако я думаю над тем, чтобы поработать с флагами. Что же я имею пож этим в виду?
     * Пусть флаг изначально равен нулю.
     * Пусть дети не изменились и у нас снова попросили вычислить BoundBox.
     * В таком случае флаг оставляем в покое и в геттере возвращаем инициализированное поле.
     * Другая ситуация, когда произошло переопределение массива детей. Приравняем флаг единице.
     * Тогда вычисяления произойдут с самого начала.
     *
     * @return BoundsBox
     */
    @Override
    public BoundBox getBounds() {
        BoundBox tmpBounds = null;
        for (Point p : getChildren()) {
            if (tmpBounds == null) {
                // Если ребенок - точка, то нам не нужно сдвигать bounds относительно НЕЕ САМОЙ,
                // поскольку у точки бокс определен и нет детей, то есть
                // вычисление рекурсии в этом месте не уйдет глубже
                if (p != null && p.getClass() == Point.class) {
                    tmpBounds = p.getBounds();
                } else {
                    // Но если ребенок - ориджин, то рекурсия уйдет дальше и нам просто необходимо сдвинуть
                    // BoundBox по родителю, ведь дальше нам потребуется, при размотке стека рекурсии,
                    // вычислять BoundBox-ы ориджинов.
                    // Возвращаем значение бокса в ориджине p, а ДАЛЬШЕ, ПОСЛЕ того как вернулись
                    // в этот же момент, сдвигаем его относительно ориджина, чей бокс вычисляли.
                    if (p != null) {
                        BoundBox checkNull = p.getBounds();
                        if (checkNull != null) {
                            tmpBounds = checkNull.shift(p.getPosition());
                        }
                    }
                }
            } else {
                if (p.getClass() == Point.class) {
                    tmpBounds = tmpBounds.unite(p.getBounds());
                } else {
                    BoundBox checkNull = p.getBounds();
                    if (checkNull != null) {
                        tmpBounds = tmpBounds.unite(checkNull.shift(p.getPosition()));

                    }
                }
            }
        }
        bounds = tmpBounds;
        return tmpBounds;
    }

    /**
     * Обертка метода проверки на циклы при добавлении сета детей
     *
     * @param children Новый сет детей
     * @return True, если циклы не появятся.
     */
    protected boolean checkCycleAfterSet(Set<Point> children) {
        return checkChildArray(this, children);
    }

    /**
     * Обертка метода проверки на циклы при добавлении одного ребенка
     *
     * @param point Новый ребенок.
     * @return True, если цикы не появятся.
     */
    protected boolean checkCycleAfterAdd(Point point) {
        return checkChild(this, point);
    }

    /**
     * Рекурсивный метод проверки на циклы при добавлении одного ребенка
     *
     * @param point Новый ребенок
     * @param cons  Ориджин от которого вызвался метод обертки. Постоянный.
     *              Необходим для проверки возвращения к началу.
     * @return True - если циклы есть, False - если нет.
     */
    private boolean checkChild(Origin cons, Point point) {
        if (point == cons) {
            return true;
        }
        if (point != null) {
            if (point.getClass() != Point.class) {
                for (Point p : ((Origin) point).children) {
                    return checkChild(cons, p);
                }
            }
        }
        return false;
    }

    /**
     * Рекурсивный метод проверки на циклы при добавлении сета детей.
     *
     * @param children Новый сет детей
     * @param cons     Ориджин от которого вызвался метод обертки. Постоянный.
     *                 Необходим для проверки возвращения к началу.
     * @return True - если циклы есть, False - если нет.
     */
    private boolean checkChildArray(Origin cons, Set<Point> children) {
        for (Point p : children) {
            if (p != null) {
                if (p.getClass() != Point.class) {
                    if (p == cons) {
                        return true;
                    } else {
                        return checkChildArray(cons, ((Origin) p).children);
                    }
                }
            }

        }
        return false;
    }
}

