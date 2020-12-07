package aoc.days;

import org.junit.Test;

public class Day7Test {

  private static final String FILE_PATH = "src/test/resources/day7.txt";

  private final Day7 day7 = new Day7();

  @Test
  public void howManyBagsContainShinyGoldBag() {
    long result = day7.howManyBagsContainShinyGoldBag(FILE_PATH);
    System.out.println("Result: " + result);
  }

  @Test
  public void howManyBagsAreRequiredInShinyGoldBag() {
    long result = day7.howManyBagsAreRequiredInShinyGoldBag(FILE_PATH);
    System.out.println("Result: " + result);
  }
}
