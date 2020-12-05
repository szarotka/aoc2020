package aoc.days;

import org.junit.Test;

public class Day5Test {

  private static final String FILE_PATH = "src/test/resources/day5.txt";

  private final Day5 day5 = new Day5();

  @Test
  public void findMaxId() {
    int result = day5.findMaxId(FILE_PATH).get();
    System.out.println("Result: " + result);
  }

  @Test
  public void findMissing() {
    int result = day5.findMissingSeat(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
