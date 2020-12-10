package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day10 {

  // TODO replace with algorithm with better time complexity
  public long howManyCombinations(String filePath) {
    List<Integer> values = readFile(filePath).stream().sorted().collect(Collectors.toList());

    int previousValue = 0;
    int lastValue = values.get(values.size() - 1) + 3;
    values.add(lastValue);

    long counter = findNext(values, previousValue, 0);

    return counter + 1;
  }

  private long findNext(List<Integer> values, int previousValue, int index) {
    long counter = 0;
    for (int i = index; i < values.size(); i++) {
      if (values.get(i) - previousValue > 3) {
        return counter + i - 1 - index;
      }
      counter += findNext(values, values.get(i), i + 1);
    }
    return counter;
  }

  public long multiplyOneJoltByThreeJolt(String filePath) {
    List<Integer> values = readFile(filePath).stream().sorted().collect(Collectors.toList());

    Map<Integer, Long> sumOfDiff = new HashMap<>();
    sumOfDiff.put(3, 1L);
    int previousValue = 0;
    for (Integer value : values) {
      int diff = value - previousValue;
      if (diff > 3) {
        return diff;
      }
      previousValue = value;
      sumOfDiff.put(diff, sumOfDiff.get(diff) == null ? 1 : sumOfDiff.get(diff) + 1);
    }

    return sumOfDiff.keySet().stream().map(sumOfDiff::get).reduce(1L, (a, b) -> a * b);
  }

  public List<Integer> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream.map(Integer::parseInt).collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}
