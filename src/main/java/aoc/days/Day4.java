package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day4 {

  public long countValidPassports(String filePath) {
    readFile(filePath).forEach(p -> System.out.println("***" + p));
    return readFile(filePath).stream()
        .filter(
            passport ->
                passport
                    .keySet()
                    .containsAll(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")))
        .count();
  }

  public long countStrictValidPassports(String filePath) {
    return readFile(filePath).stream()
        .filter(
            passport ->
                passport
                    .keySet()
                    .containsAll(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")))
        .filter(passport -> validateBirthYear(passport.get("byr")))
        .filter(passport -> validateIssueYear(passport.get("iyr")))
        .filter(passport -> validateExpirationYear(passport.get("eyr")))
        .filter(passport -> validateHeight(passport.get("hgt")))
        .filter(passport -> validateHcl(passport.get("hcl")))
        .filter(passport -> validateEcl(passport.get("ecl")))
        .filter(passport -> validatePid(passport.get("pid")))
        .count();
  }

  private boolean validatePid(String value) {
    return Pattern.matches("[0-9]{9}", value);
  }

  private boolean validateEcl(String value) {
    return Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value);
  }

  private boolean validateHcl(String value) {
    return Pattern.matches("#[0-9a-f]{6}", value);
  }

  private boolean validateHeight(String height) {
    if (height.contains("cm")) {
      try {
        int value = Integer.parseInt(height.replace("cm", "").trim());
        return value >= 150 && value <= 193;
      } catch (Exception e) {
        return false;
      }
    }

    if (height.contains("in")) {
      try {
        int value = Integer.parseInt(height.replace("in", "").trim());
        return value >= 59 && value <= 76;
      } catch (Exception e) {
        return false;
      }
    }
    return false;
  }

  private boolean validateExpirationYear(String expirationYear) {
    try {
      int iyr = Integer.parseInt(expirationYear);
      return iyr >= 2020 && iyr <= 2030;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean validateIssueYear(String issueYear) {
    try {
      int iyr = Integer.parseInt(issueYear);
      return iyr >= 2010 && iyr <= 2020;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean validateBirthYear(String birthYear) {
    try {
      int byr = Integer.parseInt(birthYear);
      return byr >= 1920 && byr <= 2002;
    } catch (Exception e) {
      return false;
    }
  }

  private List<Map<String, String>> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {

      AtomicReference<ConcurrentHashMap<String, String>> passport =
          new AtomicReference<>(new ConcurrentHashMap<>());
      List<Map<String, String>> passports = new ArrayList<>();
      passports.add(passport.get());
      stream.forEach(
          line -> {
            if (line.trim().isEmpty()) {
              passport.set(new ConcurrentHashMap<>());
              passports.add(passport.get());
            } else {
              Arrays.stream(line.split(" "))
                  .forEach(value -> passport.get().put(value.split(":")[0], value.split(":")[1]));
            }
          });
      return passports;
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}
