package aoc.days;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day8 {

  public long getAccumulatorValue(String filePath) {
    List<String> values = readValues(filePath);

    Set<Integer> visited = new HashSet<>();
    long accumulator = 0;
    int index = 0;

    while (!visited.contains(index)) {
      visited.add(index);
      Instruction instruction = getInstruction(values, index);
      int value = getValue(values, index);
      switch (instruction){
        case NOP -> index++;
        case ACC -> {accumulator += value; index++;}
        case JMP -> index += value;
      }
    }

    return accumulator;
  }

  private Instruction getInstruction(List<String> values, int index){
    return Instruction.valueOf(values.get(index).substring(0, 3).toUpperCase());
  }

  private int getValue(List<String> values, int index){
    return Integer.parseInt(values.get(index).substring(3).trim());
  }

  public long changeInstructionAndGetAccumulatorValue(String filePath) {
    List<String> values = readValues(filePath);
    Set<Integer> changedIndex = new HashSet<>();

    while (true) {
      Set<Integer> visited = new HashSet<>();
      long accumulator = 0;
      int index = 0;
      boolean indexChanged = false;
      while (index < values.size() - 1 && !visited.contains(index)) {
        Instruction instruction = getInstruction(values, index);
        int value = getValue(values, index);
        visited.add(index);

        switch (instruction){
          case NOP -> {
            if (!indexChanged && !changedIndex.contains(index)) {
              indexChanged = true;
              changedIndex.add(index);
              index += value;
            } else {
              index++;
            }
          }
          case ACC -> {
            accumulator += value;
            index++;
          }
          case JMP -> {
            if (!indexChanged && !changedIndex.contains(index)) {
              indexChanged = true;
              changedIndex.add(index);
              index++;
            } else {
              index += value;
            }
          }
        }

        if (index == values.size() - 1) {
          Instruction lastLineInstruction =  getInstruction(values, index);
          if (Instruction.ACC == lastLineInstruction) {
            int lastLineValue = getValue(values, index);
            return accumulator + lastLineValue;
          }
          return accumulator;
        }
      }
    }
  }

  public List<String> readValues(String filePath) {
    try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      return stream.collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Error during reading file " + e.getMessage());
      return new ArrayList<>();
    }
  }

  enum Instruction{
    NOP,
    ACC,
    JMP
  }
}
