package aoc.days;

import org.junit.Test;

public class Day1Test {

  private static final String FILE_PATH = "src/test/resources/day1.txt";

  private final Day1 day1 = new Day1();

  @Test
  public void testFindNumbers() {
    day1.findNumbers(FILE_PATH);
  }

  @Test
  public void testFind3Numbers() {
    day1.find3Numbers(FILE_PATH);
  }
}
