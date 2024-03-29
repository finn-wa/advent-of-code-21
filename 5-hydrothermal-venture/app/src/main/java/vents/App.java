package vents;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class App {

    private static final Pattern LINE_PATTERN = Pattern.compile("\\d+");

    public static void main(String[] args) {
        var app = new App();
        var path = app.getInputPath("input.txt");
        var lines = app.loadLines(path);
        app.printResults(1, new Field(lines.stream().filter(Predicate.not(Line::isDiagonal)).toList()));
        app.printResults(2, new Field(lines));
    }

    void printResults(int part, Field field) {
        if (field.numCols < 100) {
            System.out.println("Part %d\n%s\nHotspots: %d\n".formatted(part, field, field.numHotspots(2)));
        } else {
            System.out.println("Part %d\nHotspots: %d\n".formatted(part, field.numHotspots(2)));
        }

    }

    Path getInputPath(String file) {
        try {
            var inputUrl = this.getClass().getClassLoader().getResource(file);
            if (inputUrl == null) {
                throw new UncheckedIOException(new FileNotFoundException(file));
            }
            return Paths.get(new URI(inputUrl.toString()));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    List<Line> loadLines(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(this::parseLine).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    Line parseLine(String inputLine) {
        var coords = LINE_PATTERN.matcher(inputLine)
                .results()
                .map(MatchResult::group)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        return new Line(new Point(coords[0], coords[1]), new Point(coords[2], coords[3]));
    }

}
