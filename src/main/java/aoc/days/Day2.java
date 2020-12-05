package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day2 {

  public long howManyPasswordAreValid1(String filePath) {
    List<Password> passwords = readFile(filePath);
    return passwords.stream().filter(this::isValid).count();
  }

  private boolean isValid(Password password) {
    long numberOfChars = numberOfChars(password);
    return numberOfChars >= password.passwordPolicy.min
        && numberOfChars <= password.passwordPolicy.max;
  }

  public long howManyPasswordAreValid2(String filePath) {
    List<Password> passwords = readFile(filePath);
    return passwords.stream().filter(this::isValid2).count();
  }

  private boolean isValid2(Password password) {
    char[] chars = password.password.toCharArray();
    boolean firstPosition = chars[password.passwordPolicy.min - 1] == password.passwordPolicy.sign;
    boolean secondPosition = chars[password.passwordPolicy.max - 1] == password.passwordPolicy.sign;

    return firstPosition ^ secondPosition;
  }

  private long numberOfChars(Password password) {
    return password.password.chars().filter(ch -> ch == password.passwordPolicy.sign).count();
  }

  private List<Password> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream
          .map(
              value ->
                  Password.builder()
                      .password(value.split(" ")[2])
                      .passwordPolicy(
                          PasswordPolicy.builder()
                              .min(Integer.parseInt(value.split("-")[0]))
                              .max(Integer.parseInt(value.split("-")[1].split(" ")[0]))
                              .sign(value.split(" ")[1].split(":")[0].toCharArray()[0])
                              .build())
                      .build())
          .collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}

@AllArgsConstructor
@Builder
class Password {
  PasswordPolicy passwordPolicy;
  String password;
}

@AllArgsConstructor
@Builder
class PasswordPolicy {
  int min;
  int max;
  char sign;
}
