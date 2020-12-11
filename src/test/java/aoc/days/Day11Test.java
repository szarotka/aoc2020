package aoc.days;

import org.junit.Test;

public class Day11Test {

  private static final String FILE_PATH = "src/test/resources/day11.txt";

  private final Day11 day11 = new Day11();

  @Test
  public void howManySeatsEndUpOccupied() {
    long result = day11.howManySeatsEndUpOccupied(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void howManySeatsEndUpOccupiedStep2() {
    long result = day11.howManySeatsEndUpOccupiedStep2(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
