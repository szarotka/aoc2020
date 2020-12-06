package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day6 {

  public long sumOfEveryoneYesAnswers(String filePath) {
    return readFileStep2(filePath).stream().map(Set::size).reduce(0, Integer::sum);
  }

  private List<Set<Character>> readFileStep2(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {

      AtomicReference<Map<Character, Integer>> groupAnswers =
          new AtomicReference<>(new HashMap<>());
      List<Set<Character>> allAnswers = new ArrayList<>();
      AtomicInteger counter = new AtomicInteger();
      stream.forEach(
          line -> {
            if (line.trim().isEmpty()) {
              allAnswers.add(
                  groupAnswers.get().keySet().stream()
                      .filter(ch -> groupAnswers.get().get(ch) == counter.get())
                      .collect(Collectors.toSet()));
              // reset
              counter.set(0);
              groupAnswers.set(new HashMap<>());
            } else {
              counter.incrementAndGet();
              line.chars()
                  .mapToObj(ch -> (char) ch)
                  .forEach(
                      value ->
                          groupAnswers
                              .get()
                              .put(
                                  value,
                                  groupAnswers.get().get(value) == null
                                      ? 1
                                      : groupAnswers.get().get(value) + 1));
            }
          });

      allAnswers.add(
          groupAnswers.get().keySet().stream()
              .filter(ch -> groupAnswers.get().get(ch) == counter.get())
              .collect(Collectors.toSet()));

      return allAnswers;
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }

  public long sumOfAnybodyYesAnswers(String filePath) {
    return readFile(filePath).stream().map(Set::size).reduce(0, Integer::sum);
  }

  private List<Set<Character>> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {

      AtomicReference<Set<Character>> groupAnswers = new AtomicReference<>(new HashSet<>());
      List<Set<Character>> allAnswers = new ArrayList<>();
      allAnswers.add(groupAnswers.get());
      stream.forEach(
          line -> {
            if (line.trim().isEmpty()) {
              groupAnswers.set(new HashSet<>());
              allAnswers.add(groupAnswers.get());
            } else {
              line.chars()
                  .mapToObj(ch -> (char) ch)
                  .forEach(value -> groupAnswers.get().add(value));
            }
          });
      return allAnswers;
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}
