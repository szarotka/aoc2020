package aoc.days;

import static java.lang.Math.abs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day12 {

  public long step2(String filePath) {
    List<String> values = readFile(filePath);
    PositionWayPoint position =
        PositionWayPoint.builder()
            .position(new Position(0, 0))
            .wayPoint(new Position(10, 1))
            .build();

    for (String line : values) {
      position = move2(position, line.substring(0, 1), Integer.parseInt(line.substring(1)));
    }

    return position.getDistance();
  }

  private PositionWayPoint move2(PositionWayPoint position, String direction, Integer value) {
    switch (direction) {
      case "N":
        return PositionWayPoint.builder()
            .position(position.position)
            .wayPoint(
                Position.builder().x(position.wayPoint.x).y(position.wayPoint.y + value).build())
            .build();
      case "S":
        return PositionWayPoint.builder()
            .position(position.position)
            .wayPoint(
                Position.builder().x(position.wayPoint.x).y(position.wayPoint.y - value).build())
            .build();
      case "E":
        return PositionWayPoint.builder()
            .position(position.position)
            .wayPoint(
                Position.builder().x(position.wayPoint.x + value).y(position.wayPoint.y).build())
            .build();
      case "W":
        return PositionWayPoint.builder()
            .position(position.position)
            .wayPoint(
                Position.builder().x(position.wayPoint.x - value).y(position.wayPoint.y).build())
            .build();
      case "L":
      case "R":
        return PositionWayPoint.builder()
            .position(position.position)
            .wayPoint(rotatePoint(direction, value, position.wayPoint))
            .build();
      case "F":
        return PositionWayPoint.builder()
            .position(
                Position.builder()
                    .x(position.position.x + value * position.wayPoint.x)
                    .y(position.position.y + value * position.wayPoint.y)
                    .build())
            .wayPoint(position.wayPoint)
            .build();
    }

    throw new RuntimeException("Instruction not found.");
  }

  private Position rotatePoint(String direction, int degrees, Position wayPoint) {
    int sign = direction.equals("R") ? 1 : -1;
    int newDegreesX = (360 + (wayPoint.x > 0 ? 90 : 270) + sign * degrees) % 360;
    int newDegreesY = (360 + (wayPoint.y > 0 ? 0 : 180) + sign * degrees) % 360;

    return Position.builder()
        .x(
            Set.of(90, 270).contains(newDegreesX)
                ? (newDegreesX > 90 ? -1 : 1) * abs(wayPoint.x)
                : (newDegreesY > 90 ? -1 : 1) * abs(wayPoint.y))
        .y(
            Set.of(90, 270).contains(newDegreesX)
                ? (newDegreesY > 90 ? -1 : 1) * abs(wayPoint.y)
                : (newDegreesX > 90 ? -1 : 1) * abs(wayPoint.x))
        .build();
  }

  public long step1(String filePath) {
    List<String> values = readFile(filePath);
    PositionDirection position = new PositionDirection(0, 0, "E");
    for (String line : values) {
      position = move(position, line.substring(0, 1), Integer.parseInt(line.substring(1)));
    }

    return position.getDistance();
  }

  private PositionDirection move(PositionDirection position, String direction, Integer value) {
    switch (direction) {
      case "N":
        return new PositionDirection(position.x, position.y + value, position.direction);
      case "S":
        return new PositionDirection(position.x, position.y - value, position.direction);
      case "E":
        return new PositionDirection(position.x + value, position.y, position.direction);
      case "W":
        return new PositionDirection(position.x - value, position.y, position.direction);
      case "L":
      case "R":
        return new PositionDirection(
            position.x, position.y, getDirection(position.direction, value, direction));
      case "F":
        return move(position, position.direction, value);
    }

    throw new RuntimeException("Instruction not found: " + direction);
  }

  private String getDirection(String previousDirection, Integer degrees, String direction) {
    int sign = direction.equals("R") ? 1 : -1;
    int newDegrees = (360 + getDegrees(previousDirection) + sign * degrees) % 360;
    switch (newDegrees) {
      case 0:
        return "N";
      case 180:
        return "S";
      case 90:
        return "E";
      case 270:
        return "W";
    }
    throw new RuntimeException("Instruction not found: " + newDegrees);
  }

  private Integer getDegrees(String direction) {
    switch (direction) {
      case "N":
        return 0;
      case "S":
        return 180;
      case "E":
        return 90;
      case "W":
        return 270;
    }
    throw new RuntimeException("Instruction not found: " + direction);
  }

  public List<String> readFile(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream.collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }
}

@ToString
@AllArgsConstructor
@Builder
class Position {
  int x;
  int y;

  long getDistance(){
    return abs(x) + abs(y);
  }
}

@ToString
@AllArgsConstructor
@Builder
class PositionDirection {
  int x;
  int y;
  String direction;

  long getDistance(){
    return abs(x) + abs(y);
  }
}

@ToString
@Builder
class PositionWayPoint {
  Position position;
  Position wayPoint;

  long getDistance(){
    return position.getDistance();
  }
}
