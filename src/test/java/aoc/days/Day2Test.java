package aoc.days;

import org.junit.Test;

public class Day2Test {

  private static final String FILE_PATH = "src/test/resources/day2.txt";

  private final Day2 day2 = new Day2();

  @Test
  public void testIsPasswordValid1() {
    long result = day2.howManyPasswordAreValid1(FILE_PATH);
    System.out.println("Result " + result);
  }

  @Test
  public void testIsPasswordValid2() {
    long result = day2.howManyPasswordAreValid2(FILE_PATH);
    System.out.println("Result " + result);
  }
}
