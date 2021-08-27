package com.jazzteam.task;

import java.math.BigInteger;
import java.util.*;

public class ConverterImpl implements Converter {
    private String number;
    private Map<BigInteger, String> map;
    private Glossary glossary =new Glossary();
    private Validator validator;

    public ConverterImpl(String number) {
        this.number = number;
        this.map = getDigitValueHashMap();
    }

    public ConverterImpl() {
    }

    @Override
    public void setNumber(String number) {
        this.number = number;
        this.map = getDigitValueHashMap();
    }

    private Map<BigInteger, String> getDigitValueHashMap() {
        validator = new Validator(number, new Glossary());
        Map<BigInteger, String> map = new LinkedHashMap<>();
        StringBuffer strBuffer = new StringBuffer(validator.validateStartWithZero(number)).reverse();
        BigInteger i = BigInteger.ZERO;
        do {
            int startIndex;
            int endIndex;
            if (strBuffer.length() > 2) {
                startIndex = 0;
                endIndex = 3;
            } else {
                startIndex = 0;
                endIndex = strBuffer.length() < 2 ? 1 : 2;
            }
            StringBuffer strBuffer2 = new StringBuffer(strBuffer.substring(startIndex, endIndex));
            strBuffer.delete(startIndex, endIndex);
            i = i.add(BigInteger.ONE);
            map.put(i, strBuffer2.reverse().toString());

        } while (strBuffer.length() > 0);

        return map;
    }

    @Override
    public String convertToString() throws Exception {
        if (!validator.validate(map.size())) {
            return "Error in number or it's too big, please check!";
        }
        HashMap<Integer, List<String>> glossaryMap = glossary.getMap();
        String box = "";
        for (Map.Entry<BigInteger, String> entry : sortByKeys(map).entrySet()) {
            StringBuffer buffer = new StringBuffer(entry.getValue());
            if (!buffer.toString().equals("000")) {
                box += getStringResult(buffer, glossaryMap, entry.getKey()).trim() + " ";
                if (entry.getKey().compareTo(BigInteger.ONE) == 1) {
                    box += getThousands(entry.getKey(), glossary, buffer) + " ";
                }
            }
        }
        return box.trim();
    }

    private String getThousands(BigInteger position, Glossary glossary, StringBuffer buffer) throws Exception {
        String box = "";
        int x, xx = 0;
        int len = buffer.length();
        try {
            xx = Integer.parseInt(buffer.substring(len - 2, len));
        } catch (Exception e) {
            xx = 0;
        }
        x = xx > 10 && xx < 21 ? 5 : Integer.parseInt(buffer.substring(len - 1, len));

        try {
            return box = switch (x) {
                case 1 -> glossary.getList(Paths.DIGITS_FILE_PATH.getPath(), position.subtract(BigInteger.ONE)).get(0);
                case 2, 3, 4 -> glossary.getList(Paths.DIGITS_FILE_PATH.getPath(), position.subtract(BigInteger.ONE)).get(1);
                default -> glossary.getList(Paths.DIGITS_FILE_PATH.getPath(), position.subtract(BigInteger.ONE)).get(2);
            };
        } catch (Exception e) {
            throw new Exception("I don't know such number!:(");
        }
    }

    private String getStringResult(StringBuffer buffer, HashMap<Integer, List<String>> glossaryMap, BigInteger position) {
        String box = "";
        int digit = buffer.length(), len = buffer.length();
        int xx, x;
        for (int i = 0; i < len; i++) {
            int ii = i;
            x = Integer.parseInt(buffer.substring(ii, ++ii));
            try {
                xx = Integer.parseInt(buffer.substring(len - 2, len));
            } catch (Exception e) {
                xx = 0;
            }
            box += switch (digit--) {
                case 1 -> digitOne(x, glossaryMap, len, xx, position);
                case 2 -> digitTwo(x, glossaryMap, len, xx);
                case 3 -> digitThree(x, glossaryMap, len, xx);
                default -> "";
            };
        }
        return box;
    }

    private String digitOne(int x, HashMap<Integer, List<String>> glossaryMap, int len, int xx, BigInteger position) {
        String result;
        if (len == 1 && x == 0) {
            return "ноль";
        }
        if (position.equals(BigInteger.TWO) && (x == 2 || x == 1) && (xx != 11 && xx != 12)) {
            return result = glossaryMap.get(0).get(x - 1);
        }
        return result = (len == 1 ? glossaryMap.get(1).get(x - 1) :
                (xx < 20 && xx > 0) ? glossaryMap.get(1).get(xx - 1) :
                        len > 1 && x != 0 ? glossaryMap.get(1).get(x - 1) : "") + " ";

    }

    private String digitTwo(int x, HashMap<Integer, List<String>> glossaryMap, int len, int xx) {
        String result;
        return result = (xx < 20 || x == 0) ? "" : glossaryMap.get(2).get(x - 2) + " ";
    }

    private String digitThree(int x, HashMap<Integer, List<String>> glossaryMap, int len, int xx) {
        String result;
        return result = x == 0 ? "" : glossaryMap.get(3).get(x - 1) + " ";
    }


    public static <K extends Comparable, V> Map<K, V> sortByKeys(Map<K, V> map) {
        Map<K, V> treeMap = new TreeMap<>(new Comparator<K>() {
            @Override
            public int compare(K a, K b) {
                return b.compareTo(a);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }
}
