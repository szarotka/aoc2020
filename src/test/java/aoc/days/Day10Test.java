package aoc.days;

import org.junit.Test;

public class Day10Test {

  private static final String FILE_PATH = "src/test/resources/day10.txt";

  private final Day10 day10 = new Day10();

  @Test
  public void findFirstNotValidNumber() {
    long result = day10.multiplyOneJoltByThreeJolt(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void howManyCombinations() {
    //long result = day10.howManyCombinations(FILE_PATH);
    //System.out.println("Result: " + result);
  }
}
