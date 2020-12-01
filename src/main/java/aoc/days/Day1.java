package aoc.days;

import aoc.FileReader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day1 {

  private FileReader fileReader = new FileReader();

  public int find3Numbers(String filePath) {
    List<Integer> numbers = fileReader.readFile(filePath);
    for (int i = 0; i < numbers.size(); i++) {
      for (int j = 1; j < numbers.size(); j++) {
        for (int k = 2; k < numbers.size(); k++) {
          if (i == j || j == k || i == k) {
            break;
          }
          if (numbers.get(i) + numbers.get(j) + numbers.get(k) == 2020) {
            log.info(
                String.format(
                    "%d x %d x %d = %d",
                    numbers.get(i),
                    numbers.get(j),
                    numbers.get(k),
                    numbers.get(i) * numbers.get(j) * numbers.get(k)));
            return numbers.get(i) * numbers.get(j) * numbers.get(k);
          }
        }
      }
    }
    throw new RuntimeException("Number not found");
  }

  public int findNumbers(String filePath) {
    List<Integer> numbers = fileReader.readFile(filePath);
    for (int i = 0; i < numbers.size(); i++) {
      for (int j = 1; j < numbers.size(); j++) {
        if (i == j) {
          break;
        }
        if (numbers.get(i) + numbers.get(j) == 2020) {
          log.info(
              String.format(
                  "%d x %d = %d", numbers.get(i), numbers.get(j), numbers.get(i) * numbers.get(j)));
          return numbers.get(i) * numbers.get(j);
        }
      }
    }
    throw new RuntimeException("Number not found");
  }
}
