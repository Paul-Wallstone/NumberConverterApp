package com.jazzteam.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Glossary {
    private List<String> fromOneToTwo;
    private List<String> fromOneToTwenty;
    private List<String> fromTwentyToHundreds;
    private List<String> fromHundredsToThousands;


    private HashMap<Integer, List<String>> map;

    public Glossary() {
        this.fromOneToTwo = getList(Paths.FROM_ONE_TO_TWO_FILE_PATH.getPath());
        this.fromOneToTwenty = getList(Paths.FROM_ONE_TO_TWENTY_FILE_PATH.getPath());
        this.fromTwentyToHundreds = getList(Paths.FROM_TWENTY_TO_HUNDREDS_FILE_PATH.getPath());
        this.fromHundredsToThousands = getList(Paths.FROM_HUNDREDS_TO_THOUSANDS_FILE_PATH.getPath());
        this.map = new HashMap<>();
    }

    public HashMap<Integer, List<String>> getMap() {
        map.put(0, fromOneToTwo);
        map.put(1, fromOneToTwenty);
        map.put(2, fromTwentyToHundreds);
        map.put(3, fromHundredsToThousands);
        return map;
    }

    public List<String> getList(String path) {
        String line = "";
        List<String> list = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            String[] strings;
            while ((line = fileReader.readLine()) != null) {
                strings = line.split("[\\s ,]");
                for (int i = 0; i < strings.length; i++) {
                    list.add(strings[i]);
                }
            }
        } catch (
                IOException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public List<String> getList(String path, BigInteger position) {
        String line = "";
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            BigInteger bigInteger = BigInteger.ONE;
            String[] strings;
            while ((line = fileReader.readLine()) != null) {
                if (position.equals(bigInteger)) {
                    strings = line.split("[\\s ,]");
                    return new ArrayList<>(Arrays.asList(strings));
                }
                bigInteger = bigInteger.add(BigInteger.ONE);
            }
        } catch (
                IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
