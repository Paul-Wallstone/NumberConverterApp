package com.jazzteam.task;

public enum Paths {

    DIGITS_FILE_PATH("src/main/resources/digits.txt"),
    FROM_ONE_TO_TWO_FILE_PATH("src/main/resources/fromOneToTwo.txt"),
    FROM_ONE_TO_TWENTY_FILE_PATH("src/main/resources/fromOneToTwenty.txt"),
    FROM_TWENTY_TO_HUNDREDS_FILE_PATH("src/main/resources/fromTwentyToHundreds.txt"),
    FROM_HUNDREDS_TO_THOUSANDS_FILE_PATH("src/main/resources/fromHundredsToThousands.txt");

    private String path;

    Paths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
