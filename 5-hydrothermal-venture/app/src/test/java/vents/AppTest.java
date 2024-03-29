package vents;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {
    App app;

    @BeforeEach
    void setup() {
        app = new App();
    }

    @Test
    void appLoadsInput() throws Exception {
        var path = app.getInputPath("test.txt");
        var lines = app.loadLines(path);
        assertEquals(List.of(
                Line.of(0, 9, 5, 9),
                Line.of(8, 0, 0, 8),
                Line.of(9, 4, 3, 4),
                Line.of(2, 2, 2, 1),
                Line.of(7, 0, 7, 4),
                Line.of(6, 4, 2, 0),
                Line.of(0, 9, 2, 9),
                Line.of(3, 4, 1, 4),
                Line.of(0, 0, 8, 8),
                Line.of(5, 5, 8, 2)), lines);
    }

    @Test
    void appParsesLine() {
        assertEquals(
                app.parseLine("201,0 -> 12, 10"),
                Line.of(201, 0, 12, 10));
    }

}
