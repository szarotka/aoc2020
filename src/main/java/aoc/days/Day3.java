package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day3 {

  public long multipleResults(String filePath) {
    return countTrees(filePath, 1, 1)
        * countTrees(filePath, 3, 1)
        * countTrees(filePath, 5, 1)
        * countTrees(filePath, 7, 1)
        * countTrees(filePath, 1, 2);
  }

  public long countTrees(String filePath, int x, int y) {
    List<List<Boolean>> board = readFile(filePath);

    long counterOfTrees = 0;
    int index = 0;
    Position position = new Position(0, 0);
    while (index < board.size() - 1) {
      index = index + y;
      position.x = position.x + x;
      position.y = position.y + y;

      if (position.x >= board.get(0).size()) {
        position.x = position.x - board.get(0).size();
      }

      boolean isTree = board.get(position.y).get(position.x);
      if (isTree) {
        counterOfTrees++;
      }
    }
    System.out.println("counterOfTrees: " + counterOfTrees);
    return counterOfTrees;
  }

  private List<List<Boolean>> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream
          .map(
              value ->
                  value
                      .chars()
                      .mapToObj(ch -> (char) ch == '#')
                      .collect(Collectors.toList()))
          .collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }

  @AllArgsConstructor
  class Position {
    int x;
    int y;
  }
}
