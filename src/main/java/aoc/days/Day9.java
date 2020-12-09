package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day9 {

  private static final int PREAMBLE_LENGTH = 25;

  public Optional<Long> sumOfNumbersWhichSumIsValueFromStep1(String filePath) {
    List<Long> values = readValues(filePath);
    return findFirstNotValidNumber(values)
        .flatMap(expectedSum -> sumOfNumbersWhichSumIsValueFromStep1(values, expectedSum));
  }

  private Optional<Long> sumOfNumbersWhichSumIsValueFromStep1(List<Long> values, long expectedSum) {
    for (int i = 0; i < values.size(); i++) {
      long sum = values.get(i);
      for (int j = i; j < values.size(); j++) {
        if (i != j) {
          sum += values.get(j);
          if (expectedSum == sum) {
            Long[] addends = Arrays.copyOfRange(values.toArray(Long[]::new), i, j);
            return calculateSumOfMinAndMax(addends);
          }
          if (expectedSum < sum) {
            break;
          }
        }
      }
    }
    return Optional.empty();
  }

  private Optional<Long> calculateSumOfMinAndMax(Long[] values) {
    long min = Arrays.stream(values).min(Long::compare).get();
    long max = Arrays.stream(values).max(Long::compare).get();
    return Optional.of(min + max);
  }

  public Optional<Long> findFirstNotValidNumber(String filePath) {
    List<Long> values = readValues(filePath);
    return findFirstNotValidNumber(values);
  }

  private Optional<Long> findFirstNotValidNumber(List<Long> values) {
    return IntStream.range(PREAMBLE_LENGTH, values.size())
        .filter(index -> !isValid(values, index))
        .mapToObj(values::get)
        .findFirst();
  }

  private boolean isValid(List<Long> values, int index) {
    for (int i = index - PREAMBLE_LENGTH; i < index + PREAMBLE_LENGTH - 3; i++) {
      for (int j = index - PREAMBLE_LENGTH + 1; j < index + PREAMBLE_LENGTH - 2; j++) {
        if (i != j && values.get(index) == values.get(i) + values.get(j)) {
          return true;
        }
      }
    }
    System.out.println("Not found: " + values.get(index));
    return false;
  }

  public List<Long> readValues(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream.map(Long::parseLong).collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}
