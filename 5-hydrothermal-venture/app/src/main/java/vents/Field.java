package vents;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Field
 * 
 * @author Finn Welsford-Ackroyd <br>
 *         Created On: Dec 09, 2021
 */
public class Field {

    public final int[][] tiles;
    public final int numRows;
    public final int numCols;

    public Field(List<Line> lines) {
        this.numRows = lines.stream()
                .map(line -> Math.max(line.start().row(), line.end().row()))
                .max(Integer::compare).orElseThrow()
                + 1;
        this.numCols = lines.stream()
                .map(line -> Math.max(line.start().col(), line.end().col()))
                .max(Integer::compare).orElseThrow()
                + 1;
        this.tiles = new int[this.numCols][this.numRows];
        lines.stream().forEach(line -> {
            line.points().forEach(point -> {
                this.tiles[point.col()][point.row()]++;
            });
        });
    }

    public Stream<int[]> cols() {
        return Arrays.stream(tiles);
    }

    public Stream<int[]> rows() {
        return range(0, this.numCols).boxed().map(
                col -> range(0, this.numRows)
                        .map(row -> this.tiles[col][row])
                        .toArray());
    }

    public long numHotspots(int threshold) {
        return cols()
                .flatMap(col -> Arrays.stream(col).boxed())
                .filter(i -> i >= threshold)
                .count();
    }

    @Override
    public String toString() {
        return rows().map(
                row -> Arrays.stream(row)
                        .boxed()
                        .map(i -> i == 0 ? "." : String.valueOf(i))
                        .collect(joining(" ")))
                .collect(joining("\n"));
    }

}
