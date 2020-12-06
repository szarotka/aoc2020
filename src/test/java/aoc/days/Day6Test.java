package aoc.days;

import org.junit.Test;

public class Day6Test {

  private static final String FILE_PATH = "src/test/resources/day6.txt";

  private final Day6 day6 = new Day6();

  @Test
  public void sumOfAnybodyYesAnswers() {
    long result = day6.sumOfAnybodyYesAnswers(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void sumOfEveryoneYesAnswers() {
    long result = day6.sumOfEveryoneYesAnswers(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
