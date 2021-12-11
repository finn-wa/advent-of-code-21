package vents;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class LineTest {
    @Test
    void deepEquals() {
        var line1 = Line.of(0, 1, 2, 3);
        var line2 = Line.of(0, 1, 2, 3);
        assertEquals(line1, line2);
    }

    @Test
    void linePointsVertical() {
        var line = Line.of(0, 0, 0, 2);
        var expectedPoints = List.of(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2));
        assertEquals(expectedPoints, line.points().toList());
    }

    @Test
    void linePointsVerticalUnordered() {
        var line = Line.of(0, 2, 0, 0);
        var expectedPoints = List.of(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2));
        assertEquals(expectedPoints, line.points().toList());
    }

    @Test
    void linePointsHorizontal() {
        var line = Line.of(0, 0, 2, 0);
        var expectedPoints = List.of(
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0));
        assertEquals(expectedPoints, line.points().toList());
    }

    @Test
    void linePointsHorizontalUnordered() {
        var line = Line.of(2, 0, 0, 0);
        var expectedPoints = List.of(
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0));
        assertEquals(expectedPoints, line.points().toList());
    }

    @Test
    void linePointsDiagonal() {
        var line = Line.of(0, 0, 2, 2);
        var expectedPoints = List.of(
                new Point(0, 0),
                new Point(1, 1),
                new Point(2, 2));
        assertEquals(expectedPoints, line.points().toList());
    }

    @Test
    void linePointsDiagonalUnordered() {
        var line = Line.of(3, 3, 1, 5);
        var expectedPoints = List.of(
                new Point(1, 5),
                new Point(2, 4),
                new Point(3, 3));
        assertEquals(expectedPoints, line.points().toList());
    }

}
