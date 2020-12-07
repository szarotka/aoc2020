package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day7 {

  public long howManyBagsContainShinyGoldBag(String filePath) {
    Map<String, Set<Bag>> bags = readFile(filePath);

    return findAllBagsWhichContainBag(bags, "shiny gold").size();
  }

  public long howManyBagsAreRequiredInShinyGoldBag(String filePath) {
    Map<String, Set<Bag>> bags = readFile(filePath);

    return howManyBagsAreRequiredInBagWithGivenColor(bags, "shiny gold");
  }

  private long howManyBagsAreRequiredInBagWithGivenColor(Map<String, Set<Bag>> bags, String color) {
    return bags.get(color).stream()
        .map(item -> howManyBagsAreRequiredInBag(bags, item))
        .reduce(Long::sum)
        .get();
  }

  private long howManyBagsAreRequiredInBag(Map<String, Set<Bag>> bags, Bag bag) {
    if (isLastLevel(bags, bag)) {
      return bag.amount;
    }
    return bag.amount + bag.amount * howManyBagsAreRequiredInBagWithGivenColor(bags, bag.color);
  }

  private boolean isLastLevel(Map<String, Set<Bag>> bags, Bag bag) {
    return bags.get(bag.color) == null;
  }

  private Set<String> findAllBagsWhichContainBag(Map<String, Set<Bag>> bags, String color) {
    Set<String> containsColor =
        bags.keySet().stream()
            .filter(
                key ->
                    bags.get(key).stream()
                        .map(bag -> bag.color)
                        .collect(Collectors.toSet())
                        .contains(color))
            .collect(Collectors.toSet());

    Set<String> result = new HashSet<>(containsColor);

    containsColor.forEach(col -> result.addAll(findAllBagsWhichContainBag(bags, col)));

    return result;
  }

  private Map<String, Set<Bag>> readFile(String filePath) {
    Map<String, Set<Bag>> bags = new HashMap<>();
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      stream.forEach(
          line -> {
            String bag = line.split(" bags contain")[0];
            String containsBags = line.split("bags contain ")[1];

            if (!"no other bags.".equals(containsBags)) {
              Set<Bag> bagsInBag =
                  Arrays.stream(containsBags.split(", "))
                      .map(
                          item ->
                              new Bag(
                                  Integer.parseInt(item.split(" ")[0]),
                                  item.replace(item.split(" ")[0], "").trim().split(" bag")[0]))
                      .collect(Collectors.toSet());
              bags.put(bag, bagsInBag);
            }
          });
      return bags;
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new HashMap<>();
    }
  }

  @AllArgsConstructor
  @ToString
  class Bag {
    int amount;
    String color;
  }
}
