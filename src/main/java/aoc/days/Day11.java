package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day11 {

  private static final Set<Point> POINTS =
      Set.of(
          new Point(-1, -1),
          new Point(-1, 0),
          new Point(-1, 1),
          new Point(0, -1),
          new Point(0, 1),
          new Point(1, -1),
          new Point(1, 0),
          new Point(1, 1));

  public long howManySeatsEndUpOccupiedStep2(String filePath) {
    List<List<Character>> values = readFile(filePath);

    boolean anyMove = true;
    while (anyMove) {
      anyMove = false;
      List<List<Character>> valuesAfterRound = copyList(values);
      for (int r = 0; r < values.size(); r++) {
        for (int c = 0; c < values.get(r).size(); c++) {
          char seat = values.get(r).get(c);
          if (seat == 'L' && howManySeatsEndUpOccupiedStep2(values, r, c) == 0) {
            valuesAfterRound.get(r).set(c, '#');
            anyMove = true;
          } else if (seat == '#' && howManySeatsEndUpOccupiedStep2(values, r, c) >= 5) {
            valuesAfterRound.get(r).set(c, 'L');
            anyMove = true;
          }
        }
      }
      values = copyList(valuesAfterRound);
    }

    return values.stream()
        .map(line -> line.stream().filter(ch -> ch == '#').count())
        .reduce(0L, Long::sum);
  }

  private long howManySeatsEndUpOccupiedStep2(List<List<Character>> values, int row, int column) {
    return POINTS.stream().filter(point -> isOccupied2(values, point, row, column)).count();
  }

  private boolean isOccupied2(List<List<Character>> values, Point point, int row, int column) {
    int index = 1;
    while (true) {
      if (point.x < 0 && row + point.x * index < 0) {
        return false;
      }
      if (point.y < 0 && column + point.y * index < 0) {
        return false;
      }
      if (point.x > 0 && row + point.x * index >= values.size()) {
        return false;
      }
      if (point.y > 0 && column + point.y * index >= values.get(row + point.x * index).size()) {
        return false;
      }

      char value = values.get(row + point.x * index).get(column + point.y * index);
      if (value == '#') {
        return true;
      } else if (value == 'L') {
        return false;
      } else {
        index++;
      }
    }
  }

  public long howManySeatsEndUpOccupied(String filePath) {
    List<List<Character>> values = readFile(filePath);

    boolean anyMove = true;
    while (anyMove) {
      anyMove = false;
      List<List<Character>> valuesAfterRound = copyList(values);
      for (int r = 0; r < values.size(); r++) {
        for (int c = 0; c < values.get(r).size(); c++) {
          char seat = values.get(r).get(c);
          if (seat == 'L' && howManyAdjacentIsOccupied(values, r, c) == 0) {
            valuesAfterRound.get(r).set(c, '#');
            anyMove = true;
          } else if (seat == '#' && howManyAdjacentIsOccupied(values, r, c) >= 4) {
            valuesAfterRound.get(r).set(c, 'L');
            anyMove = true;
          }
        }
      }
      values = copyList(valuesAfterRound);
    }

    return values.stream()
        .map(line -> line.stream().filter(ch -> ch == '#').count())
        .reduce(0L, Long::sum);
  }

  private List<List<Character>> copyList(List<List<Character>> values) {
    return values.stream().map(ArrayList::new).collect(Collectors.toList());
  }

  private long howManyAdjacentIsOccupied(List<List<Character>> values, int row, int column) {
    return POINTS.stream().filter(point -> isOccupied(values, point, row, column)).count();
  }

  private boolean isOccupied(List<List<Character>> values, Point point, int row, int column) {
    if (point.x < 0 && row <= 0) {
      return false;
    }
    if (point.y < 0 && column <= 0) {
      return false;
    }
    if (point.x > 0 && row + 1 >= values.size()) {
      return false;
    }
    if (point.y > 0 && column + 1 >= values.get(row).size()) {
      return false;
    }

    return values.get(row + point.x).get(column + point.y) == '#';
  }

  private List<List<Character>> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream
          .map(line -> line.chars().mapToObj(ch -> (char) ch).collect(Collectors.toList()))
          .collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}

@AllArgsConstructor
class Point {
  int x;
  int y;
}
