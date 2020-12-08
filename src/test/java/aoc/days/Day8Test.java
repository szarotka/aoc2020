package aoc.days;

import org.junit.Test;

public class Day8Test {

  private static final String FILE_PATH = "src/test/resources/day8.txt";

  private final Day8 day8 = new Day8();

  @Test
  public void getAccumulatorValue() {
    long result = day8.getAccumulatorValue(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void changeInstructionAndGetAccumulatorValue() {
    long result = day8.changeInstructionAndGetAccumulatorValue(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
