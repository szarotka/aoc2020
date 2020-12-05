package aoc.days;

import org.junit.Test;

public class Day4Test {

  private static final String FILE_PATH = "src/test/resources/day4.txt";

  private final Day4 day4 = new Day4();

  @Test
  public void countValidPassports() {
    long result = day4.countValidPassports(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void countStrictValidPassports() {
    long result = day4.countStrictValidPassports(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
