package aoc.days;

import aoc.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day5 {

  private FileReader fileReader = new FileReader();

  public int findMissingSeat(String filePath) {
    List<Integer> seats =
        fileReader.readStringValues(filePath).stream()
            .map(this::findId)
            .collect(Collectors.toList());

    return IntStream.range(1, calculateId(127, 7))
        .filter(
            seat -> !seats.contains(seat) && seats.contains(seat - 1) && seats.contains(seat + 1))
        .findFirst()
        .getAsInt();
  }

  public Optional<Integer> findMaxId(String filePath) {
    return fileReader.readStringValues(filePath).stream().map(this::findId).max(Integer::compareTo);
  }

  private int findId(String pass) {
    int row = calculateRow(pass.substring(0, 7));
    int column = calculateColumn(pass.substring(7));
    return calculateId(row, column);
  }

  private int calculateId(int row, int column) {
    return row * 8 + column;
  }

  private int calculateColumn(String columnChars) {
    AtomicInteger colMin = new AtomicInteger();
    AtomicInteger colMax = new AtomicInteger(7);

    columnChars
        .chars()
        .forEach(
            ch -> {
              if (ch == 'L') {
                colMax.updateAndGet(
                    max -> max - (int) Math.ceil((double) (max - colMin.get()) / 2));
              } else {
                colMin.updateAndGet(
                    min -> (int) (min + Math.ceil((double) (colMax.get() - min) / 2)));
              }
            });

    return colMin.get();
  }

  private int calculateRow(String rowChars) {
    AtomicInteger rowMin = new AtomicInteger();
    AtomicInteger rowMax = new AtomicInteger(127);

    rowChars
        .chars()
        .forEach(
            ch -> {
              if (ch == 'F') {
                rowMax.updateAndGet(
                    max -> max - (int) Math.ceil((double) (max - rowMin.get()) / 2));
              } else {
                rowMin.updateAndGet(
                    min -> (int) (min + Math.ceil((double) (rowMax.get() - min) / 2)));
              }
            });

    return rowMin.get();
  }
}
