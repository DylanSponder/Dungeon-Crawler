package com.mygdx.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelParser {
  public static final String COMMA_DELIMITER = ",";

  // Reads in a level sheet and returns a list containing renderable symbols.
  public List<List<String>> read(String filename) throws FileNotFoundException, IOException {
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(COMMA_DELIMITER);
        records.add(Arrays.asList(values));
      }
    }
    return records;
  }

  public void doesWorking(String filename) {
    List<List<String>> symbols;
    try {
      symbols = read(filename);
      System.out.println(Arrays.toString(symbols.toArray()));
    } catch (Exception e) {
      System.out.println("FUCK");
      System.out.println(e);
    }
  }
}
