package com.jazzteam.task;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConverterTest {
    Converter converter = new ConverterImpl();
    Glossary glossary = new Glossary();

    @DisplayName("Should pass the test comparing with big number")
    @Test
    public void getRightConversionTest() throws Exception {
        String number = "12312313413423524525";
        String expected = "двенадцать квинтиллионов триста двенадцать квадриллионов триста тринадцать триллионов четыреста тринадцать миллиардов четыреста двадцать три миллиона пятьсот двадцать четыре тысячи пятьсот двадцать пять";
        converter.setNumber(number);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }
    @DisplayName("Should pass the test comparing with too big number")
    @Test
    public void getRightConversionTooBigNumberTest() throws Exception {
        String number = "1140002323334141112232434234200003141341424234444444444444444444444444444444444444444444444444444444402";
        String expected = "Error in number or it's too big, please check!";
        converter.setNumber(number);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }
    @DisplayName("Should pass the test number started with first zero")
    @Test
    public void getRightConversionFirstZeroTest() throws Exception {
        String number = "00012312313413423524525";
        String expected = "двенадцать квинтиллионов триста двенадцать квадриллионов триста тринадцать триллионов четыреста тринадцать миллиардов четыреста двадцать три миллиона пятьсот двадцать четыре тысячи пятьсот двадцать пять";
        converter.setNumber(number);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }

    @DisplayName("Should pass the test numbers contain letters")
    @Test
    public void getRightConversionNumberWithLettersTest() throws Exception {
        String number = "1231231341t342352t4525";
        String expected = "Error in number or it's too big, please check!";
        converter.setNumber(number);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }
    @DisplayName("Should pass the test comparing numbers from 1 to 20")
    @Test
    public void getRightConversionFromOneToTwentyTest() throws Exception {
        List<String> fromOneToTwenty = glossary.getMap().get(1);
        for (int i = 1; i < 20; i++) {
            String expected = fromOneToTwenty.get(i - 1);
            converter.setNumber(String.valueOf(i));
            String actual = converter.convertToString();
            assertEquals(actual, expected);
        }
    }

    @DisplayName("Should pass the test comparing with 0")
    @Test
    public void getRightConversionZeroTest() throws Exception {
        String number = "0";
        String expected = "ноль";
        converter.setNumber(number);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }

    @DisplayName("Should pass the test comparing numbers from 20 to 100")
    @Test
    public void getRightConversionTensTest() throws Exception {
        Map<String, String> expected = new LinkedHashMap<>(
                Map.of("25", "двадцать пять", "36", "тридцать шесть",
                        "44", "сорок четыре", "57", "пятьдесят семь", "61", "шестьдесят один",
                        "70", "семьдесят", "89", "восемьдесят девять", "93", "девяносто три"));
        Map<String, String> actual = new LinkedHashMap<>();
        for (String key : expected.keySet()) {
            converter.setNumber(key);
            actual.put(key, converter.convertToString());
        }
        assertEquals(actual, expected);
    }

    @DisplayName("Should pass the test parameters provided by the dataTest.csv file")
    @ParameterizedTest
    @CsvFileSource(resources = "/dataTest.csv", numLinesToSkip = 1)
    public void getRightConversionFromDataTest(String input, String expected) throws Exception {
        converter.setNumber(input);
        String actual = converter.convertToString();
        assertEquals(actual, expected);
    }
}
