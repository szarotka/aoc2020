package aoc.days;

import org.junit.Test;

public class Day9Test {

  private static final String FILE_PATH = "src/test/resources/day9.txt";

  private final Day9 day9 = new Day9();

  @Test
  public void findFirstNotValidNumber() {
    Long result = day9.findFirstNotValidNumber(FILE_PATH).get();
    System.out.println("Result: " + result);
  }

  @Test
  public void sumOfNumbersWhichSumIsValueFromStep1() {
    Long result = day9.sumOfNumbersWhichSumIsValueFromStep1(FILE_PATH).get();
    System.out.println("Result: " + result);
  }
}
