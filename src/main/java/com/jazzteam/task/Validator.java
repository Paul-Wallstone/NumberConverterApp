package com.jazzteam.task;


import static com.jazzteam.task.Paths.DIGITS_FILE_PATH;

public class Validator {
    private String number;
    private Integer limit;
    private Glossary glossary;

    public Validator(String number, Glossary glossary) {
        this.number = number;
        this.limit = glossary.getList(DIGITS_FILE_PATH.getPath()).size() / 3 + 1;
        this.glossary = glossary;
    }

    public Validator() {
    }

    public Boolean validate(Integer mapSize) {
        if (mapSize > limit || !number.matches("[0-9]+")) {
            return false;
        } else {
            return true;
        }
    }

    public String validateStartWithZero(String number) {
        StringBuffer buffer = new StringBuffer(number);
        if (buffer.length() > 1) {
            while (Integer.parseInt(String.valueOf(buffer.charAt(0))) == 0) {
                buffer.delete(0, 1);
            }
        }
        return buffer.toString();
    }
}
