package com.entiv.servertoolbox.utils;

import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    public static List<Number> findNumber(String string) {
        Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher matcher = p.matcher(string);

        List<Number> numbers = new ArrayList<>();

        while (matcher.find()) {
            numbers.add(NumberUtils.createNumber(matcher.group()));
        }

        return numbers;
    }

    public static List<Integer> findInt(String string) {
        return findNumber(string).stream()
                .map(Number::intValue)
                .collect(Collectors.toList());
    }

    public static List<Double> findDouble(String string) {
        return findNumber(string).stream()
                .map(Number::doubleValue)
                .collect(Collectors.toList());
    }
}
