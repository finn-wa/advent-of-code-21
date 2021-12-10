package vents;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Line(Point start, Point end) {

    public static Line of(int startX, int startY, int endX, int endY) {
        return new Line(new Point(startX, startY), new Point(endX, endY));
    }

    public static Line ordered(Point p1, Point p2) {
        Point min = new Point(Math.min(p1.row(), p2.row()), Math.min(p1.col(), p2.col()));
        Point max = new Point(Math.max(p1.row(), p2.row()), Math.max(p1.col(), p2.col()));
        return new Line(min, max);
    }

    public boolean isNotDiagonal() {
        return start.row() == end.row() || start.col() == end.col();
    }

    public Stream<Point> points() {
        Line orderedLine = ordered(start, end);
        if (!this.equals(orderedLine)) {
            return orderedLine.points();
        }
        if (start.row() == end.row()) {
            var x = start.row();
            return IntStream.rangeClosed(start.col(), end.col())
                    .mapToObj(y -> new Point(x, y));
        }
        if (start.col() == end.col()) {
            var y = start.col();
            return IntStream.rangeClosed(start.row(), end.row())
                    .mapToObj(x -> new Point(x, y));
        }
        throw new IllegalStateException("diagonal shit " + this);
    }
}