package aoc.days;

import org.junit.Test;

public class Day12Test {

  private static final String FILE_PATH = "src/test/resources/day12.txt";

  private final Day12 day12 = new Day12();

  @Test
  public void step1() {
    long result = day12.step1(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void step2() {
    long result = day12.step2(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
