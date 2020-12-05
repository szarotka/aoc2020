package aoc.days;

import org.junit.Test;

public class Day3Test {

  private static final String FILE_PATH = "src/test/resources/day3.txt";

  private final Day3 day3 = new Day3();

  @Test
  public void countTrees() {
    long count = day3.countTrees(FILE_PATH, 3, 1);
    System.out.println("Result: " + count);
  }

  @Test
  public void countTrees2() {
    long count = day3.multipleResults(FILE_PATH);
    System.out.println("Result: " + count);
  }
}
