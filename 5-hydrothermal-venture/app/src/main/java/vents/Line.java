package vents;

import static java.util.stream.IntStream.*;

import java.util.stream.Stream;

public record Line(Point start, Point end) {

    public static Line of(int startX, int startY, int endX, int endY) {
        return new Line(new Point(startX, startY), new Point(endX, endY));
    }

    public boolean isDiagonal() {
        return !(start.row() == end.row() || start.col() == end.col());
    }

    public Stream<Point> points() {
        return ordered().pointsImpl();
    }

    private Line ordered() {
        var p1IsLeft = (start.row() == end.row() && start.col() < end.col()) || start.row() < end.row();
        if (p1IsLeft) {
            return this;
        }
        return new Line(end, start);
    }

    private Stream<Point> pointsImpl() {
        int deltaRow = end.row() - start.row();
        int deltaCol = end.col() - start.col();
        var steps = Math.max(deltaRow, deltaCol);
        if (steps == 0) {
            return Stream.of(start);
        }
        return rangeClosed(0, steps)
                .boxed()
                .map(i -> new Point(
                        i * (deltaRow / steps) + start.row(),
                        i * (deltaCol / steps) + start.col()));
    }
}