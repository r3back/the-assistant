package com.qualityplus.assistant.api.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for numbers
 */
@UtilityClass
public final class NumberUtil {
    private static final TreeMap<Integer, String> ROMAN_FORMAT = new TreeMap<>();

    static {
        ROMAN_FORMAT.put(1000, "M");
        ROMAN_FORMAT.put(900, "CM");
        ROMAN_FORMAT.put(500, "D");
        ROMAN_FORMAT.put(400, "CD");
        ROMAN_FORMAT.put(100, "C");
        ROMAN_FORMAT.put(90, "XC");
        ROMAN_FORMAT.put(50, "L");
        ROMAN_FORMAT.put(40, "XL");
        ROMAN_FORMAT.put(10, "X");
        ROMAN_FORMAT.put(9, "IX");
        ROMAN_FORMAT.put(5, "V");
        ROMAN_FORMAT.put(4, "IV");
        ROMAN_FORMAT.put(1, "I");
        ROMAN_FORMAT.put(0, "0");
    }

    /**
     * Converts a decimal number to roman format
     *
     * @param number decimal number
     * @return roman number
     */
    public String toRoman(final int number) {
        final int l =  ROMAN_FORMAT.floorKey(number);
        if (number == l) {
            return ROMAN_FORMAT.get(number);
        }
        return ROMAN_FORMAT.get(l) + toRoman(number - l);
    }

    /**
     * Retrieves a list of numbers given a start and end
     *
     * @param from start number
     * @param to   end number
     * @return List of {@link Integer}
     */
    public List<Integer> intStream(final int from, final int to) {
        return IntStream.range(from, to).boxed().collect(Collectors.toList());
    }

    /**
     * Retrieves a string converted to number or a null if
     * there"s an exception during conversion
     *
     * @param toTraslate string number
     * @return number or null
     */
    public Integer intOrNull(final String toTraslate) {
        try {
            return Integer.parseInt(toTraslate);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a string converted to number or a zero if
     * there"s an exception during conversion
     *
     * @param toTraslate string number
     * @return number or zero
     */
    public Integer intOrZero(final String toTraslate) {
        try {
            return Integer.parseInt(toTraslate);
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Retrieves a string converted to number or alternative number if
     * there"s an exception during conversion
     *
     * @param toTraslate string number
     * @param number     alternative number
     * @return number or alternative
     */
    public Integer intOrNumber(final String toTraslate, final Integer number) {
        try {
            return Integer.parseInt(toTraslate);
        } catch (final NumberFormatException e) {
            return number;
        }
    }

    /**
     * Retrieves a string converted to number or a null if
     * there"s an exception during conversion
     *
     * @param toTraslate string number
     * @return number or null
     */
    public Double doubleOrNull(final String toTraslate) {
        try {
            return Double.parseDouble(toTraslate);
        } catch (final NumberFormatException e) {
            return null;
        }
    }

    /**
     * Retrieves a string converted to number or a zero if
     * there"s an exception during conversion
     *
     * @param toTraslate string number
     * @return number or zero
     */
    public Double doubleOrZero(final String toTraslate) {
        try {
            return Double.parseDouble(toTraslate);
        } catch (NumberFormatException e) {
            return 0D;
        }
    }

    /**
     * Retrieves the number in a string or zero if there"s
     * nothing
     *
     * @param from text to find
     * @return found number or zero
     */
    public int extractNumbers(final String from) {
        return intOrZero(from.replaceAll("[^0-9]", ""));
    }


    /**
     * Converts a double value to int string
     * @param value double value
     * @return double converted to int string
     */
    public String toInt(final double value) {
        return String.valueOf((int) value);
    }

    /**
     * Retrieves a list of numbers given a start and end
     *
     * @param begin start number
     * @param end   end number
     * @return List of {@link Integer}
     */
    public List<Integer> secuence(final int begin, final int end) {
        final List<Integer> ret = new ArrayList<>(end - begin + 1);
        for (int i = begin; i <= end; i++) {
            ret.add(i);
        }
        return ret;
    }

    /**
     * Retrieves a list of numbers as string given a start and end
     *
     * @param begin start number
     * @param end   end number
     * @return List of {@link String}
     */
    public List<String> stringSecuence(final int begin, final int end) {
        return secuence(begin, end)
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
